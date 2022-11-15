package com.john.chatmgmt.chat.dto

data class ChatRoom(
    val roomId: String,
    val name: String,
    val sessions: ArrayList<String> = ArrayList()
) {
    fun toDto() = ChatRoomDto(
        roomId = this.roomId,
        name = this.name,
        count = this.sessions.size
    )
}
