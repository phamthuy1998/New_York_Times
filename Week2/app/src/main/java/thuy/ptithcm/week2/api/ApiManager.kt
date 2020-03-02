package thuy.ptithcm.week2.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import thuy.ptithcm.week2.model.FilterSearch
import thuy.ptithcm.week2.model.Stories
import thuy.ptithcm.week2.model.StorySearch
import thuy.ptithcm.week2.util.API_KEY
import thuy.ptithcm.week2.util.arrSection

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
    fun filterSearch(
        @Query("api-key")
        token: String = API_KEY,
        @Body
        filterSearch: FilterSearch?
//        @Query("q")
//        strSearch: String = "",
//
//        @Query("begin_date")
//        beginDate: String = "",
//
//        @Query("end_date")
//        endDate: String = "",
//
//        @Query("sort")
//        sort: String = ""
    ): Call<StorySearch>

}