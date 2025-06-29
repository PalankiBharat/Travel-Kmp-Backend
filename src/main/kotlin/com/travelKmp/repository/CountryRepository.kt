package com.travelKmp.repository

import com.travelKmp.models.Country
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths

class CountryRepository() {
    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }
    private val dataFile = File("data/countries.json")
    
    init {
        // Create directory if it doesn't exist
        dataFile.parentFile?.mkdirs()
        
        // Create file if it doesn't exist
        if (!dataFile.exists()) {
            dataFile.createNewFile()
            dataFile.writeText("[]")
        }
    }
    
    fun getAllCountries(): List<Country> {
        if (!dataFile.exists() || dataFile.readText().isBlank()) {
            return emptyList()
        }
        return json.decodeFromString(dataFile.readText())
    }
    
    fun getCountryByName(name: String): Country? {
        return getAllCountries().find { it.name.equals(name, ignoreCase = true) }
    }
    
    fun addCountry(country: Country): Country {
        val countries = getAllCountries().toMutableList()
        
        // Check if country already exists
        val existingIndex = countries.indexOfFirst { it.name.equals(country.name, ignoreCase = true) }
        
        if (existingIndex >= 0) {
            // Replace existing country
            countries[existingIndex] = country
        } else {
            // Add new country
            countries.add(country)
        }
        
        // Save to file
        dataFile.writeText(json.encodeToString(countries))
        
        return country
    }
    
    fun saveImage(imageBytes: ByteArray, fileName: String): String {
        val imagesDir = File("data/images")
        imagesDir.mkdirs()
        
        val imageFile = File(imagesDir, fileName)
        imageFile.writeBytes(imageBytes)
        
        return "data/images/$fileName"
    }
}