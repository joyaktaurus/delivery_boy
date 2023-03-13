package app.com.patricksdeliveryboy.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.com.patricksdeliveryboy.data.remote.ApiServices
import app.com.patricksdeliveryboy.models.ItemsItemEarningDetails
import javax.inject.Inject

class EarningDetailsDataSource @Inject constructor(private val apiServices: ApiServices) :
    PagingSource<Int, ItemsItemEarningDetails>() {
    override fun getRefreshKey(state: PagingState<Int, ItemsItemEarningDetails>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemsItemEarningDetails> {
        return try {
            val nextPage = params.key ?: 1
            val shiftResponse = apiServices.getEarnedDetails(nextPage)

            LoadResult.Page(
                data = shiftResponse.data!!.items!!,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (shiftResponse.data?.hasNextPage!!) nextPage + 1 else null
            )
        }catch (e: Exception){
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}