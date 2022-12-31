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

import org.jsmpp.bean.SubmitMultiResult
import org.jsmpp.session.SMPPServerSession
import org.jsmpp.session.ServerResponseDeliveryListener
import org.jsmpp.util.MessageId
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class ServerResponseDeliveryListenerImpl : ServerResponseDeliveryListener {
  private val logger: Logger = LoggerFactory.getLogger(ServerResponseDeliveryListenerImpl::class.java)

  override fun onSubmitSmRespSent(messageId: MessageId, source: SMPPServerSession) {
    logger.debug("submit_sm_resp_sent with message id {} on session {}", messageId, source.sessionId)
  }

  override fun onSubmitSmRespError(messageId: MessageId, cause: Exception, source: SMPPServerSession) {
    logger.error("submit_sm_resp_error with message id {} on session {}: {}", messageId, source.sessionId, cause.message)
  }

  override fun onSubmitMultiRespSent(submitMultiResult: SubmitMultiResult, source: SMPPServerSession) {
    logger.info("submit_multi_resp_sent")
  }

  override fun onSubmitMultiRespError(submitMultiResult: SubmitMultiResult, cause: Exception, source: SMPPServerSession) {
    logger.error("submit_sm_resp_error with message id {} on session {}: {}", submitMultiResult.messageId, source.sessionId, cause.message)
  }
}
