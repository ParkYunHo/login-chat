package com.john.chatmgmt.api.chat

import com.john.chatmgmt.chat.repository.ChatRepository
import com.john.chatmgmt.common.dto.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author yoonho
 * @since 2022.11.19
 */
@RestController
class ChatController(
    private val chatRepository: ChatRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 전체 채팅방 조회
     *
     * @return BaseResponse [BaseResponse]
     * @author yoonho
     * @since 2022.11.19
     */
    @GetMapping("/api/chat")
    fun findAllRooms(): BaseResponse {
        var allRooms = chatRepository.findAllRoom()
        return BaseResponse().success(allRooms)
    }

    /**
     * 채팅방 생성
     *
     * @param name [String]
     * @return BaseResponse [BaseResponse]
     * @author yoonho
     * @since 2022.11.19
     */
    @PostMapping("/api/chat")
    fun createRoom(@RequestParam name: String): BaseResponse {
        var room = chatRepository.createRoom(name)
        return BaseResponse().success(room)
    }

    /**
     * 채팅방 조회 (by roomId)
     *
     * @param roomId [String]
     * @return BaseResponse [BaseResponse]
     * @author yoonho
     * @since 2022.11.19
     */
    @GetMapping("/api/chat/id")
    fun findRoomById(@RequestParam roomId: String): BaseResponse {
        var room = chatRepository.findRoomById(roomId)
        return BaseResponse().success(room)
    }

    /**
     * 채팅방 조회 (by roomName)
     *
     * @param roomName [String]
     * @return BaseResponse [BaseResponse]
     * @author yoonho
     * @since 2022.11.19
     */
    @GetMapping("/api/chat/name")
    fun findRoomByName(@RequestParam roomName: String): BaseResponse {
        var room = chatRepository.findRoomByName(roomName)
        return BaseResponse().success(room)
    }
}