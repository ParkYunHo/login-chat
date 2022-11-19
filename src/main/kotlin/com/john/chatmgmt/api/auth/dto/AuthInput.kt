package com.john.chatmgmt.api.auth.dto

/**
 * @author yoonho
 * @since 2022.11.19
 */
data class AuthInput (
    var type: String?,
    var accessToken: String?
)