package com.williamfq.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reactions")
data class Reaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val messageId: String,
    val userId: String,
    val emoji: String,
    val timestamp: Long = System.currentTimeMillis()
)