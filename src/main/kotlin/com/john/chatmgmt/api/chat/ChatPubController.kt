package com.john.chatmgmt.api.chat

import com.john.chatmgmt.chat.dto.ChatMessage
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.RestController


/**
 * @author yoonho
 * @since 2022.11.19
 */
@RestController
class ChatPubController(
    private val template: SimpMessageSendingOperations
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * STOMP프로토콜 메세지 수신
     *
     * @param message [ChatMessage]
     * @author yoonho
     * @since 2022.11.19
     */
    @MessageMapping("/message")
    fun message(message: ChatMessage){
        if(ChatMessage.MessageType.ENTER.equals(message.type)) {
            message.message = message.sender + "님이 입장하였습니다."
        }
        logger.info(" >>> [SubMessage] message: {}", message)

        template.convertAndSend("/sub/chat/" + message.roomId, message)
    }
}