package com.john.chatmgmt.config

import com.john.chatmgmt.common.interceptor.StompInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

/**
 * @author yoonho
 * @since 2022.11.19
 */
@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val stompInterceptor: StompInterceptor
): WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/sub")
        registry.setApplicationDestinationPrefixes("/pub")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(stompInterceptor)
    }
}