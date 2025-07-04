package com.pdm_proyecto.kolny.data.models

fun EventoDB.toEvento(): Evento {
    return Evento(
        id = idevento ?: 0,
        titulo = titulo,
        descripcion = descripcion,
        lugar = ubicacion,
        fecha = fechaevento,
        horaInicio = horainicio,
        horaFin = horafin,
        creadoPor = creadordui,
        aprobado = true
    )
}


fun Evento.toEventoDB(): EventoDB {
    return EventoDB(
        idevento = if (id != 0) id else null,
        titulo = titulo,
        descripcion = descripcion,
        fechaevento = fecha,
        horainicio = horaInicio,
        horafin = horaFin,
        ubicacion = lugar,
        creadordui = creadoPor
    )
}


