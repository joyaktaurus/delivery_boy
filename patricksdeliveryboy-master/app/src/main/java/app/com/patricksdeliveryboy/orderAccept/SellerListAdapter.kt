package app.com.patricksdeliveryboy.orderAccept

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.com.patricksdeliveryboy.callbacks.ItemClickListener
import app.com.patricksdeliveryboy.databinding.ItemSellerDetailsBinding
import app.com.patricksdeliveryboy.models.DataItem

class SellerListAdapter(val sellerList : List<DataItem>, private val itemClickListener : ItemClickListener) : RecyclerView.Adapter<SellerListAdapter.SellerListViewHolder>() {

    inner class SellerListViewHolder(private val binding: ItemSellerDetailsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            if(position==0)binding.viewTopDivider.visibility= View.GONE else binding.viewTopDivider.visibility= View.VISIBLE
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(sellerList[position])
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val sellerView = ItemSellerDetailsBinding.inflate(inflater, parent, false)
        return SellerListViewHolder(sellerView)
    }

    override fun onBindViewHolder(holder: SellerListViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return sellerList.size
    }
}

