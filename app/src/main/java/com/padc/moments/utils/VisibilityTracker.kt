package com.padc.moments.utils

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import com.padc.moments.data.vos.UserVO

class VisibilityTracker(
    private val recyclerView: RecyclerView,
    private val visibilityThreshold: Float = 0.5f, // Minimum visible percentage
    private val onPostVisible: (post: String) -> Unit
) : RecyclerView.OnScrollListener() {

    private val visiblePosts = HashMap<Int, Boolean>()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager ?: return

        // Get visible items
        val visibleRect = Rect()
        val childCount = layoutManager.childCount
        for (i in 0 until childCount) {
            val child = layoutManager.getChildAt(i)
            if (child != null) {
                if (child.getGlobalVisibleRect(visibleRect) &&
                    visibleRect.height() * visibleRect.width() / child.height * child.width >= visibilityThreshold * child.height * child.width) {
                    val position = layoutManager.getPosition(child)
                    //val post = recyclerView.adapter?.(position) as? String
                    val post : String?= "seen"
                    if (post != null && !visiblePosts.containsKey(position)) {
                        visiblePosts[position] = true
                        onPostVisible(post) // Call callback for seen post
                    }
                } else {
                    visiblePosts.remove(layoutManager.getPosition(child))
                }
            }
        }
    }
}
