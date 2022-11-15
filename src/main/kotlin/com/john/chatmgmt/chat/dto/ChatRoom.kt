package com.john.chatmgmt.chat.dto

import org.springframework.web.socket.WebSocketSession

data class ChatRoom(
    val roomId: String,
    val name: String,
    var sessions: LinkedHashMap<String, WebSocketSession> = LinkedHashMap()
) {
    fun toDto() = ChatRoomDto(
        roomId = this.roomId,
        name = this.name,
        sessionCnt = this.sessions.size,
        users = sessions.keys
    )
}
