package com.pdm_proyecto.kolny.ui.navigation

sealed class Route(val route: String) {

    /* ---------- Raíces ---------- */
    object Login          : Route("login")
    object AdminRoot      : Route("admin_root")
    object VigilanteRoot  : Route("guard_root")
    object ResidenteRoot  : Route("residente_root")

    /* ---------- Admin ---------- */
    object AdminHome: Route("admin_home")
    object GestionUsers: Route("admin_gestion_usuarios")
    object AdminAddUser: Route("admin_add_user")
    object AdminEditUser: Route("admin_edit_user")
    object EventRequests: Route("admin_event_requests")

    /* ---------- Vigilante ---------- */
    /*object VigilanteHome      : Route("vigilante_home")
    object Bitacora       : Route("vigilante_bitacora")

    /* ---------- Residente ---------- */
    object ResidenteHome        : Route("res_home")
    object Perfil         : Route("res_perfil")*/

    object Eventos: Route("eventos")
    object CreateEvent: Route("create_event")

    object Visitas: Route("visitas")
    object AddVisita: Route("add_visit")
    object EditVisita: Route("edit_visit")
}
