package com.padc.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.padc.moments.R
import com.padc.moments.data.models.GiphyModel
import com.padc.moments.data.models.GiphyModelImpl
import com.padc.moments.databinding.ActivitySearchGifsBinding
import com.padc.moments.delegates.GifItemActionDelegate

class SearchGifsActivity : AppCompatActivity(),GifItemActionDelegate {

    private lateinit var binding: ActivitySearchGifsBinding

    // Model
    private var mGiphyModel: GiphyModel = GiphyModelImpl

    // General

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SearchGifsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchGifsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

//    @SuppressLint("CheckResult")
//    private fun setUpListeners() {
//        binding.etSearchGif.textChanges()
//            .debounce(500L, TimeUnit.MILLISECONDS)
//            .flatMap {
//                mGiphyModel.searchGifs(it.toString())
//            }
//            .map {data ->
//                if(data.isEmpty()) {
//                    mGiphyModel.getAllTrendingGifs(
//                        onSuccess = {dataList ->
//                            mAdapter.setNewData(dataList)
//                        },
//                        onFailure = {error ->
//                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
//                        }
//                    )
//                }
//                data
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                mAdapter.setNewData(it)
//            }, {
//                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
//            })
//    }
//
//    private fun setUpRecyclerView() {
//        mAdapter = GifAdapter(this)
//        binding.rvGif.adapter = mAdapter
//        binding.rvGif.layoutManager = GridLayoutManager(this, 2)
//    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.anim.scroll_down)
    }

    override fun onTapGifImage(url: String) {
        val intent = Intent()
        intent.putExtra("data", url)
        val resultCode = RESULT_OK
        setResult(resultCode, intent)
        finish()
        overridePendingTransition(0, R.anim.scroll_down)
    }
}