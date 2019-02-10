package fr.offsec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@EnableSwagger2
@SpringBootApplication
public class OffsecApplication {

	public static void main(String[] args) {
		SpringApplication.run(OffsecApplication.class, args);
	}
	
	public class SwaggerConfig {                                    
	    @Bean
	    public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()                                  
	          .apis(RequestHandlerSelectors.any())              
	          .paths(PathSelectors.any())                          
	          .build();                                           
	    }
	}
	
	@Bean
	public ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	        .title("Offensive Security Console")
	        .description("Penetsting Jobs exposed on a REST API")
	        .version("1.0.0")
	        .build();
	}
}
