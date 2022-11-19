package com.john.chatmgmt.config

import com.john.chatmgmt.common.constants.AppProps
import com.john.chatmgmt.common.utils.AppPropsUtils
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

/**
 * @author yoonho
 * @since 2022.11.19
 */
@Configuration
class AppConfig(
    private val appProps: AppProps
) {
    @PostConstruct
    fun init() {
        AppPropsUtils.setAppProps(appProps)
    }
}