package thuy.ptithcm.week2.ui.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_story.view.*
import thuy.ptithcm.week2.R
import thuy.ptithcm.week2.model.Doc
import thuy.ptithcm.week2.model.Story
import thuy.ptithcm.week2.util.IMAGE_URL

class DetailActivity : AppCompatActivity() {

    companion object {
        private var instance: DetailActivity? = null
        fun getInstance(): DetailActivity {
            if (instance == null) instance = DetailActivity()
            return instance!!
        }
    }

    private var story: Story? = null
    private var doc: Doc? = null
    private var isSearch: Boolean? = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        isSearch = intent?.getBooleanExtra("iSearch", false)
        if (isSearch!!) {
            doc = intent?.getParcelableExtra("doc")
            inItView(doc)
        } else {
            story = intent?.getParcelableExtra("story")
            inItView(story)
        }
    }

    private fun inItView(story: Story?) {
        tvTbTitle.text = story?.title
        tv_title.text = story?.title
        Glide.with(this)
            .load(story?.multimedia?.get(0)?.url)
            .error(R.drawable.no_image)
            .placeholder(R.drawable.ic_image_load)
            .into(ivDetail)
        tv_lead_paragrap.text = story?.abstract
    }

    private fun inItView(doc: Doc?) {
        tvTbTitle.text = doc?.headline?.main
        tv_title.text = doc?.headline?.main
        if (doc?.multimedia?.size != 0) {
            ivDetail.visibility = View.VISIBLE
            Glide.with(this)
                .load(IMAGE_URL + doc?.multimedia?.get(0)?.url)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.ic_image_load)
                .into(ivDetail)
        } else
            ivDetail.visibility = View.GONE
        tv_lead_paragrap.text = doc?.leadParagraph
    }

    fun onBtnBackClickListener(view: View) {
        finish()
    }

    fun clickOpenWebView(view: View) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(applicationContext, R.color.colorLauncher))
        val customTabsIntent = builder.build()
        if (isSearch!!) {
            customTabsIntent.launchUrl(this, Uri.parse(doc?.webUrl))
        } else {
            customTabsIntent.launchUrl(this, Uri.parse(story?.url))
        }
    }
}
