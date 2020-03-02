package thuy.ptithcm.week2.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Stories(
    val copyright: String?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("num_results")
    val numResults: Int?,
    val results: List<Story>?,
    val section: String?,
    val status: String?
):Parcelable

@Parcelize
data class Story(
    val abstract: String?,
    val byline: String?,
    @SerializedName("created_date")
    val createdDate: String?,
    @SerializedName("des_facet")
    val desFacet: List<String>?,
    @SerializedName("item_type")
    val itemType: String?,
    val kicker: String?,
    @SerializedName("material_type_facet")
    val materialTypeFacet: String?,
    val multimedia: List<Multimedia>?,
    @SerializedName("published_date")
    val publishedDate: String?,
    val section: String?,
    @SerializedName("short_url")
    val shortUrl: String?,
    val subsection: String?,
    val title: String?,
    @SerializedName("updated_date")
    val updatedDate: String?,
    val uri: String?,
    val url: String?
):Parcelable

@Parcelize
data class Multimedia(
    val caption: String,
    val copyright: String,
    val format: String,
    val height: Int,
    val subtype: String,
    val type: String,
    val url: String,
    val width: Int
):Parcelable

