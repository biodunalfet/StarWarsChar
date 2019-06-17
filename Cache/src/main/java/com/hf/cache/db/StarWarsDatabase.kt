package com.hf.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hf.cache.dao.FilmDao
import com.hf.cache.dao.PlanetDao
import com.hf.cache.dao.SpecieDao
import com.hf.cache.model.CachedFilm
import com.hf.cache.model.CachedPlanet
import com.hf.cache.model.CachedSpecie
import javax.inject.Inject

@Database(
    entities = [CachedSpecie::class,
        CachedPlanet::class, CachedFilm::class], version = 1
)
abstract class StarWarsDatabase @Inject constructor() : RoomDatabase() {

    abstract fun specieDao(): SpecieDao
    abstract fun planetDao(): PlanetDao
    abstract fun filmDao(): FilmDao

    companion object {

        private var INSTANCE: StarWarsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): StarWarsDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            StarWarsDatabase::class.java, "starwars.db"
                        )
                            .build()
                    }

                    return INSTANCE as StarWarsDatabase
                }
            }

            return INSTANCE as StarWarsDatabase
        }
    }

}