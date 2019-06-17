package com.hf.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hf.cache.db.DbConstants
import com.hf.cache.db.DbConstants.COLUMN_FILM_ID
import com.hf.cache.db.DbConstants.FILM_TABLE_NAME
import com.hf.cache.model.CachedFilm
import io.reactivex.Single

@Dao
abstract class FilmDao {

    @Query(DbConstants.FETCH_FILM_BY_ID_QUERY)
    abstract fun getFilmById(film_id : String): Single<CachedFilm>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertFilm(film: CachedFilm)
}