package thuy.ptithcm.week2.ui.fragment


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_filter.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import thuy.ptithcm.week2.R
import thuy.ptithcm.week2.model.Doc
import thuy.ptithcm.week2.ui.activity.DetailActivity
import thuy.ptithcm.week2.ui.adapter.StorySearchAdapter
import thuy.ptithcm.week2.utils.hideKeyboard
import thuy.ptithcm.week2.viewmodel.StoryViewModel
import java.util.*


class SearchFragment : Fragment() {

    private lateinit var storyViewModel: StoryViewModel
    private var isFilter = false
    private var isSearch = false
    private lateinit var radioButton: RadioButton
    private val storySearchAdapter: StorySearchAdapter by lazy {
        StorySearchAdapter {
            itemStorySearchClick(it)
        }
    }
    private var timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Hidden keyboard for search
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        swipRefreshSearch.setColorSchemeResources(
            R.color.colorBtn
        )

        inItView()
        inItValue()
        addEvent()
    }

    private fun inItView() {
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rv_story_search.layoutManager = staggeredGridLayoutManager
        rv_story_search.adapter = storySearchAdapter
    }

    private fun addEvent() {
        btn_filter.setOnClickListener {
            showDialogFilterSearch()
        }

        // hide keyboard
        rv_story_search.setOnTouchListener() { v, event ->
            v?.hideKeyboard()
            false
        }

        edt_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_search.text.trim().isNotEmpty())
                    searchStory()
            }
        })
        swipRefreshSearch.setOnRefreshListener {
            refreshLayout()
        }
    }

    @SuppressLint("DefaultLocale")
    private fun refreshLayout() {
        Handler().postDelayed(
            {
                storyViewModel.getListFilter(
                    edt_search.text.toString(),
                    formatDate(storyViewModel.getBeginDate()),
                    formatDate(storyViewModel.getEndDate()),
                    storyViewModel.getSortStr().toLowerCase()
                )
                swipRefreshSearch.isRefreshing = false
            }, 1000
        )
    }

    @SuppressLint("DefaultLocale")
    private fun searchStory() {
        progressBarSearch.visibility = View.VISIBLE
        timer.cancel()
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                isSearch = true
                if (isFilter)
                    storyViewModel.getListFilter(
                        edt_search.text.trim().toString(),
                        formatDate(storyViewModel.getBeginDate()),
                        formatDate(storyViewModel.getEndDate()),
                        storyViewModel.getSortStr().toLowerCase()
                    )
                else
                    storyViewModel.getListSearchStory(edt_search.text.trim().toString())

                activity?.runOnUiThread {
                    progressBarSearch.visibility = View.GONE
                    if (storyViewModel.getListSearchSize() == 0) {
                        tv_search_null.visibility = View.VISIBLE
                        rv_story_search.visibility = View.GONE
                    } else {
                        tv_search_null.visibility = View.GONE
                        rv_story_search.visibility = View.VISIBLE
                    }
                }
            }
        }, 3000)
    }

    private fun formatDate(strDate: String): String {
        return if (strDate != "") {
            val dateArr = strDate.split("/")
            dateArr[2] + dateArr[1] + dateArr[0]
        } else
            ""
    }

    @SuppressLint("DefaultLocale", "InflateParams")
    private fun showDialogFilterSearch() {
        var beginDate: String
        var endDate: String
        var sortOrder = -1
        var sortString = ""
        val mBottomSheetDialog = RoundedBottomSheetDialog(requireContext())
        val dialog = layoutInflater.inflate(R.layout.dialog_filter, null)

        dialog.spinner_section.isEnabled = false

        dialog.edt_bgdate.setText(storyViewModel.getBeginDate())
        beginDate = storyViewModel.getBeginDate()
        dialog.edt_end_date.setText(storyViewModel.getEndDate())
        endDate = storyViewModel.getEndDate()
        if (storyViewModel.getSortOrder() != -1) {
            sortString = storyViewModel.getSortStr()
            storyViewModel.getSortOrder()?.let { dialog.radg_sort.check(it) }
        }

        dialog.btn_save.setOnClickListener {
            isFilter = true

            beginDate = dialog.edt_bgdate.text.toString()
            endDate = dialog.edt_end_date.text.toString()
            if (sortOrder != -1)
                storyViewModel.setSortOrder(sortOrder)

            storyViewModel.setBeginDate(beginDate)
            storyViewModel.setEndDate(endDate)
            storyViewModel.setSortStr(sortString)
            storyViewModel.getListFilter(
                edt_search.text.toString(),
                formatDate(beginDate),
                formatDate(endDate),
                storyViewModel.getSortStr().toLowerCase()
            )
            mBottomSheetDialog.dismiss()
            progressBarSearch.visibility = View.VISIBLE
        }
        dialog.btn_cancel.setOnClickListener {
            isFilter = false
            mBottomSheetDialog.dismiss()
        }
        dialog.radg_sort.setOnCheckedChangeListener { group, checkedId ->
            radioButton = group.findViewById(checkedId) as RadioButton
            Toast.makeText(requireContext(), radioButton.text, Toast.LENGTH_SHORT).show()
            sortString = radioButton.text.toString()
            sortOrder = checkedId
        }

        dialog.edt_bgdate.setOnClickListener {
            val cal = Calendar.getInstance()
            var yyyy = cal.get(Calendar.YEAR)
            var mm = cal.get(Calendar.MONTH)
            var dd = cal.get(Calendar.DAY_OF_MONTH)
            if (beginDate != "") {
                val dateArr = beginDate.split("/")
                yyyy = dateArr[2].toInt()
                mm = dateArr[1].toInt() - 1
                dd = dateArr[0].toInt()
            }
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, yearDl, monthOfYear, dayOfMonth ->
                    var date1 = dayOfMonth.toString()
                    var month1 = (monthOfYear + 1).toString()
                    if (date1.length == 1) date1 = "0$date1"
                    if (month1.length == 1) month1 = "0$month1"
                    val date = "$date1/$month1/$yearDl"
                    beginDate = date
                    dialog.edt_bgdate.setText(beginDate)
                }, yyyy, mm, dd
            )

            cal[Calendar.MONTH] = cal.get(Calendar.MONTH)
            cal[Calendar.DAY_OF_MONTH] = cal.get(Calendar.DAY_OF_MONTH)
            cal[Calendar.YEAR] = cal.get(Calendar.YEAR) - 10
            //min date
            dpd.datePicker.minDate = cal.timeInMillis
            if (endDate == "") {
                // set max date
                dpd.datePicker.maxDate = Date().time
            } else {// max date (bg date) <= date of end date
                val dateArr = endDate.split("/")
                yyyy = dateArr[2].toInt()
                mm = dateArr[1].toInt() - 1
                dd = dateArr[0].toInt()
                cal[Calendar.MONTH] = mm
                cal[Calendar.DAY_OF_MONTH] = dd
                cal[Calendar.YEAR] = yyyy
                dpd.datePicker.maxDate = cal.timeInMillis
            }
            dpd.show()
        }

        dialog.edt_end_date.setOnClickListener {
            val cal = Calendar.getInstance()
            var yyyy = cal.get(Calendar.YEAR)
            var mm = cal.get(Calendar.MONTH)
            var dd = cal.get(Calendar.DAY_OF_MONTH)
            if (endDate != "") {
                val dateArr = endDate.split("/")
                yyyy = dateArr[2].toInt()
                mm = dateArr[1].toInt() - 1
                dd = dateArr[0].toInt()
            }

            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, yearDl, monthOfYear, dayOfMonth ->
                    var date1 = dayOfMonth.toString()
                    var month1 = (monthOfYear + 1).toString()
                    if (date1.length == 1) date1 = "0$date1"
                    if (month1.length == 1) month1 = "0$month1"
                    val date = "$date1/$month1/$yearDl"
                    endDate = date
                    dialog.edt_end_date.setText(endDate)
                    Log.d("date", endDate)
                }, yyyy, mm, dd
            )
            dpd.datePicker.maxDate = Date().time

            if (beginDate == "") {
                cal[Calendar.MONTH] = cal.get(Calendar.MONTH)
                cal[Calendar.DAY_OF_MONTH] = cal.get(Calendar.DAY_OF_MONTH)
                cal[Calendar.YEAR] = cal.get(Calendar.YEAR) - 10
                //min date
                dpd.datePicker.minDate = cal.timeInMillis

            } else {// min date (end date) >= date of begin date
                val dateArr = beginDate.split("/")
                Log.d("ddmmyy", dateArr[0] + "/" + dateArr[1] + "/" + dateArr[2])
                yyyy = dateArr[2].toInt()
                mm = dateArr[1].toInt() - 1
                dd = dateArr[0].toInt()
                cal[Calendar.MONTH] = mm
                cal[Calendar.DAY_OF_MONTH] = dd
                cal[Calendar.YEAR] = yyyy
                dpd.datePicker.minDate = cal.timeInMillis
            }
            dpd.show()
        }
        mBottomSheetDialog.setContentView(dialog)
        mBottomSheetDialog.show()

        dialog.btnCancelDialog.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }
    }

    private fun inItValue() {
        storyViewModel = activity?.run {
            ViewModelProviders.of(this)[StoryViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        storyViewModel.getListSearchStory(edt_search.text.trim().toString())
        storyViewModel.listSearchStoryLiveData.observe(this@SearchFragment, Observer { listSearch ->
            storySearchAdapter.addDataStoriesSearch(listSearch)
            progressBarSearch.visibility = View.GONE
        })
    }

    private fun itemStorySearchClick(storySearch: Doc) {
        val intent = Intent(activity, DetailActivity.getInstance().javaClass)
        intent.putExtra("iSearch", true)
        intent.putExtra("doc", storySearch)
        startActivity(intent)
    }

}
