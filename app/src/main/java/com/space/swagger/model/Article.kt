package com.space.swagger.model

import android.annotation.SuppressLint
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

data class Article(
    val id: String,
    val title: String,
    val articleUrl: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String

){
    var isSummaryVisible : Boolean = false
    private var articledPublishedTime: String = publishedAt

    companion object{
        const val serverTimeStamtFormat = "yyyy-MM-dd'T'HH:mm:ss.000Z"
        const val newsTimeStamp = "dd-MM HH"
    }

//    fun publishedAt(): String{
//        return publishTimeMilliSecond(newsTimeStamp, publishedAt);
//    }

    @SuppressLint("SimpleDateFormat")
    fun timePassed(): String{
        val timeDiff = System.currentTimeMillis() - publishTimeMilliSecond(serverTimeStamtFormat,publishedAt).millis
        val formatter = SimpleDateFormat(newsTimeStamp)
        val calendar: Calendar = Calendar.getInstance()
//        return formatter.format(timeDiff)
        val seconds: Long = timeDiff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val month = days/30
        val time = days.toString() + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60
        return timeString(month, days, hours, minutes);
    }

    private fun timeString(month: Long, days: Long, hours: Long, minutes: Long): String {
        var timeString :StringBuilder = StringBuilder("")
        if(month>0){
            timeString.append(month).append(" month ")
        }
        if(days>0){
            timeString.append(days).append(" days ")
        }
        if(hours>0){
            timeString.append(hours).append(" hours ago")
        }
        if(timeString.isEmpty()){
           timeString.append(minutes).append(" min ago")

        }
        return timeString.toString();
    }

    private fun publishTimeMilliSecond(format:String,timeStr: String ): DateTime {
        return DateTimeFormat.forPattern(format).parseDateTime(timeStr)
    }
}
