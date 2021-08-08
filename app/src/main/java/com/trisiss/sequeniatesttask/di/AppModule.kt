package com.trisiss.sequeniatesttask.di

import com.trisiss.sequeniatesttask.data.FilmsService
import com.trisiss.sequeniatesttask.data.FilmsServiceImpl
import com.trisiss.sequeniatesttask.listFilms.ListFilmsContract
import com.trisiss.sequeniatesttask.listFilms.ListFilmsPresenter
import org.koin.dsl.module

/**
 * Created by trisiss on 8/8/2021.
 */
val appModule = module{
    // Presenters
    factory<ListFilmsContract.Presenter> { ListFilmsPresenter(get()) }

    // Service
    single<FilmsService> { FilmsServiceImpl() }
}