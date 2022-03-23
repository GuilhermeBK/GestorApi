package com.br.gestor.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.br.gestor.model.Lancamento;
import com.br.gestor.repository.filter.LancamentoFilter;
import com.br.gestor.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);
	
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable);
	
}
