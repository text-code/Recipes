package ru.netology.recipes.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import ru.netology.recipes.R
import ru.netology.recipes.ui.FeedFragment.Companion.textArg
import ru.netology.recipes.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIntent(intent)

        binding.switchFragment.check(R.id.all_recipes)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navHostFragment.navController
                .navigate(
                    R.id.action_feedFragment_to_newRecipeFragment,
                    Bundle().apply { textArg = text }
                )
        }
    }
}