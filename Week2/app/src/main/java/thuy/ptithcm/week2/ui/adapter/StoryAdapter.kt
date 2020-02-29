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

class StoryAdapter(
    private var listSories: ArrayList<Story>? = arrayListOf(),
    private var listSoriesSearch: ArrayList<Doc>? = arrayListOf(),
    var adapterEvent: AdapterEvent
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {
        private const val TYPE_NORMAL = 0
        private const val TYPE_SEARCH = 1
    }

    private lateinit var story: Story
    private lateinit var storySearch: Doc
    private var isSearch = false

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {

        return when (viewType) {
            TYPE_NORMAL -> {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.item_story, viewGroup, false);
                StoryViewholder(view)
            }
            TYPE_SEARCH -> {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.item_story, viewGroup, false);
                StorySearchViewholder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isSearch) TYPE_SEARCH
        else TYPE_NORMAL
    }

    fun setSearch(search: Boolean) {
        isSearch = search
    }

    override fun getItemCount(): Int {
        return listSories?.size ?: 0
    }

    fun getItemCountListSearch(): Int {
        return listSoriesSearch?.size ?: 0
    }

    fun updateDataStories(list: ArrayList<Story>) {
        listSories?.addAll(list ?: arrayListOf())
        notifyDataSetChanged()
    }

    fun updateDataStoriesSearch(list: ArrayList<Doc>) {
        listSoriesSearch?.addAll(list ?: arrayListOf())
        notifyDataSetChanged()
    }

    fun addDataStories(list: ArrayList<Story>) {
        listSories = list
        notifyDataSetChanged()
    }

    fun addDataStoriesSearch(list: ArrayList<Doc>) {
        listSoriesSearch = list
        notifyDataSetChanged()
    }

    fun removeAllDataStories() {
        listSories = arrayListOf()
        notifyDataSetChanged()
    }

    fun removeAllDataStoriesSearch() {
        listSoriesSearch = arrayListOf()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.bind(position)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    inner class StoryViewholder(
        itemView: View
    ) : BaseViewHolder<View>(itemView) {
        override fun bind(position: Int) {
            story = listSories?.get(position) ?: story
            //set image rounded
            val multi = MultiTransformation<Bitmap>(
                RoundedCornersTransformation(7, 0, RoundedCornersTransformation.CornerType.ALL)
            )
            Glide.with(itemView)
                .load(story.multimedia?.get(1)?.url)
                .apply(RequestOptions.bitmapTransform(multi))
                .into(itemView.iv_story)
            itemView.tv_title_story.text = story.title

            itemView.setOnClickListener { story.url?.let { it1 -> adapterEvent.itemStoryClick(it1) } }
        }
    }

    inner class StorySearchViewholder(
        itemView: View
    ) : BaseViewHolder<View>(itemView) {
        override fun bind(position: Int) {
            storySearch = listSoriesSearch?.get(position) ?: storySearch
            //set image rounded
            val multi = MultiTransformation<Bitmap>(
                RoundedCornersTransformation(7, 0, RoundedCornersTransformation.CornerType.ALL)
            )
            Glide.with(itemView)
                .load(storySearch.multimedia?.get(1)?.url)
                .apply(RequestOptions.bitmapTransform(multi))
                .into(itemView.iv_story)
            itemView.tv_title_story.text = storySearch.headline?.main

            itemView.setOnClickListener {
                storySearch.webUrl?.let { url ->
                    adapterEvent.itemStoryClick(url)
                }
            }
        }
    }
}