package app.com.patricksdeliveryboy.shiftDetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.com.patricksdeliveryboy.R
import app.com.patricksdeliveryboy.databinding.LayoutShiftDetailsBinding
import app.com.patricksdeliveryboy.home.adapter.RecyclerShiftDetailsAdapter
import app.com.patricksdeliveryboy.home.adapter.RecyclerWeeklyEarningsAdapter
import app.com.patricksdeliveryboy.utility.showLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShiftDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ShiftDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: LayoutShiftDetailsBinding
    private val viewModel : ShiftDetailsViewModel by viewModels()
    private lateinit var adapter: RecyclerShiftDetailsAdapter

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
        binding = LayoutShiftDetailsBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews(){
        binding.recyclerShiftDetails.layoutManager = LinearLayoutManager(context)
        adapter = RecyclerShiftDetailsAdapter().apply {
            withLoadStateFooter(ListingLoadStateAdapter { retry() })
        }
        binding.recyclerShiftDetails.adapter = adapter
        manageProgress()
        lifecycleScope.launch {
            viewModel.dataListFlow.collectLatest {
                showLoading(false)
                adapter.submitData(it)
            }
        }
    }

    private fun manageProgress() {
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                showLoading(true)
            } else {
                showLoading(false)

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShiftDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}