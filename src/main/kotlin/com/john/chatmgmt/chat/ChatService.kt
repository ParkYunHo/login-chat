package com.john.chatmgmt.chat

import com.john.chatmgmt.chat.dto.ChatMessage
import com.john.chatmgmt.chat.dto.ChatRoom
import com.john.chatmgmt.chat.dto.ChatRoomDto
import com.john.chatmgmt.common.exception.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Collectors

@Service
class ChatService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private var chatRooms: LinkedHashMap<String, ChatRoom> = LinkedHashMap()


    fun findAllRoom(): List<ChatRoomDto> = chatRooms.values.stream().map { x -> x.toDto() }.collect(Collectors.toList())

    fun findRoomByName(roomName: String): ChatRoom? = chatRooms.values.stream().filter { x -> x.name.equals(roomName) }.findFirst().orElse(null)

    fun findRoomById(roodId: String): ChatRoom? = chatRooms.get(roodId)

    fun findRoomBySessionId(sessionId: String): ChatRoom? = chatRooms.values.stream().filter {
                                                                x -> x.sessions.values.stream().filter {
                                                                    s -> s.id.equals(sessionId)
                                                                }.findFirst().orElse(null) != null
                                                            }.findFirst().orElse(null)

    fun findUserIdByRoomId(roomId: String, sessionId: String): String {
        var userId = ""
        for(entry: Map.Entry<String, WebSocketSession> in chatRooms[roomId]!!.sessions){
            if(entry.value.id.equals(sessionId)){
                userId = entry.key
            }
        }
        return userId
    }

    fun deleteSession(roomId: String, userId: String) = chatRooms[roomId]!!.sessions.remove(userId)

    fun createRoom(name: String): ChatRoom {
        var check = chatRooms.values.stream().filter { c -> c.name.equals(name) }.findFirst().orElse(null)
        if(check != null){
            logger.error(" >>> [createRoom] invalid chatroom name - name: {}", name)
            throw BadRequestException("이름이 동일한 채팅방은 개설할 수 없습니다.")
        }

        var randomId = UUID.randomUUID().toString()
        var chatRoom = ChatRoom(roomId = randomId, name = name)

        chatRooms.put(randomId, chatRoom)

        return chatRoom
    }

    fun sendMessage(message: ChatMessage, session: WebSocketSession){
        try{
            session.sendMessage(TextMessage(this.messageFormatter(message)))
        }catch (e: Exception){
            logger.error(" >>> [sendMessage] Exception occurs - message: {}", e.message)
        }
    }

    fun messageFormatter(message: ChatMessage): String {
        val formatter = DateTimeFormatter.ofPattern("mm:ss")
        val now = LocalDateTime.now().format(formatter)

        return "[" + now + "]" + "[" + message.sender + "] " + message.message
    }
}