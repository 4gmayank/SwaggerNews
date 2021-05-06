package com.space.swagger.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.space.swagger.R
import com.space.swagger.databinding.ActivityDashboardBinding
import com.space.swagger.model.Article
import com.space.swagger.ui.adapter.ArticleAdapter
import com.space.swagger.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), ArticleAdapter.IArticleCallback {

    private val viewModel: ArticleViewModel by viewModels()
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityDashboardBinding.inflate(layoutInflater).also { binding = it }
        setContentView(binding.root)

        adapter = ArticleAdapter()
        adapter.callback = this
        binding.recyclerViewArticles.adapter = adapter

        subscribeToObservables()
        viewModel.loadArticles()
        binding.swipeRefreshLayout.isRefreshing = true
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadArticles()
        }
    }

    private fun subscribeToObservables() {
        viewModel.dataState.observe(
            this,
            Observer { dataState: DataState<List<Article>> ->
                when (dataState) {
                    is DataState.Success -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        with(adapter) { submitList(dataState.data) }
                    }
                    is DataState.Error -> {
                        displayError()
                    }
                    DataState.Loading -> {
                        if (!binding.swipeRefreshLayout.isRefreshing) {
                            binding.swipeRefreshLayout.isRefreshing = true
                        }
                    }
                }
            }
        )
    }


    private fun displayError() {
        if (binding.swipeRefreshLayout.isRefreshing) {
            binding.swipeRefreshLayout.isRefreshing = false
        }
        if (!isOnline(this)) {
            showConnectionErrorDialog()
        } else {
            Toast.makeText(
                this,
                getString(R.string.error_occured),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun showConnectionErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.no_internet_msg))
        builder.setMessage(getString(R.string.internet_warning))
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton(getString(R.string.try_again)) { _, _ ->
            if(isOnline(this)){
                viewModel.loadArticles()
            }else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.connect_internet),
                    Toast.LENGTH_LONG
                ).show()
                subscribeToObservables()
            }
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()

    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null &&(
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            )){ return true }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    override fun onItemClick(url: String) {
        val intent = Intent(this, WebActivity::class.java)
        val bundle = Bundle()
        bundle.putString(WebActivity.ARTICLE_URL, url)
        intent.putExtra(WebActivity.BUNDLE, bundle)
        startActivity(intent, bundle)
    }

    /* val swipeCallback : ItemTouchHelper.SimpleCallback = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT,0

     ){
         override fun onMove(
             recyclerView: RecyclerView,
             viewHolder: RecyclerView.ViewHolder,
             target: RecyclerView.ViewHolder
         ): Boolean {
         return false
         }

         override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
             viewModel.dataState.observe(this@DashboardActivity, Observer { dataState ->
                 when (dataState) {
                     is DataState.Success -> {
                         onItemClick(dataState.data[viewHolder.adapterPosition as Int].articleUrl)
                     }
                     else -> return@Observer
                 }
             })

         }
     }*/
}
