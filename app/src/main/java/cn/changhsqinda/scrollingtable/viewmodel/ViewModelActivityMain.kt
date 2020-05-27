package cn.changhsqinda.scrollingtable.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.changhsqinda.scrollingtable.data.Empty

class ViewModelActivityMain : ViewModel() {

    private var _count = MutableLiveData<Int>().apply {
        value = 9
    }
    var count: LiveData<Int> = _count

    private var _dataEmpty = MutableLiveData<List<Empty>>()
    var dataEmpty: LiveData<List<Empty>> = _dataEmpty

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
            val temp = arrayListOf<Empty>()
            val randoms = (20..100).random()
            for (index in 0 until randoms) {
                temp.add(Empty(title = index.toString()))
            }
            temp
        }
    }

    fun loadDataResult() {
        _dataEmpty.value = _dataEmpty.value.let {
            val temp = arrayListOf<Empty>()
            temp.addAll(it ?: arrayListOf())
            for (index in temp.count() until temp.count() + (20..100).random()) {
                temp.add(Empty(title = index.toString()))
            }
            temp
        }
    }
}