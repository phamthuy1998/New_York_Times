package thuy.ptithcm.week2.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import thuy.ptithcm.week2.R
import thuy.ptithcm.week2.ui.fragment.HomeFragment
import thuy.ptithcm.week2.viewmodel.StoryViewModel


class MainActivity : AppCompatActivity() {

    companion object {
        private var instance: MainActivity? = null
        fun getInstance(): MainActivity {

            if (instance == null) instance = MainActivity()
            return instance!!
        }
    }

    private lateinit var viewModel: StoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(!isNetworkAvailable()) Toast.makeText(this, "No internet!", Toast.LENGTH_LONG).show();

        inItValue()
        showFragment(HomeFragment.getInstance())

    }

    private fun inItValue() {
        viewModel = this.run {
            ViewModelProviders.of(this)[StoryViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frmMain, fragment)
            .commit()
    }

}
