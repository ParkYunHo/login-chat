package com.john.chatmgmt.common.exception.handler

import com.john.chatmgmt.common.dto.BaseResponse
import com.john.chatmgmt.common.exception.BadRequestException
import com.john.chatmgmt.common.exception.KakaoServerException
import com.john.chatmgmt.common.exception.TokenInvalidException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * @author yoonho
 * @since 2022.11.19
 */
@RestControllerAdvice
class ExceptionAdvice {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(TokenInvalidException::class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    fun tokenInvalidException(e: TokenInvalidException): BaseResponse{
        logger.error(e.message, e)
        return BaseResponse(e.message, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(KakaoServerException::class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun kakaoServerException(e: KakaoServerException): BaseResponse{
        logger.error(e.message, e)
        return BaseResponse(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun badRequestException(e: BadRequestException): BaseResponse{
        logger.error(e.message, e)
        return BaseResponse(e.message, HttpStatus.BAD_REQUEST)
    }
}