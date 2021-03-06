package com.tiago.os.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiago.os.dtos.TecnicoDTO;
import com.tiago.os.model.Pessoa;
import com.tiago.os.model.Tecnico;
import com.tiago.os.repository.PessoaRepository;
import com.tiago.os.repository.TecnicoRepository;
import com.tiago.os.service.exceptions.DataIntegratyViolationException;
import com.tiago.os.service.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	/*
	 *_____________RETORNA UM TECNICO PELO ID OU EXIBE MENSAGEM QUE NÃO ENCONTROU________________
	 */
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(/*criação de uma função anonima. -> */
				"Objeto não encontrado! id: " + id + ", tipo: " + Tecnico.class.getName()));
		
		/*pode encontrar um tecnico no BD ou não
		 * por isso uso o OPTIONAL
		 * Apenas a criação dessa exceção não basta
		 * vamos criar uma classe controller exceptionHandler em um sub pacote no Controller*/
	}
	
	/*______________________RETORNA TODOS OS TECNICOS___________________________*/
	
	public List<Tecnico> findAll(){
		return tecnicoRepository.findAll();
	}
	
	/*_______________________________ATUALIZA TECNICO_____________________________________________*/
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);/*-----> old é termo para objeto velho <-----*/
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na Base de Dados!");
		}
			oldObj.setNome(objDTO.getNome());
			oldObj.setCpf(objDTO.getCpf());
			oldObj.setTelefone(objDTO.getTelefone());
			return tecnicoRepository.save(oldObj);
			
			/*se o CPF do ObjDTO for diferente de vazio e diferente do id passado no parametro desse metodo
			 * então o CPF já existe e é um cpf diferente ja cadastrado para esse usuario,
			 * só vai atualizar a informação se o cpf existir e for o mesmo...*/
	}
	
	/*____________DELETAR TECNICO________________*/
	public void delete(Integer id) {
		
		Tecnico obj = findById(id);
		
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Tecnico possui Ordem de Serviço e não pode ser deletado!");
		}
		tecnicoRepository.deleteById(id);
		
		/*findById(id) faz a verifiação se o ID informado existe antes de deletar
		 * passa o id informado ao obj, se o obj tiver em sua lista de OS alguma OS esse tecnico não pode ser deletado*/
	}
	
	/*___________________BUSCA POR CPF_________________*/
		private Pessoa findByCPF(TecnicoDTO objDTO) {
			Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
			if(obj != null) {
					return obj;
			}
			return null;
		}
	
	/*______________ANTES DE CRIAR UM TECNICO, FAZ A VERIFICAÇÃO SE JA EXISTE UM CPF IGUAL NA BD.________________*/
	public Tecnico create(TecnicoDTO objDTO) {
		if(findByCPF(objDTO) != null){
			throw new DataIntegratyViolationException("CPF já cadastrado na Base de Dados!");
		}
		return tecnicoRepository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}

	
	
	/*____________________MANEIRA 1º CRIA TECNICO E PASSA OS ATRIBUTOS PARA O OBJTO DTO________________
	public Tecnico create(TecnicoDTO objDTO) {
		return tecnicoRepository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}*/
	
	/*___________________MANEIRA 2º CRIA TECNICO E PASSA OS ATRIBUTOS PARA O OBJTO DTO____________________________
	public Tecnico create(TecnicoDTO objDTO) {
		Tecnico obj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return tecnicoRepository.save(obj);
	}*/
}