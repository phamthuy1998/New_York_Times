package thuy.ptithcm.week2.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import thuy.ptithcm.week2.R
import thuy.ptithcm.week2.ui.adapter.MyFragmentPagerAdapter
import thuy.ptithcm.week2.ui.fragment.HomeFragment
import thuy.ptithcm.week2.ui.fragment.SearchFragment
import thuy.ptithcm.week2.viewmodel.StoryViewModel


class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private val homeFragment by lazy {
        HomeFragment()
    }

    private val searchFragment by lazy {
        SearchFragment()
    }

    private lateinit var viewModel: StoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!isNetworkAvailable()) Toast.makeText(this, "No internet!", Toast.LENGTH_LONG).show()

        inItValue()

        val viewPagerAdapter = MyFragmentPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(homeFragment, "Home fragment")
        viewPagerAdapter.addFragment(searchFragment, "Category fragment")

        viewPagerHome.adapter = viewPagerAdapter
        viewPagerHome.addOnPageChangeListener(this)

        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bot_nav_home -> {
                    viewPagerHome.currentItem = 0
                    true
                }
                R.id.bot_nav_search -> {
                    viewPagerHome.currentItem = 1
                    true
                }
                else -> false
            }
        }
    }

    private fun inItValue() {
        viewModel = this.run {
            ViewModelProviders.of(this)[StoryViewModel::class.java]
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                botNavigation.selectedItemId = R.id.bot_nav_home
            }
            1 -> {
                botNavigation.selectedItemId = R.id.bot_nav_search
            }
        }
    }

}
