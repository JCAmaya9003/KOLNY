//BORRAR CUANDO ESTE EL DAO
//SOLO ES TEMPORAL

package com.pdm_proyecto.kolny.data.repository

import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.utils.createDate
import javax.inject.Inject

class UsuarioRepository @Inject constructor() {
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
                    tipoAdmin = "DESARROLLADOR"
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
                    rol = "VIGILANTE",
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
                    password = "anita9269",
                    activo = true,
                    rol = "ADMIN",
                    tipoAdmin = "DIRECTIVA"
                ),
                Usuario(
                    dui = "56781234-9",
                    nombre = "Luisa Rodríguez",
                    telefono = "2257-7777",
                    fechaNacimiento = createDate(1988, 7, 5),
                    casa = "5E",
                    email = "luisa.rodriguez@example.com",
                    password = "luisa8869",
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
                    dui = "78912345-6",
                    nombre = "Sofía Ramírez",
                    telefono = "2257-7777",
                    fechaNacimiento = createDate(1993, 9, 25),
                    casa = "7G",
                    email = "sofia.ramirez@example.com",
                    password = "sofia9369",
                    activo = true,
                    rol = "ADMIN",
                    tipoAdmin = "DIRECTIVA"
                )
            )
        )
    }

    fun getAllUsuarios() : List<Usuario> = usuarios

    fun addUsuario(usuario: Usuario): List<Usuario> {
        usuarios.add(usuario)
        return usuarios.toList() //que el dao devuelva la lista de usuarios actualizada
    }

    fun updateUsuario(usuarioActualizado: Usuario): List<Usuario> {
        val index = usuarios.indexOfFirst { it.dui == usuarioActualizado.dui }
        if (index != -1) {
            usuarios[index] = usuarioActualizado
        }
        return usuarios.toList()
    }

    fun deleteUsuario(usuario: Usuario): List<Usuario> {
        usuarios.removeAll { it.dui == usuario.dui }
        return usuarios.toList()
    }

    fun getUsuarioByDui(dui: String) : Usuario? = usuarios.find { it.dui == dui }
}