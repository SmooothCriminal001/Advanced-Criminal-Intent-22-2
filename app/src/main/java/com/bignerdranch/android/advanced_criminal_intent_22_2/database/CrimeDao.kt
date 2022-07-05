package com.bignerdranch.android.advanced_criminal_intent_22_2.database

import androidx.room.Dao
import kotlinx.coroutines.flow.Flow
import androidx.room.Query
import com.bignerdranch.android.advanced_criminal_intent_22_2.Crime
import java.util.*

@Dao
interface CrimeDao {
    @Query("SELECT * FROM crime")
    fun getCrimes(): Flow<List<Crime>>

    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): Flow<Crime>
}