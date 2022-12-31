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

import org.jsmpp.extra.SessionState
import org.jsmpp.session.Session
import org.jsmpp.session.SessionStateListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class SessionStateListenerImpl : SessionStateListener {
  private val logger: Logger = LoggerFactory.getLogger(SessionStateListenerImpl::class.java)

  override fun onStateChange(newState: SessionState, oldState: SessionState, source: Session) {
    logger.info("server session {}: {} -> {}", source.sessionId, oldState, newState)
  }
}
