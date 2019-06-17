package com.hf.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hf.cache.db.DbConstants.FETCH_SPECIE_BY_ID_QUERY
import com.hf.cache.model.CachedSpecie
import io.reactivex.Single

@Dao
abstract class SpecieDao {

    @Query(FETCH_SPECIE_BY_ID_QUERY)
    abstract fun getSpecieById(specie_id: String): Single<CachedSpecie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSpecie(specie: CachedSpecie)
}