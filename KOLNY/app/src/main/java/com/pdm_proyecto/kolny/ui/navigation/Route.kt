package com.pdm_proyecto.kolny.ui.navigation

sealed class Route(val route: String) {

    /* ---------- Raíces ---------- */
    object Login          : Route("login")
    object AdminRoot      : Route("admin_root")
    object VigilanteRoot  : Route("guard_root")
    object ResidenteRoot  : Route("residente_root")

    /* ---------- Admin ---------- */
    object AdminHome      : Route("admin_home")
    object GestionUsers   : Route("admin_gestion_usuarios")
    object Reportes       : Route("admin_reportes")
    object AdminAddUser     : Route("admin_add_user")
    object AdminEditUser    : Route("admin_edit_user")

    /* ---------- Vigilante ---------- */
    /*object VigilanteHome      : Route("vigilante_home")
    object Bitacora       : Route("vigilante_bitacora")

    /* ---------- Residente ---------- */
    object ResidenteHome        : Route("res_home")
    object Perfil         : Route("res_perfil")*/
}