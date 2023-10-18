package ru.practicum.sprint11koh9

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class NewsResponse(
    val result: String,
    val data: Data
)

data class Data(
    val title: String,
    val items: List<NewsItem>
)
//{
//    "result": "success",
//    "data": {
//    "title": "Some news",
//    "items": [
//    {
//        "id": 1,
//        "title": "Спортивная новость",
//        "type": "sport",
//        "created": "2023-01-01T11:12:13:123",
//        "specificPropertyForSport": "Англия - Франция"
//    },
//    {
//        "id": 2,
//        "title": "Научная новость",
//        "type": "science",
//        "created": "2023-01-01T11:12:13:123",
//        "specific_property_for_science": "https://avatars.dzeninfra.ru/get-zen_doc/58826/pub_61448c8d60d6b95713361fe5_61448cdae52b9105ca86c4bc/scale_1200"
//    }
//    ]
//}
//}
//data class NewsItem(
//    val id: String,
//    val title: String,
//    val type: String,
//    val created: Date,
//    val specificPropertyForSport: String?,
//    val specific_property_for_science: String?,
//)

sealed class NewsItem {
    abstract val id: String
    abstract val title: String
    abstract val type: String
    abstract val created: Date

    data class Sport(
        override val id: String,
        override val title: String,
        override val type: String,
        override val created: Date,
        val specificPropertyForSport: String,
    ) : NewsItem()

    data class Science(
        override val id: String,
        override val title: String,
        override val type: String,
        override val created: Date,
        @SerializedName("specific_property_for_science")
        val specificPropertyForScience: String
    ) : NewsItem()

    data class Unknown(
        override val id: String,
        override val title: String,
        override val type: String,
        override val created: Date,
    ) : NewsItem()

}


class CustomDateTypeAdapter : TypeAdapter<Date>() {

    // https://ru.wikipedia.org/wiki/ISO_8601
    companion object {
        const val FORMAT_PATTERN = "yyyy-MM-DD'T'hh:mm:ss:SSS"
    }

    private val formatter = SimpleDateFormat(FORMAT_PATTERN, Locale.getDefault())
    override fun write(out: JsonWriter, value: Date) {
        out.value(formatter.format(value))
    }

    override fun read(`in`: JsonReader): Date {
        return formatter.parse(`in`.nextString())
    }

}

class NewsItemTypeAdapter : JsonDeserializer<NewsItem> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): NewsItem {
        val typePrimitive = json.asJsonObject.getAsJsonPrimitive("type")
        val type = typePrimitive.asString
//        val sportClass: Class<NewsItem.Sport> = NewsItem.Sport::class.java
//        sportClass.getConstructor()
        return when (type) {
            "sport" -> context.deserialize(json, NewsItem.Sport::class.java)
            "science" -> context.deserialize(json, NewsItem.Science::class.java)
            else -> context.deserialize(json, NewsItem.Unknown::class.java)
        }

    }

}