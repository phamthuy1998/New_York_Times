package thuy.ptithcm.week2.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import thuy.ptithcm.week2.model.Stories
import thuy.ptithcm.week2.model.StorySearch
import thuy.ptithcm.week2.utils.API_KEY
import thuy.ptithcm.week2.utils.arrSection

interface ApiManager {

    @GET("topstories/v2/{section}.json")
    fun getListStory(
        @Path("section") section: String = arrSection[0],
        @Query("api-key") token: String = API_KEY
    ): Call<Stories>

    @GET("search/v2/articlesearch.json")
    fun getListSearch(
        @Query("api-key") token: String = API_KEY,
        @Query("q") strSearch: String = ""
    ): Call<StorySearch>

    @GET("search/v2/articlesearch.json")
    fun getListSearch1(
        @Query("api-key") token: String = API_KEY,
        @Query("q") strSearch: String = "",
        @Query("begin_date") beginDate: String = "",
        @Query("end_date") endDate: String = "",
        @Query("sort") sort: String = ""
    ): Call<StorySearch>

    @GET("search/v2/articlesearch.json")
    fun getListSearch2(
        @Query("api-key") token: String = API_KEY,
        @Query("q") strSearch: String = "",
        @Query("end_date") endDate: String = "",
        @Query("sort") sort: String = ""
    ): Call<StorySearch>

    @GET("search/v2/articlesearch.json")
    fun getListSearch3(
        @Query("api-key") token: String = API_KEY,
        @Query("q") strSearch: String = "",
        @Query("begin_date") beginDate: String = "",
        @Query("sort") sort: String = ""
    ): Call<StorySearch>

    @GET("search/v2/articlesearch.json")
    fun getListSearch4(
        @Query("api-key") token: String = API_KEY,
        @Query("q") strSearch: String = "",
        @Query("begin_date") beginDate: String = "",
        @Query("end_date") endDate: String = ""
    ): Call<StorySearch>

    @GET("search/v2/articlesearch.json")
    fun getListSearch5(
        @Query("api-key") token: String = API_KEY,
        @Query("q") strSearch: String = "",
        @Query("sort") sort: String = ""
    ): Call<StorySearch>

    @GET("search/v2/articlesearch.json")
    fun getListSearch6(
        @Query("api-key") token: String = API_KEY,
        @Query("q") strSearch: String = "",
        @Query("end_date") endDate: String = ""
    ): Call<StorySearch>

    @GET("search/v2/articlesearch.json")
    fun getListSearch7(
        @Query("api-key") token: String = API_KEY,
        @Query("q") strSearch: String = "",
        @Query("begin_date") beginDate: String = ""
    ): Call<StorySearch>

}