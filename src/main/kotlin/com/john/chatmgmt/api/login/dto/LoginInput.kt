package com.john.chatmgmt.api.login.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author yoonho
 * @since 2022.11.19
 */
data class LoginInput(
    var state: String?,
    var code: String?,
    var error: String?,
    @JsonProperty("error_description")
    var errorDescription: String?
)

