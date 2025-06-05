//BORRAR CUANDO ESTE EL DAO

package com.pdm_proyecto.kolny.data.repository

import com.pdm_proyecto.kolny.data.models.Usuario
import java.util.Calendar
import java.util.Date

fun createDate(year: Int, month: Int, day: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, day)
    return calendar.time
}

class UsuarioRepository() {
    private val usuarios = mutableListOf<Usuario>()

    init {
        usuarios.addAll(
            listOf(
                Usuario(
                    dui = "12345678-9",
                    nombre = "Juan Pérez",
                    telefono = "2257-7777",
                    fechaNacimiento = createDate(2000,3,10),
                    casa = "1A",
                    email = "juan.perez@example.com",
                    password = "password123",
                    activo = true,
                    rol = "ADMIN",
                    tipoAdmin = "SUPER_ADMIN"
                ),
                Usuario(
                    dui = "23456781-9",
                    nombre = "María García",
                    telefono = "2257-7777",
                    fechaNacimiento = createDate(1995, 8, 22),
                    casa = "2B",
                    email = "maria.garcia@example.com",
                    password = "securepass",
                    activo = true,
                    rol = "USUARIO",
                    tipoAdmin = null
                ),
                Usuario(
                    dui = "34567812-9",
                    nombre = "Carlos López",
                    telefono = "2257-7777",
                    fechaNacimiento = createDate(1985, 3, 10),
                    casa = "3C",
                    email = "carlos.lopez@example.com",
                    password = "clopez2023",
                    activo = false,
                    rol = "USUARIO",
                    tipoAdmin = null
                ),
                Usuario(
                    dui = "45678123-9",
                    nombre = "Ana Martínez",
                    telefono = "2257-7777",
                    fechaNacimiento = createDate(1992, 11, 30),
                    casa = "4D",
                    email = "ana.martinez@example.com",
                    password = "anita92",
                    activo = true,
                    rol = "ADMIN",
                    tipoAdmin = "EDITOR"
                ),
                Usuario(
                    dui = "56781234-9",
                    nombre = "Luisa Rodríguez",
                    telefono = "2257-7777",
                    fechaNacimiento = createDate(1988, 7, 5),
                    casa = "5E",
                    email = "luisa.rodriguez@example.com",
                    password = "luisa88",
                    activo = true,
                    rol = "USUARIO",
                    tipoAdmin = null
                ),
                Usuario(
                    dui = "67812345-9",
                    nombre = "Pedro Sánchez",
                    telefono = "2257-7777",
                    fechaNacimiento = createDate(1998, 2, 18),
                    casa = "6F",
                    email = "pedro.sanchez@example.com",
                    password = "pedrito98",
                    activo = false,
                    rol = "USUARIO",
                    tipoAdmin = null
                ),
                Usuario(
                    dui = "789123456-9",
                    nombre = "Sofía Ramírez",
                    telefono = "2257-7777",
                    fechaNacimiento = createDate(1993, 9, 25),
                    casa = "7G",
                    email = "sofia.ramirez@example.com",
                    password = "sofia93",
                    activo = true,
                    rol = "ADMIN",
                    tipoAdmin = "MODERADOR"
                )
            )
        )
    }

    fun getAllUsuarios() : List<Usuario> = usuarios

    fun addUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }

    fun getUsuarioByDui(dui: String) : Usuario? = usuarios.find { it.dui == dui }
}