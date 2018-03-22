package com.rudearts.randomscore.ui.main

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rudearts.randomscore.R
import com.rudearts.randomscore.databinding.ScoreItemBinding
import com.rudearts.randomscore.model.ScoreItem


class ScoreAdapter(context:Context) : RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var scores = emptyList<ScoreItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = createBinding(parent)
        return ViewHolder(binding)
    }

    private fun createBinding(parent: ViewGroup): ScoreItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.score_item, parent, false)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = scores[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return scores.size
    }

    fun updateItems(items:List<ScoreItem>) {
        scores = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ScoreItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScoreItem) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}