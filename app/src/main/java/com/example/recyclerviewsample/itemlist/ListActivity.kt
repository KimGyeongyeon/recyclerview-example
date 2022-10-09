package com.example.recyclerviewsample.itemlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewsample.databinding.ActivityListBinding

/**
 * RecyclerView에 필요한 요소
 * 1. View : Activity나 Fragment
 * 2. Adapter : 여러 종류가 있을 수 있다.
 * 3. ViewHolder : 어댑터 내에 선언되는 경우가 많음. 각 아이템을 다룬다.
 * 4. LayoutManager : Linear, Grid, StaggeredGrid 등의 타입이 있다.
 * */

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private val listViewModel = ListViewModelFactory(this)
        .create(ListViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.recyclerView

        // 1. manager 연결
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager

        // 2. adapter와 뷰 연결
        // Adapter 객체를 선언하고, recyclerView.adapter에 그 객체를 참조하게한다.
        val adapter = TwotypeAdapter()
        recyclerView.adapter = adapter

        // 3. 옵저버 등록해서 data 얻기
        // Factory로 생성을 하고..
        // 그 생성한 객체.listData.observe로 변경될 때 마다 할 동작을 선언한다.
        listViewModel.listLiveData.observe(this) {
            it?.let {
                adapter.submitList(it)
            }
        }

    }
}