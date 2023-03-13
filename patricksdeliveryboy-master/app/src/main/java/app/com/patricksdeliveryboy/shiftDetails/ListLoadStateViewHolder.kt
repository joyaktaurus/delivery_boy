package app.com.patricksdeliveryboy.shiftDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import app.com.patricksdeliveryboy.R
import app.com.patricksdeliveryboy.databinding.ListingLoadStateFooterViewItemBinding


class ListLoadStateViewHolder (
    private val binding: ListingLoadStateFooterViewItemBinding,
    retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState !is LoadState.Loading
            binding.errorMsg.isVisible = loadState !is LoadState.Loading
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): ListLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.listing_load_state_footer_view_item, parent, false)
                val binding = ListingLoadStateFooterViewItemBinding.bind(view)
                return ListLoadStateViewHolder(binding, retry)
            }
        }

    }