package com.hf.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hf.cache.db.DbConstants

@Entity(tableName = DbConstants.SPECIE_TABLE_NAME)
data class CachedSpecie (
    @PrimaryKey
    @ColumnInfo(name = DbConstants.COLUMN_SPECIE_ID)
    val id : String,
    val name : String?,
    val language : String?,
    val homeworld : String?)