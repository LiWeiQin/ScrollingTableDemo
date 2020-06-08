package cn.changhsqinda.scrollingtable

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cn.changhsqinda.scrollingtable.databinding.ActivityThreeBinding
import cn.changhsqinda.scrollingtable.viewmodel.ViewModelActivityThree

class ThreeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThreeBinding
    private var isDeviationHeight = false
    private val viewModelActivityThree: ViewModelActivityThree by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_three)
        binding.viewModelActivityThree = this@ThreeActivity.viewModelActivityThree
        binding.lifecycleOwner = this@ThreeActivity
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
            syncScrollViewColumn.apply {
                setScrollView(syncScrollViewContent)
            }
            syncScrollViewContent.apply {
                setScrollView(syncScrollViewColumn)
            }
            smartRefreshLayout.autoRefresh()
        }
    }

    private fun setDataResult() {
        viewModelActivityThree.setDataResult()
    }

    private fun loadDataResult() {
        viewModelActivityThree.loadDataResult()
    }
}
