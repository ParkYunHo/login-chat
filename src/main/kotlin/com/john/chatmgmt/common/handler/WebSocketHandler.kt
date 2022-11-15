package com.john.chatmgmt.common.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.john.chatmgmt.chat.ChatService
import com.john.chatmgmt.chat.dto.ChatMessage
import com.john.chatmgmt.chat.dto.ChatRoom
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketHandler(
    private val chatService: ChatService
): TextWebSocketHandler() {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        try{
            var payload = message.payload
            var chatMessage = ObjectMapper().readValue(payload, ChatMessage::class.java)
            var chatRoom = chatService.findRoomById(chatMessage.roomId)

            if(chatRoom == null){
                session.sendMessage(TextMessage("[system] 요청한 채팅방이 없습니다."))
            }else{
                this.handleActions(session, chatRoom, chatMessage)
            }
        }catch(e: Exception){
            logger.error(" >>> [handleTextMessage] Exception occurs - msg: {}", e.message)
        }
    }

    private fun handleActions(session: WebSocketSession, chatRoom: ChatRoom, chatMessage: ChatMessage){
        if(chatMessage.type == ChatMessage.MessageType.ENTER) {
            val existsSession = chatService.findRoomBySessionId(session.id)
            if(existsSession != null){
                this.deleteSession(session.id)
            }
            chatRoom.sessions.put(chatMessage.sender!!, session)

            chatMessage.message = chatMessage.sender + "님이 입장하였습니다."
            chatMessage.sender = "system"
        }
        this.sendMessage(chatRoom, chatMessage)
    }

    private fun sendMessage(chatRoom: ChatRoom, message: ChatMessage){
        chatRoom.sessions.values.parallelStream().forEach { s -> chatService.sendMessage(message, s) }
    }

    private fun deleteSession(sessionId: String) {
        val chatRoom = chatService.findRoomBySessionId(sessionId)
        if(chatRoom != null){
            val userId = chatService.findUserIdByRoomId(chatRoom.roomId, sessionId)
            chatService.deleteSession(chatRoom.roomId, userId)
        }
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info(" >>> [Open] sessionId: {}", session.id)
        super.afterConnectionEstablished(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.info(" >>> [Closed] sessionId: {}", session.id)

        this.deleteSession(session.id)

        super.afterConnectionClosed(session, status)
    }
}