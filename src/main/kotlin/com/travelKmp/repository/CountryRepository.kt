package com.travelKmp.repository

import com.travelKmp.models.Country
import kotlinx.serialization.json.Json
import java.io.File

class CountryRepository() {
    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }
    val inputStream = this::class.java.classLoader.getResourceAsStream("data/countries.json")
    val jsonText = inputStream?.bufferedReader()?.use { it.readText() }

    fun getAllCountries(): List<Country> {
        if (jsonText == null || jsonText.isBlank()) {
            return emptyList()
        }
        return json.decodeFromString(jsonText)
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
        //dataFile.writeText(json.encodeToString(countries))

        return country
    }

    fun saveImage(imageBytes: ByteArray, fileName: String): String {
        val resourceFolder = this::class.java.classLoader.getResource("data/images")?.path
        val imagesDir = File(resourceFolder ?: "data/images")
        imagesDir.mkdirs()

        val imageFile = File(imagesDir, fileName)
        imageFile.writeBytes(imageBytes)

        return "data/images/$fileName"
    }
}