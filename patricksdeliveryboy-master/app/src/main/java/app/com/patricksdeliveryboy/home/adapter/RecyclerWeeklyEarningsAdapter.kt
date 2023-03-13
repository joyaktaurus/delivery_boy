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
import app.com.patricksdeliveryboy.databinding.LayoutShiftDetailsItemBinding
import app.com.patricksdeliveryboy.databinding.LayoutWeeklyEarningsItemBinding
import app.com.patricksdeliveryboy.models.ItemsItem
import app.com.patricksdeliveryboy.models.ItemsItemEarningDetails
import app.com.patricksdeliveryboy.utility.*
import java.util.*

class RecyclerWeeklyEarningsAdapter() : PagingDataAdapter<ItemsItemEarningDetails, RecyclerWeeklyEarningsAdapter.MyViewHolder>(LIST_COMPARATOR) {
    var mSelectedPosition : Int =-1
    var context : Context? = null
    var mCalender : Calendar = Calendar.getInstance()

    inner class MyViewHolder(private val layoutWeeklyEarningsItemBinding: LayoutWeeklyEarningsItemBinding) : RecyclerView.ViewHolder(layoutWeeklyEarningsItemBinding.root){

        fun bind(items: ItemsItemEarningDetails, position: Int){
            if(position == 0){
                layoutWeeklyEarningsItemBinding.viewLine.visibility = View.GONE
            }else{
                layoutWeeklyEarningsItemBinding.viewLine.visibility = View.VISIBLE
            }

            layoutWeeklyEarningsItemBinding.txtAmount.text = items.totalCommissionAmount.toString()
            layoutWeeklyEarningsItemBinding.txtWeekday.text = items.id?.toWeekDayFormat()
            layoutWeeklyEarningsItemBinding.txtDay.text = items.id?.toDayFormat()
            layoutWeeklyEarningsItemBinding.txtDate.text = items.id?.toEarningDateFormat()
            mCalender.time = items.id?.toMilliseconds()!!
        //    Log.e("current_date",getRelativeDay(mCalender,Calendar.getInstance(Locale.getDefault())))
            layoutWeeklyEarningsItemBinding.recyclerEarningsDetails.layoutManager = LinearLayoutManager(context)
            layoutWeeklyEarningsItemBinding.recyclerEarningsDetails.adapter = context?.let { RecyclerEarningDetailsAdapter(items.list!!) }
            if(mSelectedPosition == position){
                layoutWeeklyEarningsItemBinding.recyclerEarningsDetails.visibility = View.VISIBLE
            }else{
                layoutWeeklyEarningsItemBinding.recyclerEarningsDetails.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val weeklyEarningView = LayoutWeeklyEarningsItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(weeklyEarningView)
        /*return ViewHolder(shiftView)
        context = parent.context
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_weekly_earnings_item, parent, false)
        )*/
    }

   /* override fun getItemCount(): Int {
        return 15
    }*/

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item, position)


        /*if(position == 0){
            holder.mViewLine.visibility = View.GONE
        }else{
            holder.mViewLine.visibility = View.VISIBLE
        }
        holder.mRecyclerEarningDetails.layoutManager = LinearLayoutManager(context)
        holder.mRecyclerEarningDetails.adapter = context?.let { RecyclerEarningDetailsAdapter(it) }
        if(mSelectedPosition == position){
            holder.mRecyclerEarningDetails.visibility = View.VISIBLE
        }else{
            holder.mRecyclerEarningDetails.visibility = View.GONE
        }
        holder.itemView.setOnClickListener {
            mSelectedPosition = position
           notifyDataSetChanged()
        }*/

        holder.itemView.setOnClickListener {
            mSelectedPosition = position
            notifyDataSetChanged()
        }
    }

    companion object {
        private val LIST_COMPARATOR = object : DiffUtil.ItemCallback<ItemsItemEarningDetails>() {
            override fun areItemsTheSame(oldItem: ItemsItemEarningDetails, newItem: ItemsItemEarningDetails): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ItemsItemEarningDetails, newItem: ItemsItemEarningDetails): Boolean =
                oldItem == newItem
        }
    }
}

/*
class MyViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    var mRecyclerEarningDetails : RecyclerView = view.findViewById(R.id.recycler_earnings_details)
    var mViewLine : View = view.findViewById(R.id.view_line)
}*/
