package thuy.ptithcm.week2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import thuy.ptithcm.week2.api.BaseReponse
import thuy.ptithcm.week2.model.Doc
import thuy.ptithcm.week2.model.Story
import thuy.ptithcm.week2.util.arrSection

class StoryViewModel : ViewModel() {
    var listStoryLiveData = MutableLiveData<ArrayList<Story>>().apply { value = arrayListOf() }
    var listSearchStoryLiveData =
        MutableLiveData<ArrayList<Doc>>().apply { value = arrayListOf() }

    private var section = MutableLiveData<String>().apply { value = arrSection[0] }
    private var beginDay = MutableLiveData<String>().apply { value = String() }
    private var endDay = MutableLiveData<String>().apply { value = String() }
    private var sortOrderID = MutableLiveData<Int>().apply { value = -1 }
    private var sortStr= MutableLiveData<String>().apply { value = String()  }


    private val baseReponse: BaseReponse by lazy { BaseReponse() }

    // CompositeDisposable dùng để quản lý Disposable, được sinh ra để chứa tất cả các Disposable
    // Disposable: là một Subscription mới
    // Subscription: mỗi khi có thực hiện 1 Observable sau đó chuyển qua 1 subcriber xử lý sẽ trả về 1 Subscription
    private val composite by lazy { CompositeDisposable() }
    var listTempt = ArrayList<Story>()

    init {
        if (listStoryLiveData.value.isNullOrEmpty())
            getListStories(section.value!!)
    }

    fun getListStories(section: String) {
        listTempt = arrayListOf()
        composite.add(
            baseReponse.getListStory(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.results.isNullOrEmpty()) {
                        it.results.forEach { listTempt.add(it) }
                        listStoryLiveData.postValue(listTempt)
                    }
                }, {
                })
        )
    }

    fun getListSearchSize():Int{
        return listSearchStoryLiveData.value?.size ?: 0
    }

    fun getListSearchStory(strSearch: String) {
        composite.add(
            baseReponse.getListSearchStory(strSearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listSearchStoryLiveData.postValue(it.response?.docs)
                }, {
                })
        )
    }

    fun getListFilter(
        strSearch: String,
        beginDate: String,
        endDate: String,
        sort: String
    ) {
        composite.add(
            baseReponse.getListFilterStory(strSearch, beginDate, endDate, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listSearchStoryLiveData.postValue(it.response?.docs)
                }, {
                })
        )
    }

    fun getSection(): String {
        return section.value.toString()
    }

    fun setSection(sec: String) {
        section.value = sec
    }

    fun getBeginDate(): String {
        return beginDay.value.toString()
    }

    fun setBeginDate(beginDate: String) {
        beginDay.value = beginDate
    }

    fun getEndDate(): String {
        return endDay.value.toString()
    }

    fun setEndDate(endDate: String) {
        endDay.value = endDate
    }

    fun getSortOrder(): Int? {
        return sortOrderID.value
    }

    fun setSortOrder(id: Int) {
        sortOrderID.value = id
    }

    fun getSortStr(): String {
        return sortStr.value.toString()
    }

    fun setSortStr(str: String) {
        sortStr.value = str
    }

}