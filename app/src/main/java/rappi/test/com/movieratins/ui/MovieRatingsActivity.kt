package rappi.test.com.movieratins.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import rappi.test.com.movieratins.R
import rappi.test.com.movieratins.databinding.MovieMainBinding

class MovieRatingsActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.movie_ratings_main)
        val binding = DataBindingUtil.setContentView<MovieMainBinding>(this,R.layout.movie_main)
        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.my_nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(binding.navView,navController)
    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.my_nav_host_fragment)
        return NavigationUI.navigateUp(drawerLayout, navController)
    }*/
}
