package com.john.chatmgmt.api.auth

import com.john.chatmgmt.api.auth.dto.AuthInput
import com.john.chatmgmt.auth.AuthService
import com.john.chatmgmt.common.constants.CommCode
import com.john.chatmgmt.common.dto.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/auth/token")
    fun authToken(input: AuthInput): BaseResponse {
        var tokenInfo: Any? = null
        when(input.type) {
            CommCode.Social.KAKAO.code -> tokenInfo = authService.kakaoAuth(input.accessToken)
            CommCode.Social.NAVER.code -> tokenInfo = authService.naverAuth(input.accessToken)
            CommCode.Social.APPLE.code -> logger.info(" >>> [token] apple login")
        }

        return BaseResponse().success(tokenInfo)
    }
}