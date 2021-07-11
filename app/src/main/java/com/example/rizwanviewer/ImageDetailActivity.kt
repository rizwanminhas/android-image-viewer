package com.example.rizwanviewer

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import java.io.File


class ImageDetailActivity : AppCompatActivity() {
    var imgPath: String? = null
    private var imageView: ImageView? = null
    private var scaleGestureDetector: ScaleGestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        imgPath = intent.getStringExtra("imgPath")

        imageView = findViewById(R.id.idIVImage)
        imageView?.animate()?.rotation(-90.0f)

        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        val imgFile = File(imgPath)

        if (imgFile.exists()) {
            Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background)
                .into(imageView)
        }
    }

    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {
        scaleGestureDetector!!.onTouchEvent(motionEvent)
        return true
    }

    inner class ScaleListener : SimpleOnScaleGestureListener() {
        private var mScaleFactor: Float = 1.0f

        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {

            mScaleFactor = mScaleFactor * scaleGestureDetector.scaleFactor
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f))

            imageView?.setScaleX(mScaleFactor)
            imageView?.setScaleY(mScaleFactor)
            return true
        }
    }}