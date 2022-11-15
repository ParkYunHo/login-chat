package com.john.chatmgmt.chat.dto

data class ChatRoomDto(
    var roomId: String,
    var name: String,
    var sessionCnt: Int,
    var users: Set<String>
)
