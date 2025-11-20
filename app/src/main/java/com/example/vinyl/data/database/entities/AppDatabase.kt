package com.example.vinyl.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.vinyl.data.database.entities.Basket
import com.example.vinyl.data.database.entities.BasketProduct
import com.example.vinyl.data.database.entities.Product

@Database(
    entities = [Product::class, Basket::class, BasketProduct::class], // добавили BasketProduct
    version = 3, // увеличили версию
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun basketDao(): BasketDao
    abstract fun basketProductDao(): BasketProductDao // добавили новый DAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vinyl_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}