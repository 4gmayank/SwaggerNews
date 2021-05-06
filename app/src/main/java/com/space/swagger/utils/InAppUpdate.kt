package com.space.swagger.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

class InAppUpdate constructor(context: Context) : Activity() {

    private val appUpdateManager = AppUpdateManagerFactory.create(context)
    private var appUpdateType = AppUpdateType.IMMEDIATE;
    private val DAYS_FOR_FLEXIBLE_UPDATE: Int = 365
    private val APP_UPDATE_REQUEST_CODE: Int = 123
    private var flexibleUpdatedListener = null




    fun update(context: Context) {
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        //checks platform dependent update
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate()
            }else if (appUpdateInfo.updateAvailability()
                == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
            ) {
                requestUpdate(appUpdateInfo)
            }else if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && (appUpdateInfo.clientVersionStalenessDays() ?: -1) >= DAYS_FOR_FLEXIBLE_UPDATE
                && appUpdateInfo.updatePriority() >= 0
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)//AppUpdateType.IMMEDIATE
            ) {
                requestUpdate(appUpdateInfo)
            }
        }
    }

    private fun requestUpdate(appUpdateInfo: AppUpdateInfo) {
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            appUpdateType,
            this,
            APP_UPDATE_REQUEST_CODE
        )
        //AllowAssetPackDeletion
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == APP_UPDATE_REQUEST_CODE) {
            when(resultCode) {
                RESULT_OK -> Log.e("MY_APP", "App Updated $resultCode")
                RESULT_CANCELED -> Log.e("MY_APP", "User Denied App Update")
                ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                    Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
//                    restartAppUpdate()
                }
            }
        }
    }

    private fun flexibleUpdate(updateType: Int){
        if(AppUpdateType.FLEXIBLE != updateType){
            return
        }

        val flexibleUpdatedListener = InstallStateUpdatedListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADING) {
                val bytesDownloaded = state.bytesDownloaded()
                val totalBytesToDownload = state.totalBytesToDownload()
                updateProgress(bytesDownloaded*100/totalBytesToDownload)
            } else if (state.installStatus() == InstallStatus.DOWNLOADED) {
                // request user confirmation to restart the app.
                popupSnackbarForCompleteUpdate()
            }
        }
        appUpdateManager.registerListener(flexibleUpdatedListener)
//        startUpdate()


    }

    private fun updateProgress(l: Long) {
        l/2

    }

    fun popupSnackbarForCompleteUpdate() {
        /*Snackbar.make(
            findViewById(R.id.activity_main_layout),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            setActionTextColor(resources.getColor(R.color.snackbar_action_text_color))
            show()
        }*/
    }

    override fun onResume() {
        super.onResume()

        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->

                // If the update is downloaded but not installed,
                // notify the user to complete the update.
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
//                    popupSnackbarForCompleteUpdate()
                }
            }
    }

    override fun onStop() {
        super.onStop()
        if(AppUpdateType.FLEXIBLE != appUpdateType){
            flexibleUpdatedListener?.let { appUpdateManager.unregisterListener(it) }
        }
    }

}