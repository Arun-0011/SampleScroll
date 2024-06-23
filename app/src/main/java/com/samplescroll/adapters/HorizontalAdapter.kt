package com.samplescroll.adapters

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.samplescroll.models.ImagesModel
import com.samplescroll.databinding.HorizontalListBinding

class HorizontalAdapter(
    private var activity: Activity,
    private var list: ArrayList<ImagesModel> = ArrayList()
) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

    inner class ViewHolder(internal val binding: HorizontalListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HorizontalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            with(list[position]) {
                binding.apply {
                    ivImage.let {
                        Glide.with(activity).load(download_url)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .skipMemoryCache(true).priority(Priority.IMMEDIATE)
                            .listener(object : RequestListener<Drawable> {
                                override fun onResourceReady(
                                    resource: Drawable,
                                    model: Any,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }

                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }
                            }).into(it)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(newList: List<ImagesModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
