package com.hf.starwars.di.module

import com.hf.data.StarWarsRepository
import com.hf.domain.repository.IStarWarsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: StarWarsRepository): IStarWarsRepository
}