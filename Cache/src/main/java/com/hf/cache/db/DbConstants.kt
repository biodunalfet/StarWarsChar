package com.hf.cache.db

object DbConstants {
    const val FILM_TABLE_NAME = "table_film"
    const val SPECIE_TABLE_NAME = "table_specie"
    const val PLANET_TABLE_NAME = "table_planet"
    const val COLUMN_FILM_ID = "film_id"
    const val COLUMN_PLANET_ID = "planet_id"
    const val COLUMN_SPECIE_ID = "specie_id"
    const val FETCH_FILM_BY_ID_QUERY = "SELECT * FROM $FILM_TABLE_NAME WHERE $COLUMN_FILM_ID = :film_id"
    const val FETCH_PLANET_BY_ID_QUERY = "SELECT * FROM $PLANET_TABLE_NAME WHERE $COLUMN_PLANET_ID = :planet_id"
    const val FETCH_SPECIE_BY_ID_QUERY = "SELECT * FROM $SPECIE_TABLE_NAME WHERE $COLUMN_SPECIE_ID = :specie_id"
}