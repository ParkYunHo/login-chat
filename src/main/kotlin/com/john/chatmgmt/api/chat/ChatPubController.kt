package com.john.chatmgmt.api.chat

import com.john.chatmgmt.chat.dto.ChatMessage
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RestController


@Controller
class ChatPubController(
    // "@Contoller" 어노테이션 선언시에만 사용 가능 ("@RestContoller"에서는 사용불가)
    private val template: SimpMessageSendingOperations
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @MessageMapping("/message")
    fun message(message: ChatMessage){
        if(ChatMessage.MessageType.ENTER.equals(message.type)) {
            message.message = message.sender + "님이 입장하였습니다."
        }
        logger.info(" >>> [SubMessage] message: {}", message)

        template.convertAndSend("/sub/chat/" + message.roomId, message)
    }
}