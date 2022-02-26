package com.tiago.os.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiago.os.dtos.TecnicoDTO;
import com.tiago.os.model.Tecnico;
import com.tiago.os.service.TecnicoService;

@RestController //classe controladora gerenciando as requisições http
@RequestMapping(value="/tecnicos")//endpoint inicial, ou seja nome da url
public class TecnicoController {

	@Autowired
	private TecnicoService tecnicoService;
	
	/*endpoint final variavel id
	 * quando informo entre {} significa variavel de path
	 * então no parametro tenho que passar pathVariable o tipo do ID e o id*/
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO>findById(@PathVariable Integer id){
		TecnicoDTO objDTO = new TecnicoDTO( tecnicoService.findById(id));/*para criar esse metodo a classe TecnicoService tem que estar com o metodo findById implementado...*/
		return ResponseEntity.ok().body(objDTO);
		
	}
}