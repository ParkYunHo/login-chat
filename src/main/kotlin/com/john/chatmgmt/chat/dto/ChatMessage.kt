package com.john.chatmgmt.chat.dto

import java.beans.ConstructorProperties

data class ChatMessage @ConstructorProperties("type", "roomId", "sender", "message") constructor(
    val type: MessageType,
    val roomId: String,
    var sender: String?,
    var message: String?,
    var userCnt: Long?
) {
    enum class MessageType {
        ENTER, TALK, QUIT
    }
}