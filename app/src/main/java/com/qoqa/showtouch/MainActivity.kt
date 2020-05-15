package com.qoqa.showtouch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSizeSelector()
        setupDelaySelector()
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event?.let { touchZone.handleTouch(MotionEvent.obtain(it)) }
        return super.dispatchTouchEvent(event)
    }

    private fun setupSizeSelector(){
        sizeSelector.max = 100
        sizeSelector.incrementProgressBy(1)
        sizeSelector.progress = touchZone.dotSize.toInt()
        sizeValue.text = "${sizeSelector.progress} dp"

        sizeSelector.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                touchZone.dotSize = progress.toFloat()
                sizeValue.text = "$progress dp"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    private fun setupDelaySelector(){
        delaySelector.max = 500
        delaySelector.incrementProgressBy(5)
        delaySelector.progress = touchZone.clearDelay.toInt()
        delayValue.text = "${delaySelector.progress} ms"

        delaySelector.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                touchZone.clearDelay = progress.toLong()
                delayValue.text = "$progress ms"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }
}
