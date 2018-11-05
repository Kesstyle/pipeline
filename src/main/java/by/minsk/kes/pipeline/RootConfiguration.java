package by.minsk.kes.pipeline;

import by.minsk.kes.pipeline.router.MainRouter;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = {"by.minsk.kes"})
@PropertySource({"classpath:db.properties"})
@Import(MainRouter.class)
public class RootConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
