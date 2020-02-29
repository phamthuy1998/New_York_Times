package thuy.ptithcm.week2.ui.fragment


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_detail.*
import thuy.ptithcm.week2.R
import thuy.ptithcm.week2.viewmodel.StoryViewModel


class DetailFragment : Fragment() {

    companion object {
        private var instance: DetailFragment? = null
        fun getInstance(): DetailFragment {
            if (instance == null) instance = DetailFragment()
            return instance!!
        }
    }

    private lateinit var storyViewModel: StoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = "https://www.codepath.com/"
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
        inItValue()
        addEvent()
    }

    private fun addEvent() {
        btn_back.setOnClickListener { showHomeFragment() }
    }

    private fun showHomeFragment() {
        val nextFrag = HomeFragment.getInstance()
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.frmMain, nextFrag)
            .addToBackStack(null)
            .commit()
    }

    private fun inItValue() {
        storyViewModel = activity?.run {
            ViewModelProviders.of(this)[StoryViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }
}
