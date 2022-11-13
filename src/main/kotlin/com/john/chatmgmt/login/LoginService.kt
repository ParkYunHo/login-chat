package com.john.chatmgmt.login

import com.john.chatmgmt.common.exception.KakaoServerException
import com.john.chatmgmt.common.exception.TokenInvalidException
import com.john.chatmgmt.common.utils.AppPropsUtils
import com.john.chatmgmt.login.dto.TokenInfo
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

@Service
class LoginService (
    private val webClientBuilder: WebClient.Builder
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private lateinit var webClient: WebClient

    @PostConstruct
    fun initWebClient() {
        this.webClient = webClientBuilder.build()
    }

    fun kakaoToken(code: String?, type: String): TokenInfo? {
        try{
            var appconfig = AppPropsUtils.findClientInfoByType(type)

            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            params.add("grant_type", "authorization_code")
            params.add("client_id", appconfig?.get("clientId"))
            params.add("code", code)
            params.add("client_secret", appconfig?.get("clientSecret"))

            val uriComponents = UriComponentsBuilder
                .fromHttpUrl(AppPropsUtils.findUrl("kauth") + "/oauth/token")
                .queryParams(params)
                .build(false)

            logger.info(" >>> [kakaoToken] request - url: {}", uriComponents.toString())
            var responseEntity = webClient.get()
                .uri(uriComponents.toUri())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError) { Mono.error(TokenInvalidException("kakao token invalid")) }
                .onStatus(HttpStatus::is5xxServerError) { Mono.error(KakaoServerException("kauth internal server error")) }
                .toEntity(TokenInfo::class.java)
                .block()
            logger.info(" >>> [kakaoToken] response - statusCode: {}, body: {}", responseEntity?.statusCodeValue, responseEntity?.body)

            return responseEntity?.body
        }catch(e: Throwable){
            throw e
        }
    }
}