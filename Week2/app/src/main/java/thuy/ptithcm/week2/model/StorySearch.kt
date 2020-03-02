package thuy.ptithcm.week2.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class StorySearch(
    val copyright: String?,
    val response: Response?,
    val status: String
):Parcelable

@Parcelize
data class Response(
    val docs: ArrayList<Doc>?,
    val meta: Meta?
):Parcelable

@Parcelize
data class Meta(
    val hits: Int?,
    val offset: Int?,
    val time: Int?
):Parcelable

@Parcelize
data class Doc(
    @SerializedName("_id")
    val id: String?,
    val abstract: String?,
    @SerializedName("document_type")
    val documentType: String?,
    val headline: Headline?,
    @SerializedName("lead_paragraph")
    val leadParagraph: String?,
    val multimedia: List<MultimediaSearch>?,
    @SerializedName("news_desk")
    val newsDesk: String?,
    @SerializedName("print_page")
    val printPage: String?,
    @SerializedName("print_section")
    val printSection: String?,
    @SerializedName("pub_date")
    val pubDate: String?,
    @SerializedName("section_name")
    val sectionName: String?,
    val snippet: String?,
    val source: String?,
    @SerializedName("subsection_name")
    val subsectionName: String?,
    @SerializedName("type_of_material")
    val typeOfMaterial: String?,
    val uri: String?,
    @SerializedName("web_url")
    val webUrl: String?,
    @SerializedName("word_count")
    val wordCount: Int?
) :Parcelable

@Parcelize
data class MultimediaSearch(
    val height: Int?,
    val rank: Int?,
    val type: String?,
    val url: String?,
    val width: Int?
):Parcelable

@Parcelize
data class Headline(
    val main: String?,
    @SerializedName("print_headline")
    val printHeadline: String?
):Parcelable
