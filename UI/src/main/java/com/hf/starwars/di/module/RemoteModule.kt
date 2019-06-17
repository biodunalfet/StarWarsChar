package com.hf.starwars.di.module

import com.hf.data.repository.IRemote
import com.hf.remote.RemoteImpl
import com.hf.remote.service.StarWarsService
import com.hf.remote.service.StarWarsServiceFactory
import com.hf.starwars.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGithubService(): StarWarsService {
            return StarWarsServiceFactory.makeStarWarsService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindRemote(remote: RemoteImpl): IRemote
}