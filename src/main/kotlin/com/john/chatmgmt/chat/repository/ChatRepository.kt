package com.john.chatmgmt.chat.repository

import com.john.chatmgmt.chat.dto.ChatRoom
import com.john.chatmgmt.chat.dto.ChatRoomDto
import com.john.chatmgmt.common.exception.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

/**
 * @author yoonho
 * @since 2022.11.19
 */
@Repository
class ChatRepository {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val chatRoomMap: LinkedHashMap<String, ChatRoom> = LinkedHashMap()

    /**
     * 전체 채팅방 조회
     *
     * @return rooms [ArrayList<ChatRoomDto>]
     * @author yoonho
     * @since 2022.11.19
     */
    fun findAllRoom(): List<ChatRoomDto> {
        var rooms = ArrayList<ChatRoomDto>(chatRoomMap.values.map { x -> x.toDto() })
        rooms.reverse()
        return rooms
    }

    /**
     * 채팅방 조회 (by roomId)
     *
     * @param roodId [String]
     * @return ChatRoomDto [ChatRoomDto]
     * @author yoonho
     * @since 2022.11.19
     */
    fun findRoomById(roodId: String): ChatRoomDto? = chatRoomMap.get(roodId)?.toDto()

    /**
     * 채팅방 조회 (by roomName)
     *
     * @param roomName [String]
     * @return ChatRoomDto [ChatRoomDto]
     * @author yoonho
     * @since 2022.11.19
     */
    fun findRoomByName(roomName: String): ChatRoomDto? = chatRoomMap.values.stream().filter { x -> x.name.equals(roomName) }.findFirst().orElse(null).toDto()

    /**
     * 채팅방 조회 (by sessionId)
     *
     * @param sessionId [String]
     * @return ChatRoomDto [ChatRoomDto]
     * @author yoonho
     * @since 2022.11.19
     */
    fun findRoomBySessionId(sessionId: String): ChatRoomDto? {
        return chatRoomMap.values.stream().filter {
                x -> x.sessions.stream().filter { s -> s.equals(sessionId) }.findFirst().orElse(null) != null
        }.findFirst().orElse(null).toDto()
    }

    /**
     * 채팅방 생성
     *
     * @param name [String]
     * @return ChatRoomDto [ChatRoomDto]
     * @author yoonho
     * @since 2022.11.19
     */
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

    /**
     * 채팅방에 접속한 세션 저장
     *
     * @param roomId [String]
     * @param sessionId [String]
     * @author yoonho
     * @since 2022.11.19
     */
    fun saveSession(roomId: String, sessionId: String) {
        chatRoomMap.get(roomId)?.sessions?.add(sessionId)
    }

    /**
     * 채팅방에서 나간 세션 삭제
     *
     * @param roomId [String]
     * @param sessionId [String]
     * @author yoonho
     * @since 2022.11.19
     */
    fun deleteSession(roomId: String, sessionId: String) {
        chatRoomMap.get(roomId)?.sessions?.remove(sessionId)
    }

    /**
     * 채팅방ID 추출
     *
     * @param simpDestination [String]
     * @return roomId [String]
     * @author yoonho
     * @since 2022.11.19
     */
    fun extractRoomId(simpDestination: String?): String {
        var roomId = ""
        if(!simpDestination.isNullOrBlank()){
            roomId = simpDestination.split("/")[3]
        }
        return roomId
    }
}