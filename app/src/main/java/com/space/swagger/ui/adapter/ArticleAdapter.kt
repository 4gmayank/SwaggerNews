package com.space.swagger.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article.view.*
import com.space.swagger.databinding.ItemArticleBinding
import com.space.swagger.model.Article


class ArticleAdapter : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {
    var callback: ArticleAdapter.IArticleCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =  ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)

        holder.binding.articleHeading.text = article.title
        holder.binding.summary.text = article.summary
        holder.binding.channelName.text = article.newsSite
        holder.binding.timestamp.text = article.timePassed()
        holder.itemView.summary.visibility =
            if (article.isSummaryVisible) View.VISIBLE else View.GONE

        Picasso.get()
            .load(article.imageUrl)
            .into(holder.binding.imageView)

        holder.itemView.imageView.setOnTouchListener { v, event ->
            val isSummaryVisible = getItem(holder.adapterPosition).isSummaryVisible
            getItem(holder.adapterPosition).isSummaryVisible = true
            holder.itemView.summary.visibility = View.VISIBLE
            !isSummaryVisible
        }
        holder.itemView.setOnClickListener{ onClick(holder.adapterPosition)}
        holder.itemView.setOnLongClickListener {
            callback?.onItemClick(getItem(holder.adapterPosition).articleUrl)
            true
        }

    }

    private fun onClick(itemPosition: Int): Boolean{
        callback?.onItemClick(getItem(itemPosition).articleUrl)
        return true
    }

    interface IArticleCallback {
        fun onItemClick(url: String)
    }

    inner class ArticleViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

}

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}
