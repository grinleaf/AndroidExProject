package com.grinleaf.ex098preferencefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val iv= findViewById<PhotoView>(R.id.phv)
//        val iv= findViewById(R.id.phv) as PhotoView   //형번환해서 사용도 가능!

        Glide.with(this).load(R.drawable.bird_image).into(iv)

    }
}