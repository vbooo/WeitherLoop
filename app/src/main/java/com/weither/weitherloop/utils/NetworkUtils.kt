
package com.weither.weitherloop.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.weither.weitherloop.data.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Checks if a network connection exists.
 */
open class NetworkUtilsImpl @Inject constructor(@ApplicationContext val context: Context) :
    NetworkUtils {

    override fun hasNetworkConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return networkCapabilities != null &&
            (
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                )
    }
}
