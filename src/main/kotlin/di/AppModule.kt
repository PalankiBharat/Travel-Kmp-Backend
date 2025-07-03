package di

import repository.CountryRepository
import org.koin.dsl.module

val appModule = module {
    single { CountryRepository() }
}