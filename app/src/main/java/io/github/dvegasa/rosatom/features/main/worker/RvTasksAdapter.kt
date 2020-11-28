package io.github.dvegasa.rosatom.features.main.worker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.dvegasa.rosatom.R
import kotlinx.android.synthetic.main.item_shorttask.view.*

/**
 * Created by Ed Khalturin @DVegasa
 */
class RvTasksAdapter(var list: ArrayList<Task>) : RecyclerView.Adapter<RvTasksAdapter.VH>() {
    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_shorttask, parent, false)
        return VH(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.apply {
            tvTitle.text = list[position].title
            tvLocation.text = list[position].location
            tvTime.text = list[position].time
        }
    }
}