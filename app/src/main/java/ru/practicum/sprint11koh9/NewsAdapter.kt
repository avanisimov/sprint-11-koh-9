package ru.practicum.sprint11koh9

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.DateFormat

class NewsAdapter : RecyclerView.Adapter<NewsItemViewHolder>() {

    var items: List<NewsItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}


class NewsItemViewHolder(
    parentView: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.v_news_item, parentView, false)
) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val created: TextView = itemView.findViewById(R.id.created)
    private val image: ImageView = itemView.findViewById(R.id.image)

    fun bind(item: NewsItem) {
        created.text =
            DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.SHORT
            ).format(item.created)
        when (item) {
            is NewsItem.Science -> {
                title.text = item.title
                Glide.with(itemView.context)
                    .load(item.specificPropertyForScience)
                    .into(image)
            }

            is NewsItem.Sport -> {
                title.text = "${item.title} ${item.specificPropertyForSport}"
            }

            is NewsItem.Unknown -> {
                title.text = item.title
            }
        }


    }
}