package cn.changhsqinda.scrollingtable.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.changhsqinda.scrollingtable.R
import cn.changhsqinda.scrollingtable.data.Empty
import cn.changhsqinda.scrollingtable.databinding.LayoutScrollingTableItemBinding
import cn.changhsqinda.scrollingtable.databinding.LayoutScrollingTableSecondItemBinding
import cn.changhsqinda.scrollingtable.viewmodel.ViewModelActivitySecond
import java.util.*


@BindingAdapter(
    value = ["tableItemAdapterViewModelBind", "tableItemAdapterDataBind", "tableItemAdapterBindColumnRecyclerView"],
    requireAll = false
)
fun tableItemAdapterViewModelBind(
    recyclerView: RecyclerView,
    viewModel: ViewModelActivitySecond,
    data: List<List<Empty>>?,
    recyclerViewColumn: RecyclerView
) {
    if (null == recyclerView.adapter) {
        recyclerView.adapter = TableSecondAdapter(viewModel, data)
        (recyclerView.adapter as TableSecondAdapter).syncScrollViewChangeObserver.mScrollListeners.add(
            recyclerViewColumn
        )
    }
    (recyclerView.adapter as TableSecondAdapter).data = data
    (recyclerView.adapter as TableSecondAdapter).notifyDataSetChanged()
}

class TableSecondAdapter(
    private val viewModel: ViewModelActivitySecond,
    var data: List<List<Empty>>? = arrayListOf()
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val syncScrollViewChangeObserver = SyncScrollViewChangeObserver


    override fun getItemViewType(position: Int): Int = R.layout.layout_scrolling_table_second_item


    internal class OnScrollListenerImp(private val syncScrollViewChangeObserver: SyncScrollViewChangeObserver) :
        RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE)
                syncScrollViewChangeObserver.onScrolled(recyclerView, dx, dy)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                syncScrollViewChangeObserver.onScrollStateChanged(recyclerView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.layout_scrolling_table_second_item ->
                ItemSecondViewHolder(
                    binding =
                    LayoutScrollingTableSecondItemBinding.inflate(LayoutInflater.from(parent.context))
                        .apply {
                            viewModel = this@TableSecondAdapter.viewModel
                            val onScrollListenerImp =
                                OnScrollListenerImp(syncScrollViewChangeObserver)
                            scrollingTableItemContent.addOnScrollListener(onScrollListenerImp)
                            syncScrollViewChangeObserver.mScrollListeners.add(
                                scrollingTableItemContent
                            )
                        }
                )
            else -> throw IllegalArgumentException("can not find  layout")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemSecondViewHolder -> {
                holder.bindTo(data?.get(position) ?: arrayListOf())
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        syncScrollViewChangeObserver.scrollToPosition((holder as ItemSecondViewHolder).binding.scrollingTableItemContent)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }
}

class ItemSecondViewHolder(
    val binding: LayoutScrollingTableSecondItemBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(item: List<Empty>) {
        if (binding.scrollingTableItemContent.adapter == null) binding.scrollingTableItemContent.adapter =
            TableSecondItemAdapter(item)
        (binding.scrollingTableItemContent.adapter as TableSecondItemAdapter).data = item
        (binding.scrollingTableItemContent.adapter as TableSecondItemAdapter).notifyDataSetChanged()
    }
}


@BindingAdapter(
    value = ["secondTableColumnAdapterViewModelBind", "secondTableColumnAdapterDataBind"],
    requireAll = true
)
fun secondTableColumnAdapterViewModelBind(
    recyclerView: RecyclerView,
    viewModel: ViewModelActivitySecond,
    data: List<Empty>?
) {
    if (null == recyclerView.adapter) {
        recyclerView.adapter = TableSecondItemAdapter(data)
    }
    (recyclerView.adapter as TableSecondItemAdapter).data = data
    (recyclerView.adapter as TableSecondItemAdapter).notifyDataSetChanged()
}


@BindingAdapter(
    value = ["secondTableRowAdapterViewModelBind", "secondTableRowAdapterDataBind"],
    requireAll = true
)
fun secondTableRowAdapterViewModelBind(
    recyclerView: RecyclerView,
    viewModel: ViewModelActivitySecond,
    data: List<Empty>?
) {
    if (null == recyclerView.adapter) recyclerView.adapter = TableSecondItemAdapter(data)
    (recyclerView.adapter as TableSecondItemAdapter).data = data
    (recyclerView.adapter as TableSecondItemAdapter).notifyDataSetChanged()
}

class TableSecondItemAdapter(var data: List<Empty>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return R.layout.layout_scrolling_table_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.layout_scrolling_table_item -> ItemSecondItemViewHolder(
                binding =
                LayoutScrollingTableItemBinding.inflate(LayoutInflater.from(parent.context))
            )
            else
            -> throw IllegalArgumentException("can not find  layout")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemSecondItemViewHolder -> holder.bindTo(data?.get(position) ?: Empty())
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }
}

class ItemSecondItemViewHolder(private val binding: LayoutScrollingTableItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(item: Empty) {
        binding.item = item
    }

}

object SyncScrollViewChangeObserver {

    val mScrollListeners = arrayListOf<RecyclerView>()
    private var lastPosition = 0
    private var lastOffset = 0

    fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        for (index in mScrollListeners.indices) {
            if (mScrollListeners[index] != recyclerView)
                mScrollListeners[index].scrollBy(dx, dy)
        }
    }

    private fun getPositionAndOffset(recyclerView: RecyclerView): Pair<Int, Int> {
        val view = (recyclerView.layoutManager as LinearLayoutManager).getChildAt(0)
        val lastOffset = view?.left ?: 0
        val lastPosition = view?.let {
            (recyclerView.layoutManager as LinearLayoutManager).getPosition(
                it
            )
        } ?: 0
        return Pair(lastOffset, lastPosition)
    }

    fun scrollToPosition(recyclerView: RecyclerView) {
        (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
            lastPosition,
            lastOffset
        )
    }

    fun onScrollStateChanged(recyclerView: RecyclerView) {
        val positionAndOffset = getPositionAndOffset(recyclerView)
        lastOffset = positionAndOffset.first
        lastPosition = positionAndOffset.second
    }
}