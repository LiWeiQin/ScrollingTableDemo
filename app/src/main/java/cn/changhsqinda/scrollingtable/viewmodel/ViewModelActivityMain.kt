package cn.changhsqinda.scrollingtable.viewmodel

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter(
    value = [
        "bindRecycleViewItemCount"
    ],
    requireAll = true
)
fun bindRecycleViewItemCount(
    view: RecyclerView,
    count: Int
) {
    view.layoutManager = GridLayoutManager(view.context, 9, GridLayoutManager.VERTICAL, false)
}


class ViewModelActivityMain : ViewModel() {

    private var _count = MutableLiveData<Int>().apply {
        value = 9
    }
    var count: LiveData<Int> = _count
}