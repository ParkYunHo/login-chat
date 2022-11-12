package com.john.chatmgmt.api.login

import com.john.chatmgmt.api.login.dto.LoginInput
import com.john.chatmgmt.common.constants.CommCode
import com.john.chatmgmt.common.dto.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
class LoginController {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/login")
    fun login(): ModelAndView {
        return ModelAndView("login/login")
    }

    @GetMapping("/login/token")
    fun token(input: LoginInput): BaseResponse {
        var socialName = input.state

        when(socialName) {
            CommCode.Social.KAKAO.code -> logger.info(" >>> [token] kakao login")
            CommCode.Social.NAVER.code -> logger.info(" >>> [token] naver login")
            CommCode.Social.APPLE.code -> logger.info(" >>> [token] apple login")
        }

        return BaseResponse().success(null)
    }
}