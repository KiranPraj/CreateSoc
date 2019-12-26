package com.icspl.kartik.myapplication.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.util.Log
import com.icspl.createsoc.R
import com.icspl.createsoc.callback.ApiService
import com.icspl.createsoc.networking.RetrofitClient
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class Common {
    public val BASE_URL = "http://192.168.0.243:5006/MasterAdminWebApi/"
    public fun getAPI(): ApiService {
        return RetrofitClient.getClient(Companion.BASE_URL).create(ApiService::class.java)
    }
    companion object {
        public val BASE_URL = "http://192.168.0.243:5006/MasterAdminWebApi/"
      // public val BASE_URL = "http://icspl.org:5005/Home/"



        public fun isConnectedMobile(context: Context): Boolean {

            val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE)
                    as ConnectivityManager?

            val networkInfo = connectivityManager!!.activeNetworkInfo

            return networkInfo != null && networkInfo.isConnected
        }

    }


    fun convertMultipart(imagePath: String?, paramName: String): MultipartBody.Part {
        val mRequestBody: RequestBody
        val body5: MultipartBody.Part
        if (imagePath != null)
            if (!imagePath.equals("NA", ignoreCase = true)) {
                val mFile = File(imagePath)
                mRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), mFile)
                body5 = MultipartBody.Part.createFormData(paramName, mFile.name, mRequestBody)
                Log.i("Multipart body", "convertMultipart: " + mFile.name)
            } else {
                mRequestBody = RequestBody.create(MediaType.parse("text/plain"), "")
                body5 = MultipartBody.Part.createFormData(paramName, "NA", mRequestBody)
            }
        else {
            mRequestBody = RequestBody.create(MediaType.parse("text/plain"), "")
            body5 = MultipartBody.Part.createFormData(paramName, "NA", mRequestBody)
        }
        return body5
    }


    fun getFormatedDate(date: String): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        val sdate = dateFormat.parse(date)

        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val newDate = formatter.format(sdate)

        return newDate
    }

    public fun getDateFormat(): String {
        val mSDF = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return mSDF.format(Date())
    }

    public fun getTime(): String {
        val mSDF = SimpleDateFormat("HH:mm", Locale.getDefault())
        return mSDF.format(Date())
    }

    fun getDownloadSpeedString(context: Context, downloadedBytesPerSecond: Long): String {
        if (downloadedBytesPerSecond < 0) {
            return ""
        }
        val kb = downloadedBytesPerSecond.toDouble() / 1000.toDouble()
        val mb = kb / 1000.toDouble()
        val decimalFormat = DecimalFormat(".##")
        return when {
            mb >= 1 -> context.getString(R.string.downloadSpeedMb, decimalFormat.format(mb))
            kb >= 1 -> context.getString(R.string.downloadSpeedKb, decimalFormat.format(kb))
            else -> context.getString(R.string.downloadSpeedBytes, downloadedBytesPerSecond)
        }
    }

}
