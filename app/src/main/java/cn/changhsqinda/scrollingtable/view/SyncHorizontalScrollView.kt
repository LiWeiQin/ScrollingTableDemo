package cn.changhsqinda.scrollingtable.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView

class SyncHorizontalScrollView : HorizontalScrollView {

    private var view: View? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onScrollChanged(x: Int, y: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(x, y, oldl, oldt)
        view?.scrollTo(x, y)
    }

    fun setScrollView(view: View) {
        this.view = view
    }
}