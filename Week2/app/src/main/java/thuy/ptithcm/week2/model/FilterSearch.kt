package thuy.ptithcm.week2.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class FilterSearch(
    @SerializedName("q")
    val strSearch: String?,
    @SerializedName("begin_date")
    val beginDate: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("sort")
    val sort: String?
)
