package by.minsk.kes.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import by.minsk.kes.pipeline.domain.KesEvent;
import by.minsk.kes.pipeline.domain.TestDomain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@SpringBootApplication
public class PipelineApplication {

	private static final Logger LOG = LoggerFactory.getLogger(PipelineApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PipelineApplication.class, args);
	}

	@Bean
	WebClient getWebClient() {
		return WebClient.create("http://localhost:5001");
	}
//	@Bean
//	CommandLineRunner demo(WebClient client) {
//		return args -> {
//			client.get()
//					.uri("/api/numbers")
//					.accept(MediaType.TEXT_EVENT_STREAM)
//					.retrieve()
//					.bodyToFlux(Integer.class)
//					.onErrorResume(e -> {
//						LOG.error("Error during call to /temperatures: ", e);
//						return Flux.empty();
//					})
//					.map(s -> String.valueOf(s))
//					.subscribe(msg -> {
//						System.out.println(msg);
//					});
//		};
//	}
//
//	@Bean
//	CommandLineRunner demo(WebClient client) {
//		final TestDomain dom = new TestDomain();
//		dom.setName("client");
//		dom.setId(1L);
//		return args -> {
//			client.post()
//					.uri("/api/test")
//					.accept(MediaType.APPLICATION_STREAM_JSON)
//					.contentType(MediaType.APPLICATION_STREAM_JSON)
//					.body(BodyInserters.fromObject("data"))
//					.retrieve()
//					.bodyToMono(Void.class)
//					.onErrorResume(e -> {
//						LOG.error("Error during call to /api/event: ", e);
//						return Mono.empty();
//					}).subscribe(s -> System.out.print("Done!"));
//		};
//	}

}
