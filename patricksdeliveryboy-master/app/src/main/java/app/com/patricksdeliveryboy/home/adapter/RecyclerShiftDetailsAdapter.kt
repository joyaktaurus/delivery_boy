package app.com.patricksdeliveryboy.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.com.patricksdeliveryboy.R
import app.com.patricksdeliveryboy.databinding.LayoutShiftDetailsBinding
import app.com.patricksdeliveryboy.databinding.LayoutShiftDetailsItemBinding
import app.com.patricksdeliveryboy.models.ItemsItem
import app.com.patricksdeliveryboy.utility.toShiftDateFormat

class RecyclerShiftDetailsAdapter() :
    PagingDataAdapter<ItemsItem, RecyclerShiftDetailsAdapter.ViewHolder>(LIST_COMPARATOR) {
    var context: Context? = null

    inner class ViewHolder(private val layoutShiftDetailsItemBinding: LayoutShiftDetailsItemBinding) : RecyclerView.ViewHolder(layoutShiftDetailsItemBinding.root) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        fun bind(items: ItemsItem) {
            layoutShiftDetailsItemBinding.txtDay.text = items.id?.toShiftDateFormat()
            layoutShiftDetailsItemBinding.data = items
            layoutShiftDetailsItemBinding.recyclerShiftDetailsItem.layoutManager = LinearLayoutManager(context)
            layoutShiftDetailsItemBinding.recyclerShiftDetailsItem.adapter = context?.let { RecyclerShiftDetailsItemAdapter(it, items.list!!) }
          //  listItemViewQuoteBinding.quotes = itemQuote
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*context = parent.context
        return RecyclerShiftDetailsAdapter.ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_shift_details_item, parent, false)
        )*/

        context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val shiftView = LayoutShiftDetailsItemBinding.inflate(inflater, parent, false)

        return ViewHolder(shiftView)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("data_logged","logged")
        val item = getItem(position)!!
        holder.bind(item)
        /*holder.mRecyclerShiftDetailsItem.layoutManager = LinearLayoutManager(context)
        holder.mRecyclerShiftDetailsItem.adapter = context?.let { RecyclerShiftDetailsItemAdapter(it) }*/
    }


    companion object {
        private val LIST_COMPARATOR = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean =
                oldItem == newItem
        }
    }

}


