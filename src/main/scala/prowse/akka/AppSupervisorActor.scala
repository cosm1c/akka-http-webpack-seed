package prowse.akka

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.RemainingPath
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import prowse.BuildInfo
import prowse.akka.example.ExampleWebsocketFlow

import scala.concurrent.Future

class AppSupervisorActor extends Actor with ActorLogging {

    private implicit val actorSystem = context.system
    private implicit val executionContextExecutor = context.dispatcher
    private implicit val materializer = ActorMaterializer()

    private val route: Flow[HttpRequest, HttpResponse, Any] =
    // WebSocket endpoint first is good for reverse proxy setup
        path("ws") {
            handleWebSocketMessages(ExampleWebsocketFlow.create(log))
        } ~
            get {
                pathEndOrSingleSlash {
                    getFromResource("ui/index.html")
                } ~
                    path("buildInfo") {
                        complete(HttpEntity(ContentTypes.`application/json`, BuildInfo.toJson))
                    } ~
                    path(RemainingPath) { filePath =>
                        getFromResource("ui/" + filePath)
                    }
            }


    private var bindingFuture: Future[ServerBinding] = _

    override def preStart(): Unit = {
        bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)
        bindingFuture.onComplete(serverBinding => log.info("Server online - {}", serverBinding))
    }

    override def postStop(): Unit = {
        bindingFuture
            .flatMap { serverBinding =>
                log.info("Server offline - {}", serverBinding)
                serverBinding.unbind()
            }
        ()
    }

    override def receive: Receive = {
        case msg => log.warning("Received unknown message: {}", msg)
    }
}
