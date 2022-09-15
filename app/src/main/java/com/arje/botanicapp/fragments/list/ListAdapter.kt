package com.arje.botanicapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.arje.botanicapp.R
import com.arje.botanicapp.data.model.Plant
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var plantList = emptyList<Plant>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = plantList[position]
        holder.itemView.txt_plantName.text = currentItem.name

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    fun setData(plants: List<Plant>) {
        this.plantList = plants
//        this.plantList = plants.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
        notifyDataSetChanged()
    }

}