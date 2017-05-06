package prowse.akka.example

import java.nio.charset.Charset

import akka.NotUsed
import akka.event.LoggingAdapter
import akka.http.scaladsl.model.ws.{BinaryMessage, Message, TextMessage}
import akka.stream.scaladsl.{Flow, Sink, Source, SourceQueueWithComplete}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.util.ByteString
import spray.json.{DefaultJsonProtocol, RootJsonFormat, pimpString}

case class ExampleModel(message: String)

object ExampleCodec {

    import DefaultJsonProtocol._

    private val CHARSET = Charset.forName("UTF-8")

    implicit val jsonFormat: RootJsonFormat[ExampleModel] = DefaultJsonProtocol.jsonFormat1(ExampleModel.apply)

    def marshallAsText(model: ExampleModel): String = jsonFormat.write(model).compactPrint

    def unmarshallText(text: String): ExampleModel = jsonFormat.read(text.parseJson)

    def marshallAsBinary(model: ExampleModel): ByteString = ByteString(model.message, CHARSET)

    def unmarshallBinary(binary: ByteString): ExampleModel = ExampleModel(binary.decodeString(CHARSET))
}

object ExampleWebsocketFlow {

    def create(log: LoggingAdapter)(implicit materializer: ActorMaterializer): Flow[Message, Message, NotUsed] = {

        val inbound: Sink[Message, Any] = Sink.foreach {
            case bm: BinaryMessage =>
                bm.dataStream.runForeach(byteString => {
                    val model: ExampleModel = ExampleCodec.unmarshallBinary(byteString)
                    log.info("""RCV bin: "{}"""", model.message)
                })
                ()

            case tm: TextMessage =>
                tm.textStream.runForeach(text => {
                    val model: ExampleModel = ExampleCodec.unmarshallText(text)
                    log.info("""RCV txt: "{}"""", model.message)
                })
                ()
        }

        val outbound: Source[Message, SourceQueueWithComplete[Message]] = Source.queue[Message](16, OverflowStrategy.fail)

        Flow.fromSinkAndSourceMat(inbound, outbound)((_, outboundMat) => {
            outboundMat.offer(TextMessage(ExampleCodec.marshallAsText(ExampleModel("Text from server"))))
            outboundMat.offer(BinaryMessage(ExampleCodec.marshallAsBinary(ExampleModel("Binary from server"))))
            NotUsed
        })
    }

}
