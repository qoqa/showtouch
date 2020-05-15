package com.qoqa.showtouchlibrary

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.core.view.isVisible

class TouchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val clearPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val locationOnScreen = IntArray(2)

    var dotSize = 35f
    var clearDelay = 15L
    var dotColor = Color.parseColor("#800080FF")
        set(value) {
            field = value
            paint.color = dotColor
        }

    init {
        this.setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSPARENT)
        paint.color = dotColor
        clearPaint.color = Color.TRANSPARENT
    }

    private fun Number.fromDpToPx(context: Context): Float {
        val r = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), r.displayMetrics)
    }

    fun handleTouch(event: MotionEvent) {
        if (this.isVisible) {
            getLocationOnScreen(locationOnScreen)
            event.offsetLocation(-locationOnScreen[0].toFloat(), -locationOnScreen[1].toFloat())
            val canvas = holder.lockCanvas()
            canvas.drawColor(0, PorterDuff.Mode.CLEAR)

            val radius =  dotSize.fromDpToPx(context)

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR)
                    canvas.drawCircle(event.x, event.y, radius, paint)
                }
                MotionEvent.ACTION_MOVE -> {
                    canvas.drawCircle(event.x, event.y, radius, paint)
                }
                MotionEvent.ACTION_UP -> {
                    canvas.drawCircle(event.x, event.y, radius, paint)
                    clear()
                }
            }
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun clear() {
        val handler = Handler()
        handler.postDelayed(
            {
                try {
                    val canvas = holder.lockCanvas()
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                    holder.unlockCanvasAndPost(canvas)
                } catch (e: Exception) {
                    Log.e("TouchView", "Failed to clear")
                }
            },
            clearDelay
        )
    }
}
