package app.com.patricksdeliveryboy.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.com.patricksdeliveryboy.R

class RecyclerSuggestionsAdapter() :
    RecyclerView.Adapter<SuggestionsItemViewHolder>() {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionsItemViewHolder {
        context = parent.context
        return SuggestionsItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_suggestions_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: SuggestionsItemViewHolder, position: Int) {

    }
}

class SuggestionsItemViewHolder(view : View) : RecyclerView.ViewHolder(view){

}