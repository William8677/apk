package com.williamfq.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_activity")
data class UserActivity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // ID único de cada registro

    @ColumnInfo(name = "user_id")
    val userId: String, // ID del usuario asociado

    @ColumnInfo(name = "period")
    val period: String, // Período de tiempo (e.g., "daily", "weekly", "monthly")

    @ColumnInfo(name = "messages_text")
    val messagesText: Int = 0, // Contador de mensajes de texto enviados

    @ColumnInfo(name = "messages_voice")
    val messagesVoice: Int = 0, // Contador de mensajes de voz enviados

    @ColumnInfo(name = "calls_made")
    val callsMade: Int = 0, // Contador de llamadas realizadas

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long = System.currentTimeMillis() // Marca de tiempo de la última actualización (en milisegundos)
)
