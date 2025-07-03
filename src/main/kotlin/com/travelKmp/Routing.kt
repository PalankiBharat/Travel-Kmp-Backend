package com.travelKmp


import com.travelKmp.models.Country
import com.travelKmp.repository.CountryRepository
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.http.content.staticFiles
import io.ktor.server.http.content.staticResources
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File


fun Application.configureRouting() {
    routing {
        val baseUrl = environment.config.property("ktor.travelApp.baseUrl").getString()
        staticResources("/images", "data/images")
        route("/api") {
            countriesRoutes(baseUrl)
        }
    }
}

fun Route.countriesRoutes(baseUrl: String) {
    val repository = CountryRepository()

    route("/countries") {
        // Get all countries
        get {
            call.respond(repository.getAllCountries().map {
                it.copy(
                    flagIcon = "$baseUrl${it.flagIcon}",
                    touristPlaces = it.touristPlaces.map { places->
                        places.copy(
                            images = places.images.map { imageLink->
                                "$baseUrl$imageLink"
                            }
                        )
                    },
                )
            })
        }

        // Get country by name
        get("/{name}") {
            val name = call.parameters["name"] ?: return@get call.respondText(
                "Missing or malformed name",
                status = HttpStatusCode.BadRequest
            )

            val country = repository.getCountryByName(name)
            if (country != null) {
                call.respond(country)
            } else {
                call.respondText("Country not found", status = HttpStatusCode.NotFound)
            }
        }

        // Add a new country
        post {
            val country = call.receive<Country>()
            call.respond(HttpStatusCode.Created, repository.addCountry(country))
        }

        // Upload image
        post("/upload-image") {
            val multipart = call.receiveMultipart()
            var fileName = ""
            var fileBytes: ByteArray? = null

            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        fileName = part.originalFileName ?: "${System.currentTimeMillis()}.jpg"
                        fileBytes = part.streamProvider().readBytes()
                    }
                    else -> {}
                }
                part.dispose()
            }

            if (fileBytes != null) {
                val filePath = repository.saveImage(fileBytes, fileName)
                call.respond(hashMapOf("filePath" to filePath))
            } else {
                call.respondText("No file received", status = HttpStatusCode.BadRequest)
            }
        }
    }
}
