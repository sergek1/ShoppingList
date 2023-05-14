package com.example.shoppinglist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.data.db.entities.ShoppingItem

@Database(
    entities = [ShoppingItem::class],
    version = 1 // обновлять каждый раз когда обновляется бд
)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun getShoppingDao() : ShoppingDao

    companion object {

        @Volatile // права на экзмепляр имеет множество потоков
        private var instance: ShoppingDatabase? = null // только один единственный экземпляр класса должен быть

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) { // вызывается каждый раз когда создаем объект класс
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            ShoppingDatabase::class.java, "ShoppingDB.db").build()
    }
}