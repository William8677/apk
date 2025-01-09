package com.williamfq.data.dao

import androidx.room.*
import com.williamfq.data.entities.UserActivity
import com.williamfq.data.models.UserActivityStats

@Dao
interface UserActivityDao {

    /**
     * Incrementa el contador de mensajes de texto para un usuario en un período específico.
     */
    @Query("UPDATE user_activity SET messages_text = messages_text + 1 WHERE user_id = :userId AND period = :period")
    suspend fun incrementMessageCount(userId: String, period: String)

    /**
     * Incrementa el contador de llamadas realizadas por un usuario, diferenciando videollamadas.
     */
    @Query("""
        UPDATE user_activity 
        SET calls_made = calls_made + 1, 
            video_calls = video_calls + CASE WHEN :isVideoCall THEN 1 ELSE 0 END
        WHERE user_id = :userId
    """)
    suspend fun incrementCallCount(userId: String, isVideoCall: Boolean)

    /**
     * Inserta o reemplaza una actividad de usuario.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserActivity(userActivity: UserActivity)

    /**
     * Actualiza una actividad de usuario existente.
     */
    @Update
    suspend fun updateUserActivity(userActivity: UserActivity)

    /**
     * Obtiene una actividad de usuario específica basada en el ID del usuario y el período.
     */
    @Query("SELECT * FROM user_activity WHERE user_id = :userId AND period = :period")
    suspend fun getUserActivity(userId: String, period: String): UserActivity?

    /**
     * Obtiene estadísticas agregadas de actividad de un usuario.
     */
    @Query("""
        SELECT 
            SUM(messages_text) AS messagesText, 
            SUM(calls_made) AS callsMade, 
            SUM(video_calls) AS videoCalls 
        FROM user_activity 
        WHERE user_id = :userId
    """)
    suspend fun getUserActivityStats(userId: String): UserActivityStats

    /**
     * Registra una reacción para un usuario.
     */
    @Query("INSERT INTO reactions (user_id, reaction) VALUES (:userId, :reaction)")
    suspend fun logReaction(userId: String, reaction: String)

    /**
     * Elimina una actividad específica de un usuario basada en el período.
     */
    @Query("DELETE FROM user_activity WHERE user_id = :userId AND period = :period")
    suspend fun deleteUserActivity(userId: String, period: String)

    /**
     * Limpia todas las actividades de usuario.
     */
    @Query("DELETE FROM user_activity")
    suspend fun clearAllUserActivities()
}
