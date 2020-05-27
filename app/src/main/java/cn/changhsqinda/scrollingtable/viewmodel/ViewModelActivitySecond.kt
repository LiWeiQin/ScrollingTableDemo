package cn.changhsqinda.scrollingtable.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.changhsqinda.scrollingtable.data.Empty

class ViewModelActivitySecond : ViewModel() {

    private var _count = MutableLiveData<Int>().apply {
        value = 9
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
                temp.add(Empty(title = index.toString()))
        }
        temp
    }

    var dataRow = Transformations.map(dataEmpty) {
        val temp = ArrayList<Empty>()
        val count = dataEmpty.value?.let { that ->
            (that.count() / (count.value ?: Int.MAX_VALUE)) + if ((that.count() % (count.value
                    ?: Int.MAX_VALUE)) != 0
            ) {
                1
            } else 0
        } ?: 0
        for (index in 0 until count)
            temp.add(Empty(title = index.toString()))
        temp
    }

    fun setDataResult() {
        _dataEmpty.value = _dataEmpty.value.let {
            val data = arrayListOf<List<Empty>>()
            for (i in 0..(20..100).random()) {
                val temp = arrayListOf<Empty>()
                for (index in 0 until 9) {
                    temp.add(Empty(title = index.toString()))
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
            for (i in 0..(20..100).random()) {
                val temp = arrayListOf<Empty>()
                for (index in 0 until 9) {
                    temp.add(Empty(title = index.toString()))
                }
                data.add(temp)
            }
            data
        }
    }
}