package com.john.chatmgmt.chat

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint
import kotlin.collections.HashSet

@Service
@ServerEndpoint(value = "/chat")
class ChatService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        val clients = Collections.synchronizedSet(HashSet<Session>())
    }

    @OnMessage
    fun onMessage(msg: String, session: Session){
        for(s: Session in clients){
            logger.info(" >>> [onMessage] msg: {}, session: {}", msg, session.toString())
            s.basicRemote.sendText(msg)
        }
    }

    @OnOpen
    fun onOpen(session: Session){
        if(!clients.contains(session)){
            logger.info(" >>> [onOpen] session: {}", session.toString())
            clients.add(session)
        }
    }

    @OnClose
    fun onClose(session: Session){
        logger.info(" >>> [onClose] session: {}", session.toString())
        clients.remove(session)
    }
}