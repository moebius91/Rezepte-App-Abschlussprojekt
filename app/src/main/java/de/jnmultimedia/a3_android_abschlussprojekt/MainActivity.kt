package de.jnmultimedia.a3_android_abschlussprojekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNav.setupWithNavController(navHost.navController)

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.recipesLocaleFragment -> binding.bottomNav.visibility = View.VISIBLE
                R.id.ingredientsFragment -> binding.bottomNav.visibility = View.VISIBLE
                R.id.tagsFragment -> binding.bottomNav.visibility = View.VISIBLE
                R.id.categoriesFragment -> binding.bottomNav.visibility = View.VISIBLE
                R.id.recipeOnlineFragment -> binding.bottomNav.visibility = View.VISIBLE
                else -> binding.bottomNav.visibility = View.GONE
            }
        }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Wir initialisieren eine Variable mit dem NavController
                val navController: NavController = binding.fragmentContainerView.findNavController()

                if (navController.currentDestination?.id != R.id.recipesLocaleFragment) {
                    navController.navigateUp()
                } else {
                    finish()
                }
            }
        })
    }
}