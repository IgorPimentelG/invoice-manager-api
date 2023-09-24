package com.ms.service.auth.main.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info = @Info(title = "Service Auth", version = "0.0.1"))
public class OpenApiConfiguration {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
		  .components(new Components())
		  .info(new io.swagger.v3.oas.models.info.Info()
		    .title("Service Auth")
		    .version("0.0.1")
		    .license(new License()
		      .name("Apache 2.0")
		      .url("https://www.apache.org/licenses/LICENSE-2.0")
		  ));
	}
}
