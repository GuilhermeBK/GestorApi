package com.br.gestor.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.gestor.model.Lancamento;
import com.br.gestor.model.Pessoa;
import com.br.gestor.repository.LancamentoRepository;
import com.br.gestor.repository.PessoaRepository;
import com.br.gestor.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		//verifica se a pessoa existe ou é ativa antes de salvar
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		if (!pessoa.isPresent() || pessoa.get().getAtivo() == false ) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
			
		
	}
	
}
