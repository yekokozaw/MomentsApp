package com.padc.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.padc.moments.databinding.ActivityFullSizeImageBinding

class FullSizeImageActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityFullSizeImageBinding
    companion object{
        private const val EXTRA_IMAGE_ID = "EXTRA_IMAGE_ID"
        fun newIntent(context: Context, imageUrl: String) : Intent {
            val intent =  Intent(context,FullSizeImageActivity::class.java)
            intent.putExtra(EXTRA_IMAGE_ID,imageUrl)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFullSizeImageBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        val imageId = intent.getStringExtra(EXTRA_IMAGE_ID)
        if (imageId != null){
            loadAndDisplayImage(imageId)
        }else
            Toast.makeText(this,"image url not found", Toast.LENGTH_SHORT).show()

        mBinding.fab.setOnClickListener {
            finish()
        }
    }

    private fun loadAndDisplayImage(imageUrl: String) {
        // Use Glide, Picasso, or another image loading library to load the image
        Glide.with(this)
            .load(imageUrl)
            .transform(RoundedCorners(30))
            .into(mBinding.ivImage) // Assuming your ImageView's ID
    }
}