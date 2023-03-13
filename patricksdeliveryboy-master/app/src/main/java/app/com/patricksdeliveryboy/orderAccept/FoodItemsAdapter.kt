package app.com.patricksdeliveryboy.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.com.patricksdeliveryboy.databinding.LayoutFoodItemsBinding
import app.com.patricksdeliveryboy.models.DataItemOrder

class FoodItemsAdapter(private val foodItems: List<DataItemOrder>) : RecyclerView.Adapter<FoodItemsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val foodItemsView = LayoutFoodItemsBinding.inflate(inflater, parent, false)
        return FoodItemsViewHolder(foodItemsView)
    }

    override fun getItemCount(): Int {
        return foodItems.size;
    }

    override fun onBindViewHolder(holder: FoodItemsViewHolder, position: Int) {
        holder.bind(foodItems[position])
    }
}

class FoodItemsViewHolder(var binding : LayoutFoodItemsBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(foodItem : DataItemOrder){
        binding.data = foodItem
    }
}