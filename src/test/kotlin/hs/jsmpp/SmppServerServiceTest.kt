package hs.jsmpp

import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.LoggerFactory
import reactor.test.StepVerifier
import java.net.Socket
import kotlin.random.Random

@ExtendWith(MockitoExtension::class)
internal class SmppServerServiceTest {
  private val logger = LoggerFactory.getLogger(SmppServerServiceTest::class.java)

  @Test
  fun `restart after timeout`() {
    logger.debug("point 0")
    val port = Random.nextInt(30000, 31000)
    val service = SmppServerService(1000, 1000, 1000)

    logger.debug("point 1: {}", port)
    StepVerifier.create(service.connect(port)
      .flatMap { session ->
        logger.debug("point 2: {}", session.sessionId)
        service.bind(session)
      })
      .expectNextMatches {
        logger.debug("point 3")
        // this will basically function as connect but since it's a simple socket
        // and not an smpp connection, bind will never be called, resulting in a
        // bind timeout
        Socket("127.0.0.1", port)
        Thread.sleep(5000)
        true
      }
      .verifyComplete()
  }
}
