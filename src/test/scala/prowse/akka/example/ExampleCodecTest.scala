package prowse.akka.example

import org.scalatest.FunSuite

class ExampleCodecTest extends FunSuite {

    test("Binary") {
        val expected = ExampleModel("Test message")
        val actual = ExampleCodec.unmarshallBinary(ExampleCodec.marshallAsBinary(expected))
        expected === actual
    }

    test("Text") {
        val expected = ExampleModel("Test message")
        val actual = ExampleCodec.unmarshallText(ExampleCodec.marshallAsText(expected))
        expected === actual
    }

}
