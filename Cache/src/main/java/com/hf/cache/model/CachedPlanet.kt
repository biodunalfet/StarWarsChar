package com.hf.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hf.cache.db.DbConstants

@Entity(tableName = DbConstants.PLANET_TABLE_NAME)
data class CachedPlanet(
    @PrimaryKey
    @ColumnInfo(name = DbConstants.COLUMN_PLANET_ID)
    val id: String,
    val name: String?,
    val population: String?
)