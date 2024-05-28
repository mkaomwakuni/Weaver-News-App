package dev.mkao.weaver.data.remote

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.mkao.weaver.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConvertor {
    @TypeConverter
    fun fromSource(source: Source): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSource(sourceString: String): Source {
        val type = object : TypeToken<Source>() {}.type
        return Gson().fromJson(sourceString, type)
    }
}