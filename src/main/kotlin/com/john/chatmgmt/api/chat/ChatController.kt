package com.john.chatmgmt.api.chat

import com.john.chatmgmt.chat.repository.ChatRepository
import com.john.chatmgmt.common.dto.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
class ChatController(
    private val chatRepository: ChatRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/api/chat")
    fun findAllRooms(): BaseResponse {
        var allRooms = chatRepository.findAllRoom()
        return BaseResponse().success(allRooms)
    }

    @PostMapping("/api/chat")
    fun createRoom(@RequestParam name: String): BaseResponse {
        var room = chatRepository.createRoom(name)
        return BaseResponse().success(room)
    }

    @GetMapping("/api/chat/id")
    fun findRoomById(@RequestParam roomId: String): BaseResponse {
        var room = chatRepository.findRoomById(roomId)
        return BaseResponse().success(room)
    }

    @GetMapping("/api/chat/name")
    fun findRoomByName(@RequestParam roomName: String): BaseResponse {
        var room = chatRepository.findRoomByName(roomName)
        return BaseResponse().success(room)
    }
}