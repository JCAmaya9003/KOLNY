package com.pdm_proyecto.kolny.ui.navigation.residente

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.events.EventScreen
import com.pdm_proyecto.kolny.viewmodels.NoticiaViewModel

fun NavGraphBuilder.ResidenteNavigation(
    navController: NavHostController,
    eventViewModel: EventViewModel,
    noticiaViewModel: NoticiaViewModel
) {

    ResidenteEventoNavigationEntries(
        navController = navController,
        eventViewModel = eventViewModel
    )
    ResidenteNoticiaNavigationEntries(
        navController = navController,
        noticiaViewModel = noticiaViewModel
    )
}