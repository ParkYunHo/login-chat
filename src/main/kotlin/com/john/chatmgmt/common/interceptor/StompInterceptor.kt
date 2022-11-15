package com.john.chatmgmt.common.interceptor

import com.john.chatmgmt.chat.repository.ChatRepository
import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component
import java.util.*

@Component
class StompInterceptor(
    private val chatRepository: ChatRepository
): ChannelInterceptor {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)

        when(accessor.command) {
            StompCommand.CONNECT -> {
                logger.info(" >>> [CONNECT]")
            }
            StompCommand.SUBSCRIBE -> {
                var simpDestination = Optional.ofNullable(message.headers.get("simpDestination")).orElse(null)
                var roomId = ""
                if(simpDestination != null){
                    roomId = chatRepository.extractRoomId(simpDestination.toString())
                }
                var sessionId = Optional.ofNullable(message.headers.get("simpSessionId")).orElse(null)

                logger.info(" >>> [SUBSCRIBE] roomId: {}, sessionId: {}", roomId, sessionId)

                if(roomId != null && sessionId != null){
                    chatRepository.saveSession(roomId.toString(), sessionId.toString())
                }
            }
            StompCommand.DISCONNECT -> {
                var sessionId = Optional.ofNullable(message.headers.get("simpSessionId")).orElse(null).toString()
                var roomId = chatRepository.findRoomBySessionId(sessionId)

                logger.info(" >>> [DISCONNECT] roomId: {}, sessionId: {}", roomId, sessionId)

                if(roomId != null && sessionId != null){
                    chatRepository.deleteSession(roomId.toString(), sessionId.toString())
                }
            }
        }
        return message
    }
}