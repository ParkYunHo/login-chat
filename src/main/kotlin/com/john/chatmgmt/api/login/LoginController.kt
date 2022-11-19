package com.john.chatmgmt.api.login

import com.john.chatmgmt.api.login.dto.LoginInput
import com.john.chatmgmt.common.constants.CommCode
import com.john.chatmgmt.common.dto.BaseResponse
import com.john.chatmgmt.common.utils.AppPropsUtils
import com.john.chatmgmt.login.LoginService
import com.john.chatmgmt.login.dto.TokenInfo
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

/**
 * @author yoonho
 * @since 2022.11.19
 */
@RestController
class LoginController(
    private val loginService: LoginService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 로그인 페이지(HTML) 호출
     *
     * @return mv [ModelAndView]
     * @author yoonho
     * @since 2022.11.19
     */
    @GetMapping("/login")
    fun login(): ModelAndView {
        var mv = ModelAndView("login/login")
        mv.addObject("kakaoJsKey", AppPropsUtils.findJsKeyByType(CommCode.Social.KAKAO.code))
        mv.addObject("naverJsKey", AppPropsUtils.findJsKeyByType(CommCode.Social.NAVER.code))

        return mv
    }

    /**
     * 인가코드를 통한 토큰 획득
     *
     * @param input [LoginInput]
     * @return BaseResponse [BaseResponse]
     * @author yoonho
     * @since 2022.11.19
     */
    @GetMapping("/login/token")
    fun token(input: LoginInput): BaseResponse {
        var socialName = input.state

        var tokenInfo: Any? = null
        when(socialName) {
            CommCode.Social.KAKAO.code -> tokenInfo = loginService.kakaoToken(input.code, socialName)
            CommCode.Social.NAVER.code -> tokenInfo = loginService.naverToken(input.code, socialName)
            CommCode.Social.APPLE.code -> logger.info(" >>> [token] apple login")
        }

        return BaseResponse().success(tokenInfo)
    }
}