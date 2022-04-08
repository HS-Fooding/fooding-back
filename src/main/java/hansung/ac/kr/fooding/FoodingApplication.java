package hansung.ac.kr.fooding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class FoodingApplication {
//    @Autowired
//    InitData initData;
    public static void main(String[] args) {
        SpringApplication.run(FoodingApplication.class, args);
    }

   /* @Bean
    public AuditorAware<String> auditorProvider() {
        if (SecurityContextHolder.getContext() == null) {
            return () -> null;
        }
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }*/
}



//@EnableJpaAuditing
//@SpringBootApplication
//public class FoodingApplication extends SpringBootServletInitializer {
////	@Autowired
////	InitData initData;
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(FoodingApplication.class);
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(FoodingApplication.class, args);
//	}
//
//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		if (SecurityContextHolder.getContext() == null) {
//			return () -> null;
//		}
//		return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
//	}
//}
