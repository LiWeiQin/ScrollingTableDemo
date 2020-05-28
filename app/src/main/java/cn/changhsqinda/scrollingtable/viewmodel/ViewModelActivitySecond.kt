package cn.changhsqinda.scrollingtable.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.changhsqinda.scrollingtable.data.Empty

class ViewModelActivitySecond : ViewModel() {

    private var _count = MutableLiveData<Int>().apply {
        value = 10
    }
    var count: LiveData<Int> = _count

    private var _dataEmpty = MutableLiveData<List<List<Empty>>>()
    var dataEmpty: LiveData<List<List<Empty>>> = _dataEmpty

    var dataColumn = Transformations.map(dataEmpty) {
        val temp = ArrayList<Empty>()
        dataEmpty.value?.let {
            val count = _count.value?.let { that ->
                that
            } ?: 0
            for (index in 0 until count)
                temp.add(Empty(title = "第${index}列"))
        }
        temp
    }

    var dataRow = Transformations.map(dataEmpty) {
        val temp = ArrayList<Empty>()
        val count = dataEmpty.value?.size ?: 0
        for (index in 0 until count)
            temp.add(Empty(title = "第${index}行"))
        temp
    }

    fun setDataResult() {
        _dataEmpty.value = _dataEmpty.value.let {
            val data = arrayListOf<List<Empty>>()
            for (i in 0 until 1500) {
                val temp = arrayListOf<Empty>()
                for (index in 0 until count.value!!) {
                    temp.add(Empty(title = "第${i}行-第${index}个"))
                }
                data.add(temp)
            }
            data
        }
    }

    fun loadDataResult() {
        _dataEmpty.value = _dataEmpty.value.let {
            val data = arrayListOf<List<Empty>>()
            data.addAll(it ?: arrayListOf())
            for (i in data.count() until data.count() + 500) {
                val temp = arrayListOf<Empty>()
                for (index in 0 until count.value!!) {
                    temp.add(Empty(title = "第${i}行-第${index}个"))
                }
                data.add(temp)
            }
            data
        }
    }
}