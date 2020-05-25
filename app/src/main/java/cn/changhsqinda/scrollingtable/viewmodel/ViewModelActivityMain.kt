package cn.changhsqinda.scrollingtable.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.changhsqinda.scrollingtable.data.Empty
import kotlin.math.ceil

class ViewModelActivityMain : ViewModel() {

    private var _count = MutableLiveData<Int>().apply {
        value = 9
    }
    var count: LiveData<Int> = _count

    private var _dataEmpty = MutableLiveData<List<Empty>>().apply {
        value = arrayListOf()
    }
    var dataEmpty: LiveData<List<Empty>> = _dataEmpty

    var dataColumn = Transformations.map(count) {
        val temp = ArrayList<Empty>()
        val count = _count.value?.let { that ->
            that
        } ?: 0
        for (index in 0..count)
            temp.add(Empty(title = index.toString()))
        temp
    }

    var dataRow = Transformations.map(dataEmpty) {
        val temp = ArrayList<Empty>()
        val count = dataEmpty.value?.let { that ->
            ceil((that.count() / (count.value ?: Int.MAX_VALUE)).toDouble()).toInt()
        } ?: 0
        for (index in 0..count)
            temp.add(Empty(title = index.toString()))
        temp
    }

    fun emptyDataInit() {
        _dataEmpty.value = _dataEmpty.value?.let {
            val temp = arrayListOf<Empty>()
            val randoms = (100..200).random()
            for (index in 0..randoms) {
                temp.add(Empty(title = index.toString()))
            }
            temp
        }
    }

}