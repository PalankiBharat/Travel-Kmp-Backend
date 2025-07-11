# Travel Kmp Backend

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need
  to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## Features

Here's a list of features included in this project:

| Name                                                                   | Description                                                                        |
| ------------------------------------------------------------------------|------------------------------------------------------------------------------------ |
| [Routing](https://start.ktor.io/p/routing)                             | Provides a structured routing DSL                                                  |
| [Authentication](https://start.ktor.io/p/auth)                         | Provides extension point for handling the Authorization header                     |
| [Authentication OAuth](https://start.ktor.io/p/auth-oauth)             | Handles OAuth Bearer authentication scheme                                         |
| [Authentication JWT](https://start.ktor.io/p/auth-jwt)                 | Handles JSON Web Token (JWT) bearer authentication scheme                          |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation)     | Provides automatic content conversion according to Content-Type and Accept headers |
| [kotlinx.serialization](https://start.ktor.io/p/kotlinx-serialization) | Handles JSON serialization using kotlinx.serialization library                     |
| [Default Headers](https://start.ktor.io/p/default-headers)             | Adds a default set of headers to HTTP responses                                    |
| [Call Logging](https://start.ktor.io/p/call-logging)                   | Logs client requests                                                               |
| [Koin](https://start.ktor.io/p/koin)                                   | Provides dependency injection                                                      |
| [Rate Limiting](https://start.ktor.io/p/ktor-server-rate-limiting)     | Manage request rate limiting as you see fit                                        |

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
| -------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

## API Documentation

This backend provides endpoints for managing country and tourist place information.

### Countries

#### Get All Countries
```
GET /api/countries
```
Returns a list of all countries with their tourist places.

#### Get Country by Name
```
GET /api/countries/{name}
```
Returns a specific country by name.

#### Add Country
```
POST /api/countries
```
Adds a new country or updates an existing one.

Example request body:
```json
{
  "name": "France",
  "flagIcon": "data/images/france-flag.jpg",
  "touristPlaces": [
    {
      "name": "Eiffel Tower",
      "shortDescription": "Iconic iron tower in Paris",
      "longDescription": "The Eiffel Tower is a wrought-iron lattice tower on the Champ de Mars in Paris, France. It is named after the engineer Gustave Eiffel, whose company designed and built the tower.",
      "location": {
        "lat": 48.8584,
        "long": 2.2945
      },
      "images": [
        "data/images/eiffel-tower-1.jpg",
        "data/images/eiffel-tower-2.jpg"
      ]
    }
  ]
}
```

### Images

#### Upload Image
```
POST /api/countries/upload-image
```
Uploads an image file and returns the file path.

Use multipart/form-data with a file field.

## Data Models

### Country
```kotlin
data class Country(
    val name: String,
    val flagIcon: String? = null,
    val touristPlaces: List<TouristPlace> = emptyList()
)
```

### TouristPlace
```kotlin
data class TouristPlace(
    val name: String,
    val shortDescription: String,
    val longDescription: String,
    val location: Location,
    val images: List<String> = emptyList()
)
```

### Location
```kotlin
data class Location(
    val lat: Double,
    val long: Double
)
```
