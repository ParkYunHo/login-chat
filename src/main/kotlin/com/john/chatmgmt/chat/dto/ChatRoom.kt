package com.john.chatmgmt.chat.dto

/**
 * @author yoonho
 * @since 2022.11.19
 */
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
