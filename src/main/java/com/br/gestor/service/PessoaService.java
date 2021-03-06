package com.br.gestor.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.gestor.model.Pessoa;
import com.br.gestor.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaRepository.findById(codigo).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		pessoaSalva.getContatos().clear();
		pessoaSalva.getContatos().addAll(pessoa.getContatos());
		pessoaSalva.getContatos().forEach(contatos -> contatos.setPessoa(pessoaSalva));
		
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo", "contatos");
		
		return pessoaRepository.save(pessoaSalva);
	}

	public void atualizarAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = pessoaRepository.findById(codigo).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
		
	}
	
	public ResponseEntity<Pessoa> listarPorId(Long id){
		Optional<Pessoa> pessoaPorId = pessoaRepository.findById(id);
		
		if (pessoaPorId.isPresent()) {
			return ResponseEntity.ok(pessoaPorId.get());
		} else {
			return ResponseEntity.noContent().build();
		}
		
	}

	//seta o obj pessoa dessa forma, pois o json ignora o obj pessoa na classe Contato
	public Pessoa save(Pessoa pessoa) {
		pessoa.getContatos().forEach(contatos -> contatos.setPessoa(pessoa));
		
		return pessoaRepository.save(pessoa);
		
	}

}
