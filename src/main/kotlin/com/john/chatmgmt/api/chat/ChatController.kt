package com.john.chatmgmt.api.chat

import com.john.chatmgmt.chat.ChatService
import com.john.chatmgmt.common.dto.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val chatService: ChatService
) {

    @GetMapping("/api/chat")
    fun findAllRooms(): BaseResponse {
        var allRooms = chatService.findAllRoom()
        return BaseResponse().success(allRooms)
    }

    @GetMapping("/api/chat/detail")
    fun findRoom(roomName: String): BaseResponse {
        var room = chatService.findRoomByName(roomName)
        return BaseResponse().success(room)
    }

    @PostMapping("/api/chat")
    fun createRoom(@RequestParam name: String): BaseResponse {
        var room = chatService.createRoom(name)
        return BaseResponse().success(room)
    }
}