package com.br.gestor.repository.lancamento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import com.br.gestor.dto.LancamentoEstatisticaCategoria;
import com.br.gestor.dto.LancamentoEstatisticaDia;
import com.br.gestor.dto.LancamentoEstatisticaPessoa;
import com.br.gestor.model.Categoria_;
import com.br.gestor.model.Lancamento;
import com.br.gestor.model.Lancamento_;
import com.br.gestor.model.Pessoa_;
import com.br.gestor.repository.filter.LancamentoFilter;
import com.br.gestor.repository.projection.ResumoLancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public List<LancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaPessoa> query = builder.createQuery(LancamentoEstatisticaPessoa.class);
		
		Root<Lancamento> root = query.from(Lancamento.class);
		
		query.select(builder.construct(LancamentoEstatisticaPessoa.class,
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.pessoa),
				builder.sum(root.get(Lancamento_.valor))));
		
		LocalDate primeiroDia = inicio.withDayOfMonth(1);
		LocalDate ultimoDia = fim.withDayOfMonth(fim.lengthOfMonth());
		
		query.where(
				builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDia),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDia)
				);
		
		query.groupBy(root.get(Lancamento_.tipo),root.get(Lancamento_.dataVencimento));
		
		TypedQuery<LancamentoEstatisticaPessoa> typedQuery = manager.createQuery(query);		
		
		
		return typedQuery.getResultList();	
	}
	
	@Override
	//estatistica por dia dentro do mes atual
	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaDia> query = builder.createQuery(LancamentoEstatisticaDia.class);
		Root<Lancamento> root = query.from(Lancamento.class);
		
		query.select(builder.construct(LancamentoEstatisticaDia.class,
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.dataVencimento),
				builder.sum(root.get(Lancamento_.valor))));
		
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		query.where(
				builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDia),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDia)
				);
		
		query.groupBy(root.get(Lancamento_.tipo),root.get(Lancamento_.dataVencimento));
		
		TypedQuery<LancamentoEstatisticaDia> typedQuery = manager.createQuery(query);		
		
		
		return typedQuery.getResultList();	
	}
	
	@Override
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaCategoria> query = builder.createQuery(LancamentoEstatisticaCategoria.class);
		Root<Lancamento> root = query.from(Lancamento.class);
		
		query.select(builder.construct(LancamentoEstatisticaCategoria.class,
				root.get(Lancamento_.categoria),
				builder.sum(root.get(Lancamento_.valor))));
		
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		query.where(
				builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDia),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDia)
				);
		
		query.groupBy(root.get(Lancamento_.categoria));
		
		TypedQuery<LancamentoEstatisticaCategoria> typedQuery = manager.createQuery(query);		
		
		
		return typedQuery.getResultList();	
	}
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		
		//CRIA AS RESTRICOES
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(filter)) ;
	}


	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class 
				,root.get(Lancamento_.codigo), root.get(Lancamento_.descricao)
				,root.get(Lancamento_.dataVencimento), root.get(Lancamento_.dataPagamento)
				,root.get(Lancamento_.valor), root.get(Lancamento_.tipo)
				,root.get(Lancamento_.categoria).get(Categoria_.nome)
				,root.get(Lancamento_.pessoa).get(Pessoa_.nome)));
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		
		
		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(filter)) ;
	}

	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(!ObjectUtils.isEmpty(filter.getDescricao())) {
		    predicates.add(builder.like(
		            builder.lower(root.get(Lancamento_.descricao)), "%" + filter.getDescricao().toLowerCase() + "%"));
		}
		
		if (filter.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoDe()));
		}
		
		if (filter.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoAte()));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long total(LancamentoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(filter,builder, root);
			criteria.where(predicates);
			
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}
	
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
		
	}





	
	
	
}
