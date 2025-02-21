package com.ditya.notesapp.ui.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ditya.notesapp.ui.detail_screen.DetailScreen
import com.ditya.notesapp.ui.detail_screen.DetailViewModel
import com.ditya.notesapp.ui.main_screen.MainScreen
import com.ditya.notesapp.ui.main_screen.MainViewModel


@Composable
fun Navigation(){
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Routes.MainScreen
    ) {
        composable<Routes.MainScreen> {
            val mainViewModel = hiltViewModel<MainViewModel>()
            val mainState by mainViewModel.state.collectAsState()

            MainScreen(
                state = mainState,
                onEvent = mainViewModel::onEvent,
                onNavigate = { noteId ->
                    Log.d("Navigation", "MainScreen: $noteId")
                    navController.navigate(Routes.DetailScreen(noteId))
                }
                )
        }
        composable<Routes.DetailScreen> {
            val detailViewModel = hiltViewModel<DetailViewModel>()
            val detailState by detailViewModel.state.collectAsState()

            DetailScreen(
                state = detailState,
                onEvent = detailViewModel::onEvent,
                onBack = {
                    navController.popBackStack()
                }
                )
        }
    }

}