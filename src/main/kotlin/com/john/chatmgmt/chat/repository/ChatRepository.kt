package com.john.chatmgmt.chat.repository

import com.john.chatmgmt.chat.dto.ChatRoom
import com.john.chatmgmt.chat.dto.ChatRoomDto
import com.john.chatmgmt.common.exception.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

@Repository
class ChatRepository {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val chatRoomMap: LinkedHashMap<String, ChatRoom> = LinkedHashMap()

    fun findAllRoom(): List<ChatRoomDto> {
        var rooms = ArrayList<ChatRoomDto>(chatRoomMap.values.map { x -> x.toDto() })
        rooms.reverse()
        return rooms
    }

    fun findRoomById(roodId: String): ChatRoomDto? = chatRoomMap.get(roodId)?.toDto()

    fun findRoomByName(roomName: String): ChatRoomDto? = chatRoomMap.values.stream().filter { x -> x.name.equals(roomName) }.findFirst().orElse(null).toDto()

    fun findRoomBySessionId(sessionId: String): ChatRoomDto? {
        return chatRoomMap.values.stream().filter {
                x -> x.sessions.stream().filter { s -> s.equals(sessionId) }.findFirst().orElse(null) != null
        }.findFirst().orElse(null).toDto()
    }

    fun createRoom(name: String): ChatRoomDto {
        var check = chatRoomMap.values.stream().filter { x -> x.name.equals(name) }.findFirst().orElse(null)
        if(check != null){
            logger.error(" >>> [createRoom] invalid chatroom name - name: {}", name)
            throw BadRequestException("이름이 동일한 채팅방은 개설할 수 없습니다.")
        }

        var randomId = UUID.randomUUID().toString()
        var chatRoom = ChatRoom(roomId = randomId, name = name)

        chatRoomMap.put(randomId, chatRoom)

        return chatRoom.toDto()
    }

    fun saveSession(roomId: String, sessionId: String) {
        chatRoomMap.get(roomId)?.sessions?.add(sessionId)
    }

    fun deleteSession(roomId: String, sessionId: String) {
        chatRoomMap.get(roomId)?.sessions?.remove(sessionId)
    }

    fun extractRoomId(simpDestination: String?): String {
        var roomId = ""
        if(!simpDestination.isNullOrBlank()){
            roomId = simpDestination.split("/")[3]
        }
        return roomId
    }
}