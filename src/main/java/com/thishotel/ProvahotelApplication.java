package com.thishotel;

import com.thishotel.util.EnvUtil;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class ProvahotelApplication {

	public static void main(String[] args) {
		EnvUtil.setEnv2();
		SpringApplication.run(ProvahotelApplication.class, args);
	}

}
