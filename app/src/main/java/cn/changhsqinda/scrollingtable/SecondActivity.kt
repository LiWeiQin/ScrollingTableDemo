package cn.changhsqinda.scrollingtable

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cn.changhsqinda.scrollingtable.adapter.TableSecondAdapter
import cn.changhsqinda.scrollingtable.databinding.ActivitySecondBinding
import cn.changhsqinda.scrollingtable.viewmodel.ViewModelActivitySecond

class SecondActivity : AppCompatActivity() {

    private val viewModelActivitySecond: ViewModelActivitySecond by viewModels()
    private lateinit var binding: ActivitySecondBinding
    private var isDeviationHeight = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)
        binding.viewModelActivitySecond = this@SecondActivity.viewModelActivitySecond
        binding.lifecycleOwner = this@SecondActivity
        binding.apply {
            smartRefreshLayout.apply {
                setOnRefreshListener {
                    finishRefresh()
                    setDataResult()
                }
                setOnLoadMoreListener {
                    isDeviationHeight = true
                    loadDataResult()
                    finishLoadMore(2000, true, false)
                }
            }
            smartRefreshLayout.autoRefresh()
            secondScrollingTableItemContent.setHasFixedSize(true)
            secondScrollingTableItemContent.apply {
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                            binding.secondScrollingTableRow.scrollBy(dx, dy)
                        }
                        if (smartRefreshLayout.state.isFinishing && isDeviationHeight) {
                            isDeviationHeight = false
                            binding.secondScrollingTableRow.scrollBy(dx, dy)
                        }
                    }
                })
            }
            secondScrollingTableRow.apply {
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                            binding.secondScrollingTableItemContent.scrollBy(dx, dy)
                        }
                        if (smartRefreshLayout.state.isFinishing && isDeviationHeight) {
                            isDeviationHeight = false
                            binding.secondScrollingTableItemContent.scrollBy(dx, dy)
                        }
                    }
                })
            }

            secondScrollingTableColumn.apply {
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                            (binding.secondScrollingTableItemContent.adapter as? TableSecondAdapter)
                                ?.syncScrollViewChangeObserver?.onScrolled(recyclerView, dx, dy)
                        }
                    }
                })
            }
        }
    }

    private fun setDataResult() {
        viewModelActivitySecond.setDataResult()
    }

    private fun loadDataResult() {
        viewModelActivitySecond.loadDataResult()
    }
}
