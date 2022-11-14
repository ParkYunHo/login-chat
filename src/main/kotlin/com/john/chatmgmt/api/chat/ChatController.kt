package com.john.chatmgmt.api.chat

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
class ChatController {

    @GetMapping("/mychat")
    fun chat(): ModelAndView {
        return ModelAndView("chat/chat")
    }
}