package com.padc.moments.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.padc.moments.R
import com.padc.moments.adapters.BookAdapter
import com.padc.moments.databinding.ActivityBookDetailsBinding

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityBookDetailsBinding
    private lateinit var mBookAdapter : BookAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_book_details)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        mBookAdapter = BookAdapter()
        mBinding.rvBook.adapter = mBookAdapter
        mBinding.rvBook.layoutManager =GridLayoutManager(this,2)
    }
}