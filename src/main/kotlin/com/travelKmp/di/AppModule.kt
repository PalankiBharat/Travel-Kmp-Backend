package com.travelKmp.di

import com.travelKmp.repository.CountryRepository
import org.koin.dsl.module

val appModule = module {
    single { CountryRepository() }
}