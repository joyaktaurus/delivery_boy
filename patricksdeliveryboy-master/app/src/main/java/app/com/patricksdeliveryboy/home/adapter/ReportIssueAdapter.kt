package app.com.patricksdeliveryboy.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.com.patricksdeliveryboy.R

class ReportIssueAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return if(viewType ==1){
            ReportIssueSenderViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_report_issue_send_item, parent, false))
        }else{
            ReportIssueReceiverViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_report_issue_received_item, parent, false))
        }
    }

    override fun getItemCount(): Int {
       return 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            1;
        }else{
            2;
        }
    }
}

class ReportIssueSenderViewHolder(view: View) : RecyclerView.ViewHolder(view){

}

class ReportIssueReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view){

}