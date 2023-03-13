package app.com.patricksdeliveryboy.orderAccept

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import app.com.patricksdeliveryboy.R
import app.com.patricksdeliveryboy.ReferenceIds
import app.com.patricksdeliveryboy.callbacks.ItemClickListener
import app.com.patricksdeliveryboy.databinding.FragmentOrderAcceptBinding
import app.com.patricksdeliveryboy.home.adapter.FoodItemsAdapter
import app.com.patricksdeliveryboy.models.DataItem
import app.com.patricksdeliveryboy.models.DataItemOrder
import app.com.patricksdeliveryboy.models.PostAcceptRejectStatus
import app.com.patricksdeliveryboy.models.SellerIdsItem
import app.com.patricksdeliveryboy.shiftDetails.ShiftDetailsViewModel
import app.com.patricksdeliveryboy.utility.getRoundOffValue
import app.com.patricksdeliveryboy.utility.showLoading
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderAcceptFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class OrderAcceptFragment : Fragment(), ItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentOrderAcceptBinding
    private val viewModel : OrderAcceptViewModel by viewModels()
    lateinit var dialog: Dialog
    lateinit var mButThank: AppCompatButton
    lateinit var adapter: OrderSellerDetailsAdapter
    lateinit var adapterSellerList: SellerListAdapter
    lateinit var adapterFoodItems: FoodItemsAdapter
    private var referencId = ""

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
        // Inflate the layout for this fragment
        binding = FragmentOrderAcceptBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        getOrderRequestDetails()
        binding.viewAcceptOrder.recyclerSellers.layoutManager =
                LinearLayoutManager(requireContext())

        binding.viewSellerList.recyclerSellers.layoutManager = LinearLayoutManager(requireContext())

        binding.viewPickComplete.recyclerFoodItems.layoutManager = LinearLayoutManager(requireContext())

        binding.viewAcceptOrder.butAcceptOrder.setOnClickListener {
                acceptOrRejectOrder("accepted")
        }

        binding.viewAcceptOrder.butRejectOrder.setOnClickListener {
            acceptOrRejectOrder("rejected")
        }

        binding.viewPickComplete.butPickComplete.setOnClickListener {
            binding.viewPickComplete.root.visibility = View.GONE
            binding.viewReached.root.visibility = View.VISIBLE
        }

        binding.viewReached.butReached.setOnClickListener {
            binding.viewReached.root.visibility = View.GONE
            binding.viewDelivered.root.visibility = View.VISIBLE
        }

        binding.viewDelivered.butDelivered.setOnClickListener {
            showCompletedDialog()
        }
    }

    private fun setOrderSellerDetailsAdapter(sellerList : List<SellerIdsItem>){
        adapter = OrderSellerDetailsAdapter(sellerList)
        binding.viewAcceptOrder.recyclerSellers.adapter = adapter
    }

    private fun setSellerListAdapter(sellerList: List<DataItem>){
        adapterSellerList = SellerListAdapter(sellerList, this)
        binding.viewSellerList.recyclerSellers.adapter = adapterSellerList
    }

    private fun setRecyclerFoodItemsAdapter(foodList: List<DataItemOrder>){
        adapterFoodItems = FoodItemsAdapter(foodList)
        binding.viewPickComplete.recyclerFoodItems.adapter = adapterFoodItems
    }

    private fun getOrderRequestDetails(){
        try {
            Log.e("referenceId", ReferenceIds.ReferenceId!!)
            if(ReferenceIds.ReferenceId.isNotEmpty()) {
                viewModel.getOrderRequestDetails("605c7f25f1cc9c67d11b9038", {
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                    referencId = it.data?.id!!
                    setOrderSellerDetailsAdapter(it.data?.sellerIds!!)
                    binding.viewAcceptOrder.txtKm.text = getRoundOffValue(it.data?.totalDistance!!)+"KM"
                    binding.viewAcceptOrder.txtCommission.text = "\u20B9"+getRoundOffValue(it.data?.totalCommission!!)
                    binding.viewAcceptOrder.txtOrderNumber.text = it.data?.cartId?.orderId.toString()
                }, {

                })
            }
            ReferenceIds.ReferenceId = ""
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun acceptOrRejectOrder(status: String){
        showLoading(true)
        viewModel.updateOrderAcceptRejectStatus(referencId, PostAcceptRejectStatus(status),{
            Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
            showLoading(false)
            binding.viewAcceptOrder.root.visibility = View.GONE
            binding.viewSellerList.root.visibility = View.VISIBLE
            setSellerListAdapter(it.data!! as List<DataItem>)
        },{
            showLoading(false)
        })
    }

    private fun getOrderedItemDetails(sellerId: String){
        showLoading(true)
        viewModel.getOrderedItemDetails(referencId, sellerId,{
            Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
            setRecyclerFoodItemsAdapter(it.data!!)
            showLoading(false)
        },{
            showLoading(false)
        })
    }

    private fun showCompletedDialog() {
        //       activityHomeBinding.includeContentHome.viewDelivered.visibility = View.GONE
        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dialog)
        dialog.getWindow()?.getDecorView()?.setBackgroundResource(android.R.color.transparent);
        mButThank = dialog.findViewById(R.id.but_thank)
        mButThank.setOnClickListener {
            dismissDialog()
        }
        dialog.show()
    }

    private fun dismissDialog() {
        dialog.dismiss()
        activity?.finish()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderAcceptFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderAcceptFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(data: DataItem) {
        binding.viewSellerList.root.visibility = View.GONE
        binding.viewPickComplete.root.visibility = View.VISIBLE
        getOrderedItemDetails(data.id!!)
    }
}