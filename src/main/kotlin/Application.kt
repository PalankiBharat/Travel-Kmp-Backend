package com.travelKmp

import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)


fun Application.module() {
   // configureSecurity()
    configureSerialization()
    configureHTTP()
    configureMonitoring()
    configureFrameworks()
 //   configureAdministration()
    configureRouting()
}
