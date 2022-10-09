package com.example.recyclerviewsample.itemlist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerviewsample.R
import com.example.recyclerviewsample.data.Content
import com.example.recyclerviewsample.data.HeaderContent
import com.example.recyclerviewsample.data.ImageContent

class ListViewModel : ViewModel() {
    // dataSource가 존재한다면 contructor에서 참조를 받자

    private val initData = arrayListOf<Content>(
        HeaderContent("Fruit"),
        ImageContent(R.drawable.apple_square, "apple"),
        ImageContent(R.drawable.grape_square, "grape"),
        ImageContent(R.drawable.orange_square, "orange"),
        ImageContent(R.drawable.cherry_square, "cherry"),
    )
    val listLiveData = MutableLiveData(initData)

    fun getList(): ArrayList<Content> {
        return listLiveData.value ?: arrayListOf()
    }

    fun insertItem(description: String) {
        val currentData = listLiveData.value ?: arrayListOf<Content>()
        currentData.add(0, ImageContent(R.drawable.ic_baseline_question_mark_24, description))
        listLiveData.postValue(currentData)
    }

    fun deleteItem(index: Int) {
        listLiveData.value?.let { toString().toCharArray() }
        val currentData = listLiveData.value ?: return
        val size = currentData.size
        if (index < size) {
            currentData.removeAt(index)
            listLiveData.postValue(currentData)
        }
    }
}

class ListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}