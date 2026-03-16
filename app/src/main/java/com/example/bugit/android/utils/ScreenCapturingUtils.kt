package com.example.bugit.android.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View
import androidx.core.content.FileProvider
import androidx.core.graphics.createBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/**
 * Captures exactly what is currently visible on the user's screen.
 */
fun captureCurrentScreen(activity: Activity): Bitmap? {
    return try {
        val view: View = activity.window.decorView.rootView

        val bitmap = createBitmap(view.width, view.height)
        val canvas = Canvas(bitmap)

        view.draw(canvas)

        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

suspend fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri? = withContext(Dispatchers.IO) {
    try {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()

        val file = File(cachePath, "screenshot_${System.currentTimeMillis()}.png")
        val fileOutputStream = FileOutputStream(file)

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()

        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
