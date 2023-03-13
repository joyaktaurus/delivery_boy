package app.com.patricksdeliveryboy.orderAccept

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.com.patricksdeliveryboy.databinding.ItemSellerDetailsBinding
import app.com.patricksdeliveryboy.models.SellerIdsItem

class OrderSellerDetailsAdapter(var listSellers : List<SellerIdsItem>) : RecyclerView.Adapter<SellerDetailsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val sellerView = ItemSellerDetailsBinding.inflate(inflater, parent, false)
        return SellerDetailsViewHolder(sellerView)
    }

    override fun onBindViewHolder(holder: SellerDetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return listSellers.size
    }

}

class SellerDetailsViewHolder(private val binding: ItemSellerDetailsBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(position: Int){
        if(position==0)binding.viewTopDivider.visibility= View.GONE else binding.viewTopDivider.visibility= View.VISIBLE

    }
}