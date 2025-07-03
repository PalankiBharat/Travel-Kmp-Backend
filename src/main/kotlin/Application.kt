import com.travelKmp.configureMonitoring
import com.travelKmp.configureRouting
import com.travelKmp.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


fun Application.module() {
   // configureSecurity()
    configureSerialization()
  //  configureHTTP()
    configureMonitoring()
  //  configureFrameworks()
 //   configureAdministration()
    configureRouting()
}
