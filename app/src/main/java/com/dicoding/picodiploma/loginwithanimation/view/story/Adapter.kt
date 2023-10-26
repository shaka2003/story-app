package com.dicoding.picodiploma.loginwithanimation.view.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ListStoryBinding

class Adapter(private val clickListener: ItemClickListener) :
    ListAdapter<ListStoryItem, Adapter.MyViewHolder>(DIFF_CALLBACK) {

    interface ItemClickListener {
        fun onItemClick(users: ListStoryItem, view: ListStoryBinding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(users, holder.binding)
        }
    }

    class MyViewHolder(val binding: ListStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: ListStoryItem) {

            binding.apply {
                Glide.with(itemView.context)
                    .load(users.photoUrl)
                    .into(imageStory)

                tvUname.text = users.name
                tvDeskripsi.text = users.description

            }
        }
    }
}