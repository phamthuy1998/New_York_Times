package thuy.ptithcm.week2.ui.fragment


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_filter.*
import kotlinx.android.synthetic.main.fragment_home.*
import thuy.ptithcm.week2.R
import thuy.ptithcm.week2.model.Story
import thuy.ptithcm.week2.ui.activity.DetailActivity
import thuy.ptithcm.week2.ui.adapter.GridItemDecoration
import thuy.ptithcm.week2.ui.adapter.StoryAdapter
import thuy.ptithcm.week2.util.arrSection
import thuy.ptithcm.week2.viewmodel.StoryViewModel


class HomeFragment : Fragment() {

    private val storyAdapter: StoryAdapter by lazy {
        StoryAdapter { story ->
            itemStoryClick(story)
        }
    }

    private lateinit var storyViewModel: StoryViewModel
    private var showDialog = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeContainer.setColorSchemeResources(
            R.color.colorBtn
        )
        progressBarHome.visibility = View.VISIBLE
        inItView()
        bindings()
        addEvent()
    }

    private fun addEvent() {
        btn_menu_section.setOnClickListener {
            if (!showDialog)
                showDialogSection()
        }
        swipeContainer.setOnRefreshListener { refreshLayout() }
    }

    private fun refreshLayout() {
        Handler().postDelayed(
            {
                storyViewModel.getListStories(storyViewModel.getSection())
                swipeContainer.isRefreshing = false
            }, 1000
        )
    }

    private fun showDialogSection() {
        val mBottomSheetDialog = RoundedBottomSheetDialog(requireContext())
        val sheetView = layoutInflater.inflate(R.layout.dialog_filter, null)

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_filter)
        dialog.edt_bgdate.isEnabled = false
        dialog.edt_end_date.isEnabled = false
        dialog.newest.isEnabled = false
        dialog.oldest.isEnabled = false
        dialog.relevance.isEnabled = false
        var section = ""
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.arr_section,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dialog.spinner_section.adapter = adapter
        }

        dialog.spinner_section.setSelection(arrSection.indexOf(storyViewModel.getSection()))
        dialog.spinner_section.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    section = parent?.getItemAtPosition(position).toString()
                }
            }
        dialog.btn_save.setOnClickListener {
            storyViewModel.setSection(section)
            storyViewModel.getListStories(section)
            dialog.dismiss()
            showDialog = false
            progressBarHome.visibility = View.VISIBLE
        }
        dialog.btn_cancel.setOnClickListener {
            showDialog = false
            dialog.dismiss()
        }
        if (!showDialog)
            dialog.show()
        showDialog = true
    }

    private fun bindings() {
        storyViewModel = activity?.run {
            ViewModelProviders.of(this)[StoryViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        progressBarHome.visibility = View.VISIBLE
        storyViewModel.listStoryLiveData.observe(this@HomeFragment, Observer { listStories ->
            storyAdapter.addDataStories(listStories)
            progressBarHome.visibility = View.GONE
        })
    }

    private fun inItView() {
        rv_story.adapter = storyAdapter
        rv_story.layoutManager = GridLayoutManager(requireContext(), 4)
        rv_story.addItemDecoration(GridItemDecoration(10, 4))
    }

    private fun itemStoryClick(story: Story) {
        val intent = Intent(activity, DetailActivity.getInstance().javaClass)
        intent.putExtra("iSearch", false)
        intent.putExtra("story", story)
        startActivity(intent)
    }
}