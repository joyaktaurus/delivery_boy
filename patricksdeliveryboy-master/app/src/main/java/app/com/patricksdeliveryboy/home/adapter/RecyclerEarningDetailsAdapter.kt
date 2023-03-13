package app.com.patricksdeliveryboy.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.com.patricksdeliveryboy.R
import app.com.patricksdeliveryboy.databinding.LayoutEarningsDetailsItemBinding
import app.com.patricksdeliveryboy.databinding.LayoutWeeklyEarningsItemBinding
import app.com.patricksdeliveryboy.models.ListItemEarningDetails
import app.com.patricksdeliveryboy.utility.toShiftTimeFormat

class RecyclerEarningDetailsAdapter(val listItemEarningDetails: List<ListItemEarningDetails>) :
    RecyclerView.Adapter<MyEarningDetailsViewHolder>() {
    var context : Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEarningDetailsViewHolder {

        context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val earningDetailView = LayoutEarningsDetailsItemBinding.inflate(inflater, parent, false)
        return MyEarningDetailsViewHolder(earningDetailView)
    }

    override fun getItemCount(): Int {
        return listItemEarningDetails.size;
    }

    override fun onBindViewHolder(holder: MyEarningDetailsViewHolder, position: Int) {
        /*when(position){
            0 -> holder.mTopView.visibility = View.VISIBLE
            else -> holder.mTopView.visibility = View.GONE
        }*/

        holder.bind(listItemEarningDetails.get(position), position)
    }
}

class MyEarningDetailsViewHolder(val earningDetailViewBinding : LayoutEarningsDetailsItemBinding) : RecyclerView.ViewHolder(earningDetailViewBinding.root){
    fun bind(listItemEarningDetails: ListItemEarningDetails, position: Int){
        when(position){
            0 -> earningDetailViewBinding.viewTop.visibility = View.VISIBLE
            else -> earningDetailViewBinding.viewTop.visibility = View.GONE

        }

        earningDetailViewBinding.txtShopName.text = listItemEarningDetails.firstName + " "+listItemEarningDetails.lastName
        earningDetailViewBinding.txtAmount.text = listItemEarningDetails.commissionAmount.toString()
        earningDetailViewBinding.txtTime.text = listItemEarningDetails.deliveredAt?.toShiftTimeFormat()
    }
}