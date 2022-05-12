package ru.vidos.habitstracker.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GradientItemDecoration : RecyclerView.ItemDecoration() {

    private val gColors = intArrayOf(
        Color.rgb(255, 0,0),
        Color.rgb(255, 255,0),
        Color.rgb(0, 255,0),
        Color.rgb(0, 255,255),
        Color.rgb(0, 0,255),
        Color.rgb(255, 0,255),
        Color.rgb(255, 0,0))
    private val gd = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, gColors)

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null) {

            return
        }

        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val left = -parent.computeHorizontalScrollOffset()
        val right = parent.computeHorizontalScrollRange() + left

        gd.setBounds(left, top, right, bottom)
        gd.draw(canvas)
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) = outRect.set(0, 0, 0, 0)


}