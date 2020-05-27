package cn.changhsqinda.scrollingtable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.changhsqinda.scrollingtable.R
import cn.changhsqinda.scrollingtable.data.Empty
import cn.changhsqinda.scrollingtable.databinding.LayoutScrollingTableItemBinding
import cn.changhsqinda.scrollingtable.databinding.LayoutScrollingTableSecondItemBinding
import cn.changhsqinda.scrollingtable.viewmodel.ViewModelActivitySecond
import java.util.ArrayList

@BindingAdapter(
    value = ["tableItemAdapterViewModelBind", "tableItemAdapterDataBind"],
    requireAll = true
)
fun tableItemAdapterViewModelBind(
    recyclerView: RecyclerView,
    viewModel: ViewModelActivitySecond,
    data: List<List<Empty>>?
) {
    if (null == recyclerView.adapter) {
        recyclerView.adapter = TableSecondAdapter(viewModel)
    }
    (recyclerView.adapter as TableSecondAdapter).submitList(data ?: arrayListOf())
}

class TableSecondAdapter(private val viewModel: ViewModelActivitySecond) :
    ListAdapter<List<Empty>, RecyclerView.ViewHolder>(diffCallback) {


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<List<Empty>>() {
            override fun areItemsTheSame(oldItem: List<Empty>, newItem: List<Empty>): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: List<Empty>, newItem: List<Empty>): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return R.layout.layout_scrolling_table_second_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.layout_scrolling_table_second_item -> ItemSecondViewHolder(binding =
            LayoutScrollingTableSecondItemBinding.inflate(LayoutInflater.from(parent.context))
                .apply {
                    viewModel = this@TableSecondAdapter.viewModel
                })
            else -> throw IllegalArgumentException("can not find  layout")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemSecondViewHolder -> holder.bindTo(getItem(position))
        }
    }

}

class ItemSecondViewHolder(private val binding: LayoutScrollingTableSecondItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(item: List<Empty>) {
        binding.item = item as ArrayList<Empty>?
    }
}

@BindingAdapter(
    value = ["tableItemAdapterViewModelItemBind", "tableItemAdapterItemDataBind"],
    requireAll = true
)
fun tableItemAdapterItemViewModelBind(
    recyclerView: RecyclerView,
    viewModel: ViewModelActivitySecond,
    data: List<Empty>?
) {
    if (null == recyclerView.adapter) {
        recyclerView.adapter = TableSecondItemAdapter(viewModel)
    }
    (recyclerView.adapter as TableSecondItemAdapter).submitList(data ?: arrayListOf())
}

class TableSecondItemAdapter(private val viewModel: ViewModelActivitySecond) :
    ListAdapter<Empty, RecyclerView.ViewHolder>(diffCallback) {


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Empty>() {
            override fun areItemsTheSame(oldItem: Empty, newItem: Empty): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Empty, newItem: Empty): Boolean {
                return oldItem == newItem
            }
        }
    }


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
            is ItemSecondItemViewHolder -> holder.bindTo(getItem(position))
        }
    }

}

class ItemSecondItemViewHolder(private val binding: LayoutScrollingTableItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(item: Empty) {
        binding.item = item
    }
}