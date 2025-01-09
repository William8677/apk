package com.williamfq.data.dao

import androidx.room.*
import com.williamfq.data.entities.Reaction

@Dao
interface ReactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReaction(reaction: Reaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReactions(reactions: List<Reaction>)

    @Query("SELECT * FROM reactions WHERE messageId = :messageId")
    suspend fun getReactionsByMessage(messageId: String): List<Reaction>

    @Query("SELECT * FROM reactions WHERE userId = :userId")
    suspend fun getReactionsByUser(userId: String): List<Reaction>

    @Query("DELETE FROM reactions WHERE messageId = :messageId")
    suspend fun deleteReactionsByMessage(messageId: String)

    @Query("DELETE FROM reactions")
    suspend fun clearReactions()
}
