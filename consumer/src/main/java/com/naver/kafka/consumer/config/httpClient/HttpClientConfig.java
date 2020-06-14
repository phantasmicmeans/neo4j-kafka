package com.naver.kafka.consumer.config.httpClient;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class HttpClientConfig {
    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 2000;
    private static final int DEFAULT_CONNECTION_TIMEOUT = 1000;
    private static final int DEFAULT_READ_TIMEOUT = 10000;
    private static final int DEFAULT_WRITE_TIMEOUT = 10000;

    @Bean
    public WebClient.Builder webClientBuilder() {
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DEFAULT_CONNECTION_TIMEOUT)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)));

        ClientHttpConnector connector = new ReactorClientHttpConnector(HttpClient.from(tcpClient));

        return WebClient.builder().clientConnector(connector);
    }

    @Bean
    public WebClient webClient() {
        return webClientBuilder().build();
    }
}