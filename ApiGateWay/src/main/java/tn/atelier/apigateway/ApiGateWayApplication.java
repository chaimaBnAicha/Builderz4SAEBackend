package tn.atelier.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpMethod;
import java.time.Duration;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGateWayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("backenddb-service", r -> r
                        .path("/api/**")
                        .filters(f -> f
                            .preserveHostHeader()
                            .retry(retryConfig -> retryConfig
                                .setRetries(3)
                                .setMethods(HttpMethod.POST, HttpMethod.GET))
                            .rewritePath("/api/(?<segment>.*)", "/api/${segment}"))
                        .uri("lb://BackEnd"))
                .build();
    }
}
