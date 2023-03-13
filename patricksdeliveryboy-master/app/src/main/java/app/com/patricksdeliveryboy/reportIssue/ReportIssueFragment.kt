package app.com.patricksdeliveryboy.reportIssue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.com.patricksdeliveryboy.R
import app.com.patricksdeliveryboy.home.adapter.RecyclerSuggestionsAdapter
import app.com.patricksdeliveryboy.home.adapter.ReportIssueAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ReportIssueFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var mRecyclerChat : RecyclerView
    lateinit var mRecyclerSuggestions : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.layout_report_issue, container, false)
        initViews(view)
        return view
    }

    fun initViews(view: View){
        mRecyclerChat = view.findViewById(R.id.recycler_chat)
        mRecyclerSuggestions = view.findViewById(R.id.recycler_suggestions)
        mRecyclerChat.layoutManager = LinearLayoutManager(context)
        mRecyclerChat.adapter = ReportIssueAdapter()
        var layoutManager : LinearLayoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mRecyclerSuggestions.layoutManager = layoutManager
        mRecyclerSuggestions.adapter = RecyclerSuggestionsAdapter()

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReportIssueFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}