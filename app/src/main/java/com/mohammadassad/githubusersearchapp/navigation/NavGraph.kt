package com.mohammadassad.githubusersearchapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mohammadassad.githubusersearchapp.common.VersionControlUser
import com.mohammadassad.githubusersearchapp.followersscreen.presentation.FollowersScreen
import com.mohammadassad.githubusersearchapp.followersscreen.presentation.FollowersViewModel
import com.mohammadassad.githubusersearchapp.followingscreen.presentation.FollowingScreen
import com.mohammadassad.githubusersearchapp.followingscreen.presentation.FollowingViewModel
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserItem
import com.mohammadassad.githubusersearchapp.searchscreen.presentation.UserSearchAndListScreen
import com.mohammadassad.githubusersearchapp.searchscreen.presentation.UserSearchAndListViewModel
import com.mohammadassad.githubusersearchapp.splash.SplashScreen
import com.mohammadassad.githubusersearchapp.userscreen.presentation.UserProfileScreen
import com.mohammadassad.githubusersearchapp.userscreen.presentation.UserProfileViewModel

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Search.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Search.route) {
            val viewModel: UserSearchAndListViewModel = hiltViewModel()
            UserSearchAndListScreen(
                viewModel = viewModel,
                onFollowersTapped = { user ->
                    navController.navigate(Screen.Followers.createRoute(user.login))
                },
                onFollowingTapped = { user ->
                    navController.navigate(Screen.Following.createRoute(user.login))
                },
                onProfileTapped = { user ->
                    navController.navigate(Screen.Profile.createRoute(user.login))
                }
            )
        }
        
        composable(
            route = Screen.Profile.route,
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: return@composable
            val viewModel: UserProfileViewModel = hiltViewModel()
            UserProfileScreen(
                viewModel = viewModel,
                onFollowersTapped = { user ->
                    navController.navigate(Screen.Followers.createRoute(user.login))
                },
                onFollowingTapped = { user ->
                    navController.navigate(Screen.Following.createRoute(user.login))
                }
            )
        }
        
        composable(
            route = Screen.Followers.route,
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: return@composable
            val viewModel: FollowersViewModel = hiltViewModel()
            FollowersScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onUserTapped = { user ->
                    navController.navigate(Screen.Profile.createRoute(user.login))
                }
            )
        }
        
        composable(
            route = Screen.Following.route,
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: return@composable
            val viewModel: FollowingViewModel = hiltViewModel()
            FollowingScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onUserTapped = { user ->
                    navController.navigate(Screen.Profile.createRoute(user.login))
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Search : Screen("search")
    object Followers : Screen("followers/{username}") {
        fun createRoute(username: String) = "followers/$username"
    }
    object Following : Screen("following/{username}") {
        fun createRoute(username: String) = "following/$username"
    }
    object Profile : Screen("profile/{username}") {
        fun createRoute(username: String) = "profile/$username"
    }
} 