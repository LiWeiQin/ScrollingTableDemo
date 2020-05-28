package cn.changhsqinda.scrollingtable

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cn.changhsqinda.scrollingtable.databinding.ActivityMainBinding
import cn.changhsqinda.scrollingtable.viewmodel.ViewModelActivityMain

class MainActivity : AppCompatActivity() {

    private val viewModelActivityMain: ViewModelActivityMain by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModelActivityMain = this@MainActivity.viewModelActivityMain
        binding.lifecycleOwner = this@MainActivity
        binding.apply {
            scrollingTableContent.apply {
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                            binding.scrollingTableContent.scrollBy(dx, dy)
                        }
                        if (smartRefreshLayout.state.isFinishing) {
                            binding.scrollingTableContent.scrollBy(dx, dy)
                        }
                    }
                })
            }
            scrollingTableContent.apply {
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                            binding.mainScrollingTableRow.scrollBy(dx, dy)
                        }
                        if (smartRefreshLayout.state.isFinishing) {
                            binding.mainScrollingTableRow.scrollBy(dx, dy)
                        }
                    }
                })
            }
            smartRefreshLayout.apply {
                setOnRefreshListener {
                    finishRefresh()
                    setDataResult()
                }
                setOnLoadMoreListener {
                    loadDataResult()
                    finishLoadMore(0, true, false)
                }
            }
            smartRefreshLayout.autoRefresh()
        }
    }

    private fun setDataResult() {
        viewModelActivityMain.setDataResult()
    }

    private fun loadDataResult() {
        viewModelActivityMain.loadDataResult()
    }
}
