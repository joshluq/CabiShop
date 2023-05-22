package es.joshluq.cabishop.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.joshluq.cabishop.data.datasource.AppDatabase
import es.joshluq.cabishop.data.datasource.local.dao.CartDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "DATABASE"
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideCartDao(appDatabase: AppDatabase): CartDao {
        return appDatabase.cartDao()
    }
}