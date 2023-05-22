package es.joshluq.cabishop.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import es.joshluq.cabishop.data.datasource.local.dao.CartDao
import es.joshluq.cabishop.data.datasource.local.entity.ItemEntity

@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}