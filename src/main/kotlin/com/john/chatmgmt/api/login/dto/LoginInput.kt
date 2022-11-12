package com.john.chatmgmt.api.login.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginInput(
    var state: String?,
    var code: String?,
    var error: String?,
    @JsonProperty("error_description")
    var errorDescription: String?
)

