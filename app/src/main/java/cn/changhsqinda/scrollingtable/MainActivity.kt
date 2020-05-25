package cn.changhsqinda.scrollingtable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
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
        viewModelActivityMain.emptyDataInit()
    }
}
