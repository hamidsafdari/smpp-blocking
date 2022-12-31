/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package hs.jsmpp

import org.jsmpp.SMPPConstant
import org.jsmpp.bean.CancelSm
import org.jsmpp.bean.DataSm
import org.jsmpp.bean.QuerySm
import org.jsmpp.bean.ReplaceSm
import org.jsmpp.bean.SubmitMulti
import org.jsmpp.bean.SubmitMultiResult
import org.jsmpp.bean.SubmitSm
import org.jsmpp.extra.ProcessRequestException
import org.jsmpp.session.DataSmResult
import org.jsmpp.session.QuerySmResult
import org.jsmpp.session.SMPPServerSession
import org.jsmpp.session.ServerMessageReceiverListener
import org.jsmpp.session.Session
import org.jsmpp.util.MessageIDGenerator
import org.jsmpp.util.MessageId
import org.jsmpp.util.RandomMessageIDGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ServerMessageReceiverListenerImpl : ServerMessageReceiverListener {
  private val logger: Logger = LoggerFactory.getLogger(ServerMessageReceiverListenerImpl::class.java)
  private val messageIDGenerator: MessageIDGenerator = RandomMessageIDGenerator()

  @Throws(ProcessRequestException::class)
  override fun onAcceptSubmitSm(submitSm: SubmitSm, source: SMPPServerSession): MessageId {
    logger.info("session {} received {}", source.sessionId, submitSm.shortMessage)
    return messageIDGenerator.newMessageId()
  }

  @Throws(ProcessRequestException::class)
  override fun onAcceptDataSm(dataSm: DataSm, source: Session): DataSmResult {
    logger.info("received data_sm")
    throw ProcessRequestException("the data_sm is not implemented", SMPPConstant.STAT_ESME_RSYSERR)
  }

  @Throws(ProcessRequestException::class)
  override fun onAcceptSubmitMulti(submitMulti: SubmitMulti, source: SMPPServerSession): SubmitMultiResult {
    logger.info("received submit_multi")
    val messageId = messageIDGenerator.newMessageId()
    return SubmitMultiResult(messageId.value)
  }

  @Throws(ProcessRequestException::class)
  override fun onAcceptQuerySm(querySm: QuerySm, source: SMPPServerSession): QuerySmResult {
    logger.info("received query_sm")
    throw ProcessRequestException("the replace_sm is not implemented", SMPPConstant.STAT_ESME_RSYSERR)
  }

  @Throws(ProcessRequestException::class)
  override fun onAcceptReplaceSm(replaceSm: ReplaceSm, source: SMPPServerSession) {
    logger.info("received replace_sm")
    throw ProcessRequestException("the replace_sm is not implemented", SMPPConstant.STAT_ESME_RSYSERR)
  }

  @Throws(ProcessRequestException::class)
  override fun onAcceptCancelSm(cancelSm: CancelSm, source: SMPPServerSession) {
    logger.info("received cancelsm")
  }
}
