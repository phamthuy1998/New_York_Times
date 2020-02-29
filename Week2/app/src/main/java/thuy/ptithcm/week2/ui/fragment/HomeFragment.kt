package thuy.ptithcm.week2.ui.fragment


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.dialog_filter.*
import kotlinx.android.synthetic.main.dialog_top_stories_filter.*
import kotlinx.android.synthetic.main.fragment_home.*
import thuy.ptithcm.week2.R
import thuy.ptithcm.week2.ui.adapter.AdapterEvent
import thuy.ptithcm.week2.ui.adapter.GridItemDecoration
import thuy.ptithcm.week2.ui.adapter.StoryAdapter
import thuy.ptithcm.week2.util.arrSection
import thuy.ptithcm.week2.viewmodel.StoryViewModel
import java.util.*


class HomeFragment : Fragment(), AdapterEvent {
    companion object {
        private var instance: HomeFragment? = null
        fun getInstance(): HomeFragment {
            if (instance == null) instance = HomeFragment()
            return instance!!
        }
    }

    private val storyAdapter: StoryAdapter by lazy {
        StoryAdapter(adapterEvent = this)
    }

    private var isSeach = false
    private var isFilter = false
    private lateinit var storyViewModel: StoryViewModel
    private lateinit var radioButton: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Hidden keyboard for search
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        inItValue()
        inItView()
        bidings()
        addEvent()
    }

    private fun addEvent() {
        edt_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchStory()
            }
        })
        btn_filter.setOnClickListener { showDialogFilterSearch() }
        btn_menu_section.setOnClickListener { showDialogSection() }
    }

    @SuppressLint("DefaultLocale")
    private fun showDialogFilterSearch() {
        var beginDate = ""
        var endDate = ""
        var sortOrder = -1
        var sortString = ""
        val dialog = Dialog(requireContext(), android.R.style.Theme_Material_Light_NoActionBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_filter)

        dialog.edt_bgdate.setText(storyViewModel.getBeginDate())
        dialog.edt_end_date.setText(storyViewModel.getEndDate())
        if (storyViewModel.getSortOrder() != -1)
            storyViewModel.getSortOrder()?.let { dialog.radg_sort.check(it) };

        dialog.btn_save.setOnClickListener {
            var check = true
            if (dialog.edt_bgdate.text.trim().toString() == "") {
                dialog.edt_bgdate.error = "You have not entered a begin date"
                check = false
            }

            if (dialog.edt_end_date.text.trim().toString() == "") {
                dialog.edt_end_date.error = "You have not entered a end date"
                check = false
            }
            if (dialog.radg_sort.checkedRadioButtonId == -1) {
                Toast.makeText(
                    requireContext(),
                    "you have not choosen a type of sort",
                    Toast.LENGTH_LONG
                ).show()
                check = false
            }

            if (!check) return@setOnClickListener
            dialog.dismiss()
            beginDate = dialog.edt_bgdate.text.toString()
            endDate = dialog.edt_end_date.text.toString()
            if (sortOrder != -1)
                storyViewModel.setSortOrder(sortOrder)

            storyViewModel.setBeginDate(beginDate)
            storyViewModel.setEndDate(endDate)
            radioButton =
                dialog.radg_sort.findViewById(storyViewModel.getSortOrder()!!) as RadioButton

            sortString = radioButton.text.toString()
            storyViewModel.getListFilter(
                edt_search.text.toString(),
                beginDate.replace("/", ""),
                endDate.replace("/", ""),
                sortString.toLowerCase()
            )
        }
        dialog.btn_cancel.setOnClickListener { dialog.dismiss() }
        dialog.radg_sort.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            Log.d("dialog", checkedId.toString())
            radioButton = group.findViewById(checkedId) as RadioButton
            Toast.makeText(requireContext(), radioButton.text, Toast.LENGTH_SHORT).show()
            sortOrder = checkedId
        })

        dialog.edt_bgdate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, yearDl, monthOfYear, dayOfMonth ->
                    var date1 = dayOfMonth.toString()
                    var month1 = (monthOfYear + 1).toString()
                    if (date1.length == 1) date1 = "0$date1"
                    if (month1.length == 1) month1 = "0$month1"
                    val date = "$yearDl/$month1/$date1"
                    beginDate = date
                    dialog.edt_bgdate.setText(beginDate)
                }, year, month, day
            )
            dpd.show()

//            val newFragment = DatetimeFragment(dialog.edt_bgdate)
//            fragmentManager?.let { it1 -> newFragment.show(it1, "Choose a date of birth") }
        }

        dialog.edt_end_date.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, yearDl, monthOfYear, dayOfMonth ->
                    var date1 = dayOfMonth.toString()
                    var month1 = (monthOfYear + 1).toString()
                    if (date1.length == 1) date1 = "0$date1"
                    if (month1.length == 1) month1 = "0$month1"
                    val date = "$yearDl/$month1/$date1"
                    endDate = date
                    dialog.edt_end_date.setText(endDate)
                    Log.d("date", endDate)
                }, year, month, day
            )
            dpd.datePicker.maxDate = Date().time
            dpd.show()

//            val newFragment = DatetimeFragment(dialog.edt_end_date)
//            fragmentManager?.let { it1 -> newFragment.show(it1, "Choose a date of birth") }
        }

        dialog.show()
    }

    private fun showDialogSection() {
        val dialog = Dialog(requireContext(), android.R.style.Theme_Material_Light_NoActionBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_top_stories_filter)

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
        dialog.btn_save_section.setOnClickListener {
            dialog.dismiss()
            storyViewModel.setSection(section)
            storyViewModel.getListStories(section)
        }
        dialog.btn_cancel_section.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun searchStory() {
        if (edt_search.text.trim().isNotEmpty()) {
            isSeach = true
            storyAdapter.setSearch(isSeach)
            storyAdapter.removeAllDataStories()
            storyViewModel.getListSearchStory(edt_search.text.trim().toString())

            if (storyAdapter.getItemCountListSearch() == 0) {
                tv_search_null.visibility = View.VISIBLE
                rv_story.visibility = View.GONE
            } else {
                tv_search_null.visibility = View.GONE
                rv_story.visibility = View.VISIBLE
            }
        } else {
            isSeach = false
            storyAdapter.setSearch(isSeach)
            storyAdapter.notifyDataSetChanged()
            storyViewModel.getListStories(storyViewModel.getSection())
        }
    }

    private fun inItValue() {
        storyViewModel = activity?.run {
            ViewModelProviders.of(this)[StoryViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        storyViewModel.getListStories(storyViewModel.getSection())
    }

    private fun bidings() {
        storyViewModel.listStoryLiveData.observe(this@HomeFragment, Observer { listStories ->
            if (!isSeach)
                storyAdapter.updateDataStories(listStories)
        })

        storyViewModel.listSearchStoryLiveData.observe(this@HomeFragment, Observer { listSearch ->
            if (isSeach || isFilter) {
                storyAdapter.removeAllDataStoriesSearch()
                storyAdapter.updateDataStoriesSearch(listSearch)
            }
        })
    }

    private fun inItView() {
        rv_story.adapter = storyAdapter
        rv_story.layoutManager = GridLayoutManager(requireContext(), 4)
        rv_story.addItemDecoration(GridItemDecoration(10, 4))
    }

    override fun itemStoryClick(T: Any) {
        val url = T as String
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(requireContext(), R.color.colorLauncher));
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    private fun showDetailFragment() {
        val nextFrag = DetailFragment.getInstance()
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.frmMain, nextFrag)
            .addToBackStack(null)
            .commit()
    }
}
