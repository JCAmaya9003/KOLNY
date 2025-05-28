package com.pdm_proyecto.kolny.data.dao

import com.pdm_proyecto.kolny.data.model.Usuario
import androidx.room.Query
import androidx.room.Dao

@Dao
interface UsuarioDao {

    @Query("""
        SELECT 
            u.IdUsuario AS id,
            u.Nombre AS nombre,
            u.Activo AS activo,
            r.NombreRol AS rol,
            ta.NombreTipo AS tipoAdmin
        FROM Usuarios u
        JOIN Roles r ON u.IdRol = r.IdRol
        LEFT JOIN Administradores a ON u.IdUsuario = a.IdAdministrador
        LEFT JOIN TiposAdmin ta ON a.IdTipoAdmin = ta.IdTipoAdmin
        WHERE u.Correo = :correo
        LIMIT 1
    """)
    suspend fun buscarUsuarioPorCorreo(correo: String): Usuario?
}
