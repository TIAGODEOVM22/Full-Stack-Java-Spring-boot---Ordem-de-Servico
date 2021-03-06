package com.tiago.os.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.tiago.os.service.DBservice;

@Configuration
@Profile("teste")/*Através desta anotação o aplication.properties identifica que temos um perfil e vamos chama-lo de --->  test
sempre que este perfil estiver ativo ira chamar o metodo instanciaDB*/
public class TesteConfig {
	
	@Autowired
	private DBservice dbservice;
	
	
	@Bean
	public void instanciaDB() {
		this.dbservice.instaciaDB();
	}	
}