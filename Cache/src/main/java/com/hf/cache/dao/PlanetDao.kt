package com.hf.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hf.cache.db.DbConstants
import com.hf.cache.model.CachedPlanet
import io.reactivex.Single

@Dao
abstract class PlanetDao {

    @Query(DbConstants.FETCH_PLANET_BY_ID_QUERY)
    abstract fun getPlanetById(planet_id : String): Single<CachedPlanet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPlanet(planet: CachedPlanet)
}