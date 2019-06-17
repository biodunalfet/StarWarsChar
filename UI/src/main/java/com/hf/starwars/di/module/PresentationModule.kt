package com.hf.starwars.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hf.presentation.details.PersonDetailsViewModel
import com.hf.presentation.search.SearchViewModel
import com.hf.starwars.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(
        viewModel: SearchViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonDetailsViewModel::class)
    abstract fun bindPersonDetailsViewModell(
        viewModel: PersonDetailsViewModel
    ): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)