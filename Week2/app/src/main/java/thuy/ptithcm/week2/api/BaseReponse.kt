package thuy.ptithcm.week2.api

import android.util.Log
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import thuy.ptithcm.week2.model.Stories
import thuy.ptithcm.week2.model.StorySearch
import thuy.ptithcm.week2.util.BASE_URL

class BaseReponse {
    private val _myApi: ApiManager by lazy {
        getHelperRestFull()!!.create(ApiManager::class.java)
    }

    companion object {

        private var retrofit: Retrofit? = null

        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("API", message)
            }
        }).setLevel(HttpLoggingInterceptor.Level.BASIC)

        var client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        fun getHelperRestFull(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit
                    .Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }

    private fun <T : Any> buildRequest(call: Call<T>): Single<T> {
        return Single.create {
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    try {
                        it.onSuccess(response.body()!!) // !!server bat buoc tra ve co hoac rong, khong the tra ve null
                    } catch (ex: Exception) {
                        it.onError(ex)
                    }
                }

                override fun onFailure(p0: Call<T>, response: Throwable) {
                    it.onError(response)
                }
            })
        }
    }

    fun getListStory(section: String): Single<Stories> {
        return buildRequest(_myApi.getListStory(section = section))
    }

    fun getListSearchStory(strSearch: String): Single<StorySearch> {
        return buildRequest(_myApi.getListSearch(strSearch = strSearch))
    }

    fun getListFilterStory(
        strSearch: String,
        beginDay: String,
        endDate: String,
        sort: String
    ): Single<StorySearch> {
        return buildRequest(
            _myApi.filterSearch(
                strSearch = strSearch,
                beginDate = beginDay,
                endDate = endDate,
                sort = sort
            )
        )
    }
}