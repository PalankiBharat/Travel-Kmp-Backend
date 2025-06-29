package com.travelKmp

import com.travelKmp.repository.CountryRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * This is a simple utility to test that the data can be accessed correctly.
 * It's not meant to be used in production.
 */
fun main() {
    val repository = CountryRepository()
    
    // Get all countries
    val countries = repository.getAllCountries()
    println("Found ${countries.size} countries:")
    
    // Print each country and its tourist places
    countries.forEach { country ->
        println("Country: ${country.name}")
        println("Flag: ${country.flagIcon}")
        println("Tourist Places: ${country.touristPlaces.size}")
        
        country.touristPlaces.forEach { place ->
            println("  - ${place.name}: ${place.shortDescription}")
            println("    Location: ${place.location.lat}, ${place.location.long}")
            println("    Images: ${place.images.size}")
        }
        println()
    }
    
    // Test getting a country by name
    val usa = repository.getCountryByName("USA")
    if (usa != null) {
        println("Found USA with ${usa.touristPlaces.size} tourist places")
    } else {
        println("USA not found")
    }
    
    // Test getting a non-existent country
    val nonExistent = repository.getCountryByName("NonExistentCountry")
    if (nonExistent != null) {
        println("Found NonExistentCountry (unexpected)")
    } else {
        println("NonExistentCountry not found (expected)")
    }
}