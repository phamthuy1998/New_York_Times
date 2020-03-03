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
import thuy.ptithcm.week2.model.Story

class StoryAdapter(
    private var listStories: ArrayList<Story>? = arrayListOf(),
//    var adapterEvent: AdapterEvent,
    val itemClick: (story: Story) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_story, viewGroup, false)
        StoryViewHolder(view, itemClick)
        return StoryViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return listStories?.size ?: 0
    }

    fun addDataStories(list: ArrayList<Story>) {
        listStories = arrayListOf()
        listStories = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.bind(position)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    inner class StoryViewHolder(
        itemView: View,
        val itemClick: (story: Story) -> Unit
    ) : BaseViewHolder<View>(itemView) {
        override fun bind(position: Int) {
           val story = listStories?.get(position)
            //set image rounded
            val multi = MultiTransformation<Bitmap>(
                RoundedCornersTransformation(7, 0, RoundedCornersTransformation.CornerType.ALL)
            )
            Glide.with(itemView)
                .load(story?.multimedia?.get(0)?.url)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.ic_image_load)
                .apply(RequestOptions.bitmapTransform(multi))
                .into(itemView.iv_story)
            itemView.tv_title_story.text = story?.title

            itemView.setOnClickListener {
                story?.let { it1 -> itemClick(it1) }
            }
        }
    }
}