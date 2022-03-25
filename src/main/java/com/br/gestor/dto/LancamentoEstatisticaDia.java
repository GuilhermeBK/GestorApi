package com.br.gestor.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.br.gestor.model.tipoLancamento;

public class LancamentoEstatisticaDia {

	private tipoLancamento tipo;
	
	private LocalDate dia;
	
	private BigDecimal total;

	public LancamentoEstatisticaDia(tipoLancamento tipo, LocalDate dia, BigDecimal total) {
		super();
		this.tipo = tipo;
		this.dia = dia;
		this.total = total;
	}

	public tipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(tipoLancamento tipo) {
		this.tipo = tipo;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	
	
}
