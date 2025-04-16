package tn.atelier.apigateway;
/*
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
                .route("tache-etape-service", r -> r
                        .path("/api/**")

                        .uri("lb://tache-etape"))
                // .uri("http://localhost:8081"))
                .build();
    }
}
*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGateWayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("service", r -> r
                        .path("/api/**")

                        .uri("lb://tache-etape"))
                .build();
    }
}