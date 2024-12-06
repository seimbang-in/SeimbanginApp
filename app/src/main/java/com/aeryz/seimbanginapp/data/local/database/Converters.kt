package com.aeryz.seimbanginapp.data.local.database

import androidx.room.TypeConverter
import com.aeryz.seimbanginapp.model.ProductItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromProductItemList(value: List<ProductItem>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toProductItemList(value: String?): List<ProductItem>? {
        val listType = object : TypeToken<List<ProductItem>>() {}.type
        return gson.fromJson(value, listType)
    }
}
