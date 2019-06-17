package com.hf.starwars.di.module

import com.hf.domain.executor.PostExecutionThread
import com.hf.starwars.UiThread
import com.hf.starwars.details.PersonDetailsActivity
import com.hf.starwars.search.SearchActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesSearchActivity(): SearchActivity

    @ContributesAndroidInjector
    abstract fun contributesPersonDetailsActivity(): PersonDetailsActivity

}