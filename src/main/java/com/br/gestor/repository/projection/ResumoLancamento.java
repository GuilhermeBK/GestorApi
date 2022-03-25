package com.br.gestor.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.br.gestor.model.Lancamento;
import com.br.gestor.model.tipoLancamento;

public class ResumoLancamento {

	private Long codigo;
	private String descricao;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private BigDecimal valor;
	private tipoLancamento tipo;
	private String categoria;
	private String pessoa;

	public ResumoLancamento(Long codigo, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
			BigDecimal valor, tipoLancamento tipo, String categoria, String pessoa) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
		this.pessoa = pessoa;

	}
	
	//usado para resumir de forma mais simples
	public ResumoLancamento(Lancamento lancamento){
		this.codigo = lancamento.getCodigo();
		this.descricao = lancamento.getDescricao();
		this.dataVencimento = lancamento.getDataVencimento();
		this.dataPagamento = lancamento.getDataPagamento();
		this.valor = lancamento.getValor();
		this.tipo = lancamento.getTipo();
		this.categoria = lancamento.getCategoria().getNome();
		this.pessoa = lancamento.getPessoa().getNome();
		
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public tipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(tipoLancamento tipo) {
		this.tipo = tipo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPessoa() {
		return pessoa;
	}

	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}


}
