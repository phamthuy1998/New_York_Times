package thuy.ptithcm.week2.model

import com.google.gson.annotations.SerializedName

data class Stories(
    val copyright: String?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("num_results")
    val numResults: Int?,
    val results: List<Story>?,
    val section: String?,
    val status: String?
)

data class Story(
    val abstract: String?,
    val byline: String?,
    @SerializedName("created_date")
    val createdDate: String?,
    @SerializedName("des_facet")
    val desFacet: List<String>?,
    @SerializedName("geo_facet")
    val geoFacet: List<Any>?,
    @SerializedName("item_type")
    val itemType: String?,
    val kicker: String?,
    @SerializedName("material_type_facet")
    val materialTypeFacet: String?,
    val multimedia: List<Multimedia>?,
    @SerializedName("org_facet")
    val orgFacet: List<Any>?,
    @SerializedName("per_facet")
    val perFacet: List<Any>?,
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
)

data class Multimedia(
    val caption: String,
    val copyright: String,
    val format: String,
    val height: Int,
    val subtype: String,
    val type: String,
    val url: String,
    val width: Int
)

