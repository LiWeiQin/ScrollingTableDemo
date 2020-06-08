package cn.changhsqinda.scrollingtable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.changhsqinda.scrollingtable.R
import cn.changhsqinda.scrollingtable.data.Empty
import cn.changhsqinda.scrollingtable.databinding.LayoutScrollingTableThreeItemBinding
import cn.changhsqinda.scrollingtable.viewmodel.ViewModelActivityThree
import java.util.ArrayList

@BindingAdapter(
    value = ["tableItemAdapterViewModelThreeBind", "tableItemThreeAdapterDataBind"],
    requireAll = false
)
fun tableItemAdapterViewModelThreeBind(
    recyclerView: RecyclerView, viewModel: ViewModelActivityThree,
    data: List<List<Empty>>?
) {
    if (null == recyclerView.adapter) {
        recyclerView.adapter = TableThreeAdapter(viewModel)
    }
    (recyclerView.adapter as TableThreeAdapter).submitList(data)
}

class TableThreeAdapter(
    private val viewModel: ViewModelActivityThree,
    private var data: List<List<Empty>>? = arrayListOf()
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = R.layout.layout_scrolling_table_three_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.layout_scrolling_table_three_item ->
                ItemThreeViewHolder(
                    binding =
                    LayoutScrollingTableThreeItemBinding.inflate(LayoutInflater.from(parent.context))
                        .apply {
                            viewModel = this@TableThreeAdapter.viewModel
                        }
                )
            else -> throw IllegalArgumentException("can not find  layout")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemThreeViewHolder -> {
                holder.bindTo(data?.get(position) ?: arrayListOf())
            }
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun submitList(data: List<List<Empty>>?) {
        data?.let {
            this.data = it
            notifyDataSetChanged()
        }
    }
}

class ItemThreeViewHolder(
    val binding: LayoutScrollingTableThreeItemBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(item: List<Empty>) {
        binding.item = item as ArrayList<Empty>
    }
}

