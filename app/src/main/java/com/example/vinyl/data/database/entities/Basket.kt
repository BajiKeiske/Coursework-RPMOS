package com.example.vinyl.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "baskets")
data class Basket(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int
)