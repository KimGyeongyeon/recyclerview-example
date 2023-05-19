package com.example.recyclerviewsample.itemlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewsample.data.Content
import com.example.recyclerviewsample.data.ContentType
import com.example.recyclerviewsample.data.HeaderContent
import com.example.recyclerviewsample.data.ImageContent
import com.example.recyclerviewsample.databinding.ItemTypeHeaderBinding
import com.example.recyclerviewsample.databinding.ItemTypeImageBinding

/* 데이터는 생성자가 아니라 Activity에서 submitList()를 호출하면 그 때 받는다 */
class TwotypeAdapter :
    ListAdapter<Content, RecyclerView.ViewHolder>(ContentDiffCallback) {

    /* 각 타입에 대한 뷰 홀더 선언 */

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Adapter에서 정의한 Header의 layout을 itemView 속성으로 얻을 수 있다.
        private val binding = ItemTypeHeaderBinding.bind(view)
        val titleView = binding.titleTextView

        fun bind(content: HeaderContent) {
            titleView.text = content.title
        }
    }

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTypeImageBinding.bind(view)
        val image = binding.itemImage
        val description = binding.itemText

        fun bind(content: ImageContent) {
            image.setImageResource(content.imageResourceId)
            description.text = content.description
        }
    }
    
    /* 어댑터 주요 동작 구현 */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // ViewHolder 객체를 생성하고 xml과 연결함.
        // viewType 인식하려면? getItemViewType() 오버라이드
        
        return when(viewType){
            ContentType.HEADER.code -> {
                val view = ItemTypeHeaderBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false) // LayoutInflater.from(Context)는 오작동함
                HeaderViewHolder(view.root)
            }
            else -> {
                val view = ItemTypeImageBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                ImageViewHolder(view.root)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val content = getItem(position)
        return if(content is HeaderContent){
            ContentType.HEADER.code
        } else {
            ContentType.IMAGE.code
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 리스트의 현재 데이터를 holder.bind 메서드에 전달함
        val content = getItem(position)?:return
        if(content is HeaderContent){
            val headerHolder = holder as HeaderViewHolder
            headerHolder.bind(content)
        } else if (content is ImageContent) {
            val imageHolder = holder as ImageViewHolder
            imageHolder.bind(content)
        }
    }
}

/**
 * ListAdapter는 생성자에 DiffUtil을 상속받은 object를 꼭 넣어줘야한다.
 * RecyclerView.Adapter는 필요 없음
 * */
object ContentDiffCallback : DiffUtil.ItemCallback<Content>() {
    override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
        return if(oldItem.type == newItem.type){
            oldItem == newItem
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
        return if(oldItem.type == newItem.type){
            oldItem == newItem
        } else {
            false
        }
    }
}
