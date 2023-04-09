package com.musicat.config;

import com.musicat.interceptor.HttpSessionIdHandshakeInterceptor;
import com.musicat.interceptor.WebSocketUserCounterInterceptor;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@EnableScheduling
@EnableWebSocket // [change]
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
  private final WebSocketUserCounterInterceptor userCounterInterceptor;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic", "/queue");
    config.setApplicationDestinationPrefixes("/app");
    config.setUserDestinationPrefix("/user"); // 특정 유저에게 메시지 전송
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws")
        .setAllowedOriginPatterns("*") // 프론트엔드 주소를 허용하도록 설정
        .addInterceptors(new HttpSessionIdHandshakeInterceptor())
        .withSockJS();
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    logger.debug("채널 인바운드 설정");
    registration.interceptors(userCounterInterceptor);
//    registration.interceptors(new HeartbeatInterceptor());
  }

  @Bean // [change]
  public ServletServerContainerFactoryBean configureWebSocketContainer() {
    ServletServerContainerFactoryBean factory = new ServletServerContainerFactoryBean();
    factory.setMaxSessionIdleTimeout(TimeUnit.MINUTES.convert(30, TimeUnit.MILLISECONDS));
    return factory;
  }

}

