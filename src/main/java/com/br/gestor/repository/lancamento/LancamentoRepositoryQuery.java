package com.br.gestor.repository.lancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.br.gestor.dto.LancamentoEstatisticaCategoria;
import com.br.gestor.dto.LancamentoEstatisticaDia;
import com.br.gestor.dto.LancamentoEstatisticaPessoa;
import com.br.gestor.model.Lancamento;
import com.br.gestor.repository.filter.LancamentoFilter;
import com.br.gestor.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {
	
	public List<LancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim);
	
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
	
	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia);

	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);
	
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable);
	
}
