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
import cn.changhsqinda.scrollingtable.viewmodel.ViewModelActivityMain

@BindingAdapter(
    value = ["tableColumnAdapterViewModelBind", "tableColumnAdapterDataBind"],
    requireAll = true
)
fun tableColumnAdapterViewModelBind(
    recyclerView: RecyclerView,
    viewModel: ViewModelActivityMain,
    data: List<Empty>?
) {
    if (null == recyclerView.adapter) {
        recyclerView.adapter = TableColumnAdapter(viewModel)
    }
    (recyclerView.adapter as TableColumnAdapter).submitList(data ?: arrayListOf())
}

class TableColumnAdapter(private val viewModel: ViewModelActivityMain) :
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
            R.layout.layout_scrolling_table_item -> ColumnViewHolder(binding =
            LayoutScrollingTableItemBinding.inflate(LayoutInflater.from(parent.context)).apply {
                viewModel = this@TableColumnAdapter.viewModel
            })
            else -> throw IllegalArgumentException("can not find  layout")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ColumnViewHolder -> holder.bindTo(getItem(position))
        }
    }

}

class ColumnViewHolder(val binding: LayoutScrollingTableItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(item: Empty) {
        binding.item = item
    }
}