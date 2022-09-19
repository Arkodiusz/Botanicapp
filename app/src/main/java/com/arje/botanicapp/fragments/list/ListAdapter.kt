package com.arje.botanicapp.fragments.list

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.arje.botanicapp.MainActivity.Companion.selectedPlant
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
        holder.itemView.imageView_list.setImageBitmap(BitmapFactory.decodeFile(currentItem.imagePath))

        holder.itemView.rowLayout.setOnClickListener {
            selectedPlant = currentItem
            holder.itemView.findNavController().navigate(R.id.action_listFragment_to_detailsFragment)
        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    fun setData(plants: List<Plant>) {
        this.plantList = plants
        notifyDataSetChanged()
    }

}