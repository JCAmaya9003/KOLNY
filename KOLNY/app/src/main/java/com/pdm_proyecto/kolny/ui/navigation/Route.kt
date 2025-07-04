package com.pdm_proyecto.kolny.ui.navigation

sealed class Route(val route: String) {

    object AdminHome: Route("admin_home")
    object GestionUsers: Route("admin_gestion_usuarios")
    object AdminAddUser: Route("admin_add_user")
    object AdminEditUser: Route("admin_edit_user")
    object EventRequests: Route("admin_event_requests")

    object Eventos: Route("eventos")
    object CreateEvent: Route("create_event")

    object Visitas: Route("visitas")
    object AddVisita: Route("add_visit")
    object EditVisita: Route("edit_visit")
}
