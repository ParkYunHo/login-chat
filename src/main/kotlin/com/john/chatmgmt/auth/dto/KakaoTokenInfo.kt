package com.john.chatmgmt.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoTokenInfo(
    val id: Long,
    @JsonProperty("expires_in")
    var expiresIn: Int?,
    @JsonProperty("expiresInMillis")
    var expiresInMillis: Int?,
    @JsonProperty("appId")
    var appId: Int?,

    val code: Int?,
    val msg: String?,
)