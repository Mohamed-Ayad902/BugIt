package com.example.bugit.android.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.FileProvider
import androidx.core.graphics.createBitmap
import com.example.bugit.android.theme.BugItTheme
import com.example.bugit.android.theme.rememberWindowSizeClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.coroutines.resume

enum class CaptureMode { VISIBLE_SCREEN, FULL_CONTENT }

/**
 * Finds the closest Activity from a Context hierarchy.
 */
fun Context.findActivity(): Activity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}

/**
 * Compose offscreen and capture to Bitmap.
 *
 * @param activity Must be an Activity so ComposeView can attach.
 * @param widthPx Target width in pixels.
 * @param captureMode [CaptureMode.VISIBLE_SCREEN] to limit to screen height, or [CaptureMode.FULL_CONTENT] to allow taller content.
 * @param maxHeightPx Only used for [CaptureMode.FULL_CONTENT] (clamped).
 * @param content The composable content to capture.
 */
suspend fun captureComposableContentBitmap(
    activity: Activity,
    widthPx: Int,
    captureMode: CaptureMode = CaptureMode.VISIBLE_SCREEN,
    maxHeightPx: Int = 30000,
    content: @Composable () -> Unit,
): Bitmap = withContext(Dispatchers.Main) {
    val root = activity.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
        ?: throw IllegalStateException("Unable to find activity content view")

    val screenHeightPx = activity.resources.displayMetrics.heightPixels

    val composeView = createOffscreenComposeView(activity, content)
    val container = FrameLayout(activity).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addView(composeView)
    }

    root.addView(container)
    container.translationX = -root.width.toFloat() // keep offscreen

    try {
        val widthSpec = MeasureSpec.makeMeasureSpec(widthPx, MeasureSpec.EXACTLY)
        val limitHeight = if (captureMode == CaptureMode.FULL_CONTENT) maxHeightPx else screenHeightPx
        val heightSpec = MeasureSpec.makeMeasureSpec(limitHeight, MeasureSpec.AT_MOST)

        // Measure and wait for layout passes
        composeView.measure(widthSpec, heightSpec)
        composeView.awaitMeasurement()

        // Re-measure after layout has settled
        composeView.measure(widthSpec, heightSpec)

        val clampedHeight = composeView.measuredHeight.coerceAtMost(limitHeight)

        // Draw and return
        return@withContext composeView.drawToBitmap(widthPx, clampedHeight)
    } finally {
        try { root.removeView(container) } catch (_: Exception) {}
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

/**
 * Creates and configures the offscreen ComposeView wrapper.
 */
private fun createOffscreenComposeView(
    activity: Activity,
    content: @Composable () -> Unit
): ComposeView {
    return ComposeView(activity).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
        setContent {
            BugItTheme(rememberWindowSizeClass()) {
                content()
            }
        }
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}

/**
 * Suspends the coroutine until the ComposeView has completed its measurement phase.
 */
private suspend fun ComposeView.awaitMeasurement() = suspendCancellableCoroutine { cont ->
    if (measuredHeight > 0 && measuredWidth > 0) {
        cont.resume(Unit)
        return@suspendCancellableCoroutine
    }

    val vto = viewTreeObserver
    val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredHeight > 0 && measuredWidth > 0) {
                try { vto.removeOnGlobalLayoutListener(this) } catch (_: Exception) {}
                if (!cont.isCompleted) cont.resume(Unit)
            }
        }
    }

    vto.addOnGlobalLayoutListener(listener)

    cont.invokeOnCancellation {
        try { vto.removeOnGlobalLayoutListener(listener) } catch (_: Exception) {}
    }
}

/**
 * Lays out the ComposeView and draws its content onto a new Bitmap.
 */
private fun ComposeView.drawToBitmap(widthPx: Int, heightPx: Int): Bitmap {
    layout(0, 0, widthPx, heightPx)
    val bitmap = createBitmap(widthPx, heightPx)
    val canvas = Canvas(bitmap)
    draw(canvas)
    return bitmap
}