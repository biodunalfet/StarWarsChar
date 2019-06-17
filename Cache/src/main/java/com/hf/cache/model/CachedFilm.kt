package com.hf.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hf.cache.db.DbConstants

@Entity(tableName = DbConstants.FILM_TABLE_NAME)
data class CachedFilm(
    @PrimaryKey
    @ColumnInfo(name = DbConstants.COLUMN_FILM_ID)
    val id: String,
    val title: String?,
    val release_date: String?,
    val opening_crawl: String?
)