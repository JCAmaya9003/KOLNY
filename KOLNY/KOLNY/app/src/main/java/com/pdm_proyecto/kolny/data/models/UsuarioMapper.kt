package com.pdm_proyecto.kolny.data.models

fun Usuario.toUsuarioDB(): UsuarioDB {
    return UsuarioDB(
        dui = this.dui,
        activo = this.activo,
        fotoperfil = null, // O convertir si tienes base64 o bytearray
        nombre = this.nombre,
        telefono = this.telefono,
        correo = this.email,
        password = this.password,
        idrol = when (this.rol.uppercase()) {
            "RESIDENTE" -> 1
            "VIGILANTE" -> 2
            "ADMIN"     -> 3
            else        -> 0
        },
        fecha_nacimiento = this.fechaNacimiento
    )
}
fun UsuarioDB.toUsuario(): Usuario {
    return Usuario(
        fotoPerfil = null, // Si luego usas base64 u otro formato, lo puedes mapear aquí
        dui = this.dui,
        nombre = this.nombre,
        telefono = this.telefono ?: "",
        fechaNacimiento = this.fecha_nacimiento,
        casa = null, // El campo 'casa' no está en UsuarioDB, así que se deja nulo
        email = this.correo,
        password = this.password,
        activo = this.activo,
        rol = when (this.idrol) {
            1 -> "RESIDENTE"
            2 -> "VIGILANTE"
            3 -> "ADMIN"
            else -> "USUARIO"
        },
        tipoAdmin = null // Si en el futuro agregas ese campo a UsuarioDB, lo mapeas aquí
    )
}