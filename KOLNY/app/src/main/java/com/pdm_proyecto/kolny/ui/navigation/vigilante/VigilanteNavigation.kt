package com.pdm_proyecto.kolny.ui.navigation.vigilante

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import com.pdm_proyecto.kolny.viewmodels.NoticiaViewModel
import com.pdm_proyecto.kolny.viewmodels.VisitaViewModel

fun NavGraphBuilder.VigilanteNavigation(
    navController: NavHostController,
    visitaViewModel: VisitaViewModel,
    noticiaViewModel: NoticiaViewModel,
    eventViewModel: EventViewModel
) {
    VigilanteVisitNavigationEntries(
        visitaViewModel = visitaViewModel,
        navController = navController
    )
    VigilanteNoticiaNavigationEntries(
        noticiaViewModel = noticiaViewModel,
        navController = navController
    )
    VigilanteEventoNavigationEntries(
        eventViewModel = eventViewModel,
        navController = navController
    )
}