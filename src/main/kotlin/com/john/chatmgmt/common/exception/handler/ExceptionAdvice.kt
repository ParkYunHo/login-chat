package com.john.chatmgmt.common.exception.handler

import com.john.chatmgmt.common.dto.BaseResponse
import com.john.chatmgmt.common.exception.TokenInvalidException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(TokenInvalidException::class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    fun tokenInvalidException(e: TokenInvalidException): BaseResponse{
        logger.error(e.message, e)
        return BaseResponse(e.message, HttpStatus.UNAUTHORIZED)
    }
}