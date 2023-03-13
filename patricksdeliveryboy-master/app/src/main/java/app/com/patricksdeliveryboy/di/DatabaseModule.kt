package app.com.patricksdeliveryboy.di

import android.app.Application
import com.inmenzo.patrics.db.LoginDao
import com.inmenzo.patrics.db.PatricsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun providePatricsDb(application: Application?): PatricsDb {
        return PatricsDb.getDatabase(application!!.applicationContext)
    }
    @Provides
    @Singleton
    fun provideLoginDao(db: PatricsDb): LoginDao {
        return db.loginDao()
    }
}