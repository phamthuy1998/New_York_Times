package thuy.ptithcm.week2.model

import com.google.gson.annotations.SerializedName

data class StorySearch(
    val copyright: String?,
    val response: Response?,
    val status: String
)

data class Response(
    val docs: ArrayList<Doc>?,
    val meta: Meta?
)

data class Meta(
    val hits: Int?,
    val offset: Int?,
    val time: Int?
)

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
)

data class MultimediaSearch(
    @SerializedName("crop_name")
    val cropName: String?,
    val height: Int?,
    val legacy: Legacy?,
    val rank: Int?,
    val subType: String?,
    val subtype: String?,
    val type: String?,
    val url: String?,
    val width: Int?
)

data class Legacy(
    val xlarge: String?,
    val xlargeheight: Int?,
    val xlargewidth: Int?
)

data class Headline(
    val main: String?,
    @SerializedName("print_headline")
    val printHeadline: String?
)
