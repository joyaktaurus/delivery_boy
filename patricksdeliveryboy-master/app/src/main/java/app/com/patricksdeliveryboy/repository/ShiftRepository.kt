package app.com.patricksdeliveryboy.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.com.patricksdeliveryboy.PageUtils
import app.com.patricksdeliveryboy.data.remote.ApiServices
import app.com.patricksdeliveryboy.datasource.ShiftDetailsDataSource
import app.com.patricksdeliveryboy.models.ItemsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShiftRepository @Inject constructor(private val shiftDetailsDataSource: ShiftDetailsDataSource ,
                                          private val apiServices: ApiServices){

    fun getListingStream() :Flow<PagingData<ItemsItem>> {
        return Pager(
            config = PagingConfig(pageSize = PageUtils.PER_PAGE),
            pagingSourceFactory = {shiftDetailsDataSource}
        ).flow
    }

}