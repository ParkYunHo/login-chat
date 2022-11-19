package com.john.chatmgmt.common.constants

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

/**
 * @author yoonho
 * @since 2022.11.19
 */
@Component
@ConfigurationProperties(prefix = "login")
data class AppProps(
    val apis: Map<String, String>,
    val appconfigs: List<Map<String, String>>
)