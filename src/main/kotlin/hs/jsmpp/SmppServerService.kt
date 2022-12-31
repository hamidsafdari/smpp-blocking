package hs.jsmpp

import org.jsmpp.SMPPConstant
import org.jsmpp.session.BindRequest
import org.jsmpp.session.SMPPServerSession
import org.jsmpp.session.SMPPServerSessionListener
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.concurrent.ConcurrentHashMap

class SmppServerService constructor(private val enquireTimeout: Int, private val bindTimeout: Long, private val transactionTimeout: Long) {
  private val logger = LoggerFactory.getLogger(SmppServerService::class.java)
  private val sessionMap = ConcurrentHashMap<Int, SMPPServerSessionListener>()

  fun connect(port: Int): Mono<SMPPServerSession> {
    return Mono.fromCallable {
      logger.debug("point i0")
      val sessionListener = SMPPServerSessionListener(port)

      logger.debug("point i1")
      sessionListener.run {
        sessionStateListener = SessionStateListenerImpl()
        messageReceiverListener = ServerMessageReceiverListenerImpl()
        setResponseDeliveryListener(ServerResponseDeliveryListenerImpl())

        logger.debug("point i2")
        accept().apply {
          transactionTimer = transactionTimeout
          enquireLinkTimer = enquireTimeout
          logger.debug("point i3")
        }
      }
    }.subscribeOn(Schedulers.boundedElastic())
  }

  fun bind(session: SMPPServerSession): Mono<BindRequest> {
    return Mono.fromCallable { session.waitForBind(bindTimeout) }
      .doOnNext { request -> request.accept(request.systemId, request.interfaceVersion) }
      .onErrorContinue { _, request -> (request as BindRequest).reject(SMPPConstant.STAT_ESME_RSYSERR) }
  }

  fun stop(port: Int): Mono<Boolean> {
    return Mono.create { sink ->
      try {
        sessionMap[port]?.close()
        sessionMap.remove(port)
        sink.success(true)
      } catch (e: Exception) {
        sink.error(e)
      }
    }
  }
}
