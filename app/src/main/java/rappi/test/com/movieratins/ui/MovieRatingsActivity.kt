package rappi.test.com.movieratins.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import rappi.test.com.movieratins.R
import rappi.test.com.movieratins.databinding.MovieMainBinding

class MovieRatingsActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.movie_ratings_main)
        val binding = DataBindingUtil.setContentView<MovieMainBinding>(this, R.layout.movie_main)
        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.my_nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        // prevent nav gesture if not on start destination
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, bundle: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        NavigationUI.setupWithNavController(binding.navView, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.my_nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val argBundle = Bundle()
        val fragment = ListMovieFragment()

        return when(item.itemId){
            R.id.popularityFragment ->{
                argBundle.putInt("category",1)
                fragment.arguments = argBundle
                return true
            }
            R.id.topFragment-> {
                argBundle.putInt("category",2)
                fragment.arguments = argBundle
                return true
            }
            R.id.upcominFragment-> {
                argBundle.putInt("category",3)
                fragment.arguments = argBundle
                return true
            }else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
