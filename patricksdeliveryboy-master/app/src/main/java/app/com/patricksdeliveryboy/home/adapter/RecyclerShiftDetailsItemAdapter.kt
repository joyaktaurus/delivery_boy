package app.com.patricksdeliveryboy.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.com.patricksdeliveryboy.R
import app.com.patricksdeliveryboy.databinding.LayoutShiftDetailsItemBinding
import app.com.patricksdeliveryboy.databinding.LayoutShiftTimeDetailsItemBinding
import app.com.patricksdeliveryboy.models.ListItem
import app.com.patricksdeliveryboy.utility.toShiftTimeFormat

class RecyclerShiftDetailsItemAdapter(val context: Context, val listItems : List<ListItem>) :
    RecyclerView.Adapter<ShiftDetailsItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftDetailsItemViewHolder {

        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val shiftView = LayoutShiftTimeDetailsItemBinding.inflate(inflater, parent, false)

        return ShiftDetailsItemViewHolder(shiftView)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ShiftDetailsItemViewHolder, position: Int) {
        holder.bind(listItems.get(position))
    }
}

class ShiftDetailsItemViewHolder(private val binding: LayoutShiftTimeDetailsItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(listItems: ListItem){
        binding.data = listItems
        binding.txtInTime.text = listItems.startTime?.toShiftTimeFormat()
        binding.txtOutTime.text = listItems.endTime?.toShiftTimeFormat()
    }
}