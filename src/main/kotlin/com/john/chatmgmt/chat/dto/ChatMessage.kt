package com.john.chatmgmt.chat.dto

import java.beans.ConstructorProperties

/**
 * @author yoonho
 * @since 2022.11.19
 */
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
