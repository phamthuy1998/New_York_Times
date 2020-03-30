##
# Week 2

# Resources - User Interaction and Flows

This week we are covering user interaction and navigation flows within apps.

###
## Assignment
<img src="https://user-images.githubusercontent.com/32641436/77923729-066eac80-72cd-11ea-8612-f45345bc8e11.png" width="142" heigh="216">

This week we will be building a news article searching application powered by the [New York Times Search API](https://developer.nytimes.com/).

###
## Guides

- [Defining the ActionBar](http://guides.codepath.com/android/Defining-The-ActionBar)
- [Using the RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView)
- [Displaying Toasts](http://guides.codepath.com/android/Displaying-Toasts)
- [Using Intents to Create Flows](http://guides.codepath.com/android/Using-Intents-to-Create-Flows)
- [Common Implicit Intents](http://guides.codepath.com/android/Common-Implicit-Intents)
- [Using the App Toolbar](http://guides.codepath.com/android/Using-the-App-ToolBar)
- [Constructing Layouts](http://guides.codepath.com/android/Constructing-View-Layouts)
- [View Event Listeners](http://guides.codepath.com/android/Basic-Event-Listeners)
- [Creating Custom Listeners](http://guides.codepath.com/android/Creating-Custom-Listeners)
- [Styling UI Screens FAQ](http://guides.codepath.com/android/Styling-UI-Screens-FAQ)
- [Passing Objects with Parcelable](http://guides.codepath.com/android/Using-Parcelable)
- [Navigation and Task Stacks](http://guides.codepath.com/android/Navigation-and-Task-Stacks)
- [Extended ActionBar Guide](http://guides.codepath.com/android/Extended-ActionBar-Guide)
- [Displaying the Snackbar](http://guides.codepath.com/android/Displaying-the-Snackbar)

### User Stories

**The following user stories must be completed:**

- User can enter a search query that will display a grid of news articles using the thumbnail and headline from the New York Times Search API. ( **3 points** )
- User can click on &quot;filter&quot; icon which allows selection of advanced search options to filter articles. ( **3 points** )
  - An example of a query with filters (begin\_date, sort, and news\_desk) applied [can be found here](https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20160112&amp;sort=oldest&amp;fq=news_desk:(%22Education%22%20%22Health%22)&amp;api-key=227c750bb7714fc39ef1559ef1bd8329). Full details of the API can be found [n this article search README](https://developer.nytimes.com/article_search_v2.json#/README).
- User can configure advanced search filters such as: ( **points included above** )
  - Begin Date (using a [date picker](http://guides.codepath.com/android/Using-DialogFragment#displaying-date-or-time-picker-dialogs))
  - Sort order (_oldest or newest_) using a [spinner dropdown](http://guides.codepath.com/android/Working-with-Input-Views#spinners)
  - News desk values (_Arts, Fashion &amp; Style, Sports_) using [checkboxes](http://guides.codepath.com/android/Working-with-Input-Views#checkboxes)
- Subsequent searches will have any filters applied to the search results. ( **1 point** )
- User can tap on any article in results to view the contents in an embedded browser. ( **2 points** )
- User can [scroll down &quot;infinitely&quot;](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView) to continue loading more news articles. The maximum number of articles is limited by the API search. ( **1 point** )

**The following advanced user stories are optional but recommended:**

- Robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures. ( **1 point** )
- Use the [ActionBar SearchView](http://guides.codepath.com/android/Extended-ActionBar-Guide#adding-searchview-to-actionbar) or custom layout as the query box instead of an EditText. ( **1 point** )
- User can [share a link](http://guides.codepath.com/android/Sharing-Content-with-Intents#attach-share-for-a-webview-url) to their friends or email it to themselves. ( **1 point** )
- Replace Filter Settings Activity with a lightweight [modal overlay](http://guides.codepath.com/android/Using-DialogFragment). ( **2 points** )
- Improve the user interface and experiment with image assets and/or styling and coloring ( **1 to 3 points depending on the difficulty of UI improvements** )
- **Stretch:**  Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) with the StaggeredGridLayoutManager to display improve the grid of image results (see [Picasso guide](http://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#adjusting-the-image-size-dynamically) too). ( **2 points** )
- **Stretch:**  For different news articles that only have text or have text with thumbnails, use [Heterogenous Layouts](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) with RecyclerView. ( **2 points** )
- **Stretch:**  Apply the popular [ButterKnife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate. ( **1 point** )
- **Stretch:**  Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler). ( **1 point** )
- **Stretch:**  Replace all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate. ( **1 point** )
- **Stretch:**  Leverage the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into one or more activity layout templates. ( **1 point** )
- **Stretch:**  Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering. ( **1 point** )
- **Stretch:**  Switch to [using retrolambda expressions](http://guides.codepath.com/android/Lambda-Expressions) to cleanup event handling blocks. ( **1 point** )
- **Stretch:**  Leverage the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data. ( **1 point** )
- **Stretch:**  Consume the New York Times API using the popular [Retrofit networking library](http://guides.codepath.com/android/Consuming-APIs-with-Retrofit) instead of Android Async HTTP. ( **3 points** )
- Replace the embedded WebView with [Chrome Custom Tabs](http://guides.codepath.com/android/Chrome-Custom-Tabs) using a custom action button for sharing. ( **2 points** )

**Mockups:**

With required user stories completed:

<img src="https://user-images.githubusercontent.com/32641436/77923778-1a1a1300-72cd-11ea-904c-2d74b52af06e.png" width="142" heigh="216">
<img src="https://user-images.githubusercontent.com/32641436/77923824-2c944c80-72cd-11ea-8c1b-8279796ce0e1.png" width="142" heigh="216">
<img src="https://user-images.githubusercontent.com/32641436/77923847-33bb5a80-72cd-11ea-820b-7a1a67dbc73f.png" width="142" heigh="216">

With several optionals completed, the app might look like:

<img src="https://user-images.githubusercontent.com/32641436/77923893-3fa71c80-72cd-11ea-9cd4-361041509581.png" width="142" heigh="216">
<img src="https://user-images.githubusercontent.com/32641436/77923925-49308480-72cd-11ea-9ff3-53ae074d74a5.png" width="142" heigh="216">
<img src="https://user-images.githubusercontent.com/32641436/77923955-5188bf80-72cd-11ea-8984-03eb8492155c.png" width="142" heigh="216">
