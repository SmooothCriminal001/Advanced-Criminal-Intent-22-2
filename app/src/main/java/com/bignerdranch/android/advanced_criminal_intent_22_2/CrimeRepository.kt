package com.bignerdranch.android.advanced_criminal_intent_22_2

import android.content.Context
import androidx.room.Room
import com.bignerdranch.android.advanced_criminal_intent_22_2.database.CrimeDatabase
import kotlinx.coroutines.flow.Flow
import java.util.*

private const val DATABASE_NAME = "crime-database"
class CrimeRepository private constructor(context: Context) {

    private val database: CrimeDatabase = Room
            .databaseBuilder(
                context.applicationContext,
                CrimeDatabase::class.java,
                DATABASE_NAME
            )
    		.createFromAsset(DATABASE_NAME)
            .build()

    fun getCrimes() : Flow<List<Crime>> = database.crimeDao().getCrimes()

    fun getCrime(id: UUID) : Flow<Crime> = database.crimeDao().getCrime(id)

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}