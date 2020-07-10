package com.example.enedilim.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.enedilim.database.Letter
import com.example.enedilim.databinding.ListCompletedTasksBinding

class LetterAdapter : ListAdapter<Letter,
        LetterAdapter.ViewHolder>(LetterTaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListCompletedTasksBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Letter) {
            binding.task = item
            binding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListCompletedTasksBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
class LetterTaskDiffCallback : DiffUtil.ItemCallback<Letter>(){

    override fun areItemsTheSame(oldItem: Letter, newItem: Letter): Boolean {
        return oldItem.letterId == newItem.letterId
    }

    override fun areContentsTheSame(oldItem: Letter, newItem: Letter): Boolean {
        return oldItem == newItem
    }
}