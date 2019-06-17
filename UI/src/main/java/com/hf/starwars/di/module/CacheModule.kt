package com.hf.starwars.di.module

import android.app.Application
import com.hf.cache.FilmCacheImpl
import com.hf.cache.PlanetCacheImpl
import com.hf.cache.SpecieCacheImpl
import com.hf.cache.db.StarWarsDatabase
import com.hf.data.mapper.IUrlToIdMapper
import com.hf.data.mapper.UrlToIdMapper
import com.hf.data.model.FilmEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import com.hf.data.repository.ICache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDataBase(application: Application): StarWarsDatabase {
            return StarWarsDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun providesUrlToIdConverter
                (converter : UrlToIdMapper) : IUrlToIdMapper

    @Binds
    abstract fun bindFilmCache
                (cache: FilmCacheImpl): ICache<String, FilmEntity>

    @Binds
    abstract fun bindPlanetCache
                (cache: PlanetCacheImpl): ICache<String, PlanetEntity>

    @Binds
    abstract fun bindSpecieCache
                (cache: SpecieCacheImpl): ICache<String, SpecieEntity>
}