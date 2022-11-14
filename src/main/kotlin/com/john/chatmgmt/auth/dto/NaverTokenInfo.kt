package com.john.chatmgmt.auth.dto

data class NaverTokenInfo (
    val resultcode: String?,
    val message: String?,
    val response: NaverUserInfo?
) {
    data class NaverUserInfo(
        val id: String?,
        val nickname: String?,
        val name: String?,
        val email: String?,
        val gender: String?,
        val age: String?,
        val birthday: String?,
        val profile_image: String?,
        val birthyear: String?,
        val mobile: String?
    )
}