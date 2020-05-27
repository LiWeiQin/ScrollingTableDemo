package cn.changhsqinda.scrollingtable

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cn.changhsqinda.scrollingtable.databinding.ActivityMainBinding
import cn.changhsqinda.scrollingtable.databinding.ActivitySecondBinding
import cn.changhsqinda.scrollingtable.viewmodel.ViewModelActivityMain
import cn.changhsqinda.scrollingtable.viewmodel.ViewModelActivitySecond

class SecondActivity : AppCompatActivity() {

    private val viewModelActivitySecond: ViewModelActivitySecond by viewModels()
    private lateinit var binding: ActivitySecondBinding

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
                    loadDataResult()
                    finishLoadMore(0, true, false)
                }
            }
            smartRefreshLayout.autoRefresh()
        }
    }

    private fun setDataResult() {
        viewModelActivitySecond.setDataResult()
    }

    private fun loadDataResult() {
        viewModelActivitySecond.loadDataResult()
    }
}
