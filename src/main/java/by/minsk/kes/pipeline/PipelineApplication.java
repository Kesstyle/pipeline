package by.minsk.kes.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@SpringBootApplication
public class PipelineApplication {

	private static final Logger LOG = LoggerFactory.getLogger(PipelineApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PipelineApplication.class, args);
	}

//	@Bean
//	WebClient getWebClient() {
//		return WebClient.create("http://localhost:5001");
//	}
//	@Bean
//	CommandLineRunner demo(WebClient client) {
//		return args -> {
//			client.get()
//					.uri("/temperatures")
//					.accept(MediaType.TEXT_EVENT_STREAM)
//					.retrieve()
//					.bodyToFlux(Integer.class)
//					.onErrorResume(e -> {
//						LOG.error("Error during call to /temperatures: ", e);
//						return Flux.empty();
//					})
//					.map(s -> String.valueOf(s))
//					.subscribe(msg -> {
//						LOG.info(msg);
//					});
//		};
//	}

}
