package com.br.gestor.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
		validaPessoa(lancamento.getPessoa().getCodigo());
		return lancamentoRepository.save(lancamento);
			
		
	}
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = lancamentoRepository.findById(codigo).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		validaPessoa(lancamento.getPessoa().getCodigo());
		
		//copia os dados e ignora o campo do codigo
			BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
			
			return lancamentoRepository.save(lancamentoSalvo);
	}
	
	public void validaPessoa(Long codigo){
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo); 
		
		if (!pessoa.isPresent() || pessoa.get().getAtivo() == false ) {
			throw new PessoaInexistenteOuInativaException();
		}
		
	}
	
}
