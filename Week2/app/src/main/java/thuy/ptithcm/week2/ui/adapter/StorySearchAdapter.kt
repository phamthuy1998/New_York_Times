package thuy.ptithcm.week2.ui.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_story.view.*
import thuy.ptithcm.week2.R
import thuy.ptithcm.week2.model.Doc
import thuy.ptithcm.week2.model.Story
import thuy.ptithcm.week2.util.IMAGE_URL

class StorySearchAdapter(
    private var listStoriesSearch: ArrayList<Doc>? = arrayListOf(),
//    var adapterEvent: AdapterEvent
    private val itemClick: (doc: Doc) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_story, viewGroup, false)
        StorySearchViewHolder(view)
        return StorySearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listStoriesSearch?.size ?: 0
    }

    fun updateDataSearch(list: ArrayList<Doc>) {
        listStoriesSearch?.addAll(list)
        notifyDataSetChanged()
    }

    fun addDataStoriesSearch(list: ArrayList<Doc>) {
        listStoriesSearch = arrayListOf()
        listStoriesSearch = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.bind(position)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    inner class StorySearchViewHolder(
        itemView: View
    ) : BaseViewHolder<View>(itemView) {
        override fun bind(position: Int) {
            val storySearch = listStoriesSearch?.get(position)
            //set image rounded
            val multi = MultiTransformation<Bitmap>(
                RoundedCornersTransformation(7, 0, RoundedCornersTransformation.CornerType.ALL)
            )
            if (storySearch?.multimedia?.size != 0)
                Glide.with(itemView)
                    .load(IMAGE_URL + storySearch?.multimedia?.get(0)?.url)
                    .error(R.drawable.no_image)
                    .apply(RequestOptions.bitmapTransform(multi))
                    .into(itemView.iv_story)
            else
                Glide.with(itemView)
                    .load(R.drawable.no_image)
                    .apply(RequestOptions.bitmapTransform(multi))
                    .into(itemView.iv_story)
            itemView.tv_title_story.text = storySearch?.headline?.main

            itemView.setOnClickListener {
                storySearch?.let { it1 -> itemClick(it1) }
            }
        }
    }
}