package app.com.patricksdeliveryboy.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.com.patricksdeliveryboy.PageUtils
import app.com.patricksdeliveryboy.datasource.EarningDetailsDataSource
import app.com.patricksdeliveryboy.models.ItemsItemEarningDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EarnedDetailsRepository @Inject constructor(private val earningDetailsDataSource: EarningDetailsDataSource) {

    fun getListingStream() : Flow<PagingData<ItemsItemEarningDetails>>{
        return Pager(
            config = PagingConfig(pageSize = PageUtils.PER_PAGE),
            pagingSourceFactory = {earningDetailsDataSource}
        ).flow
    }
}