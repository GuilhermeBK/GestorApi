package com.br.gestor.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.br.gestor.dto.LancamentoEstatisticaPessoa;
import com.br.gestor.model.Lancamento;
import com.br.gestor.model.Pessoa;
import com.br.gestor.repository.LancamentoRepository;
import com.br.gestor.repository.PessoaRepository;
import com.br.gestor.service.exception.PessoaInexistenteOuInativaException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
//	@Scheduled(cron = "")
//	public void avisarSobreLancamentosVencidos() {
//		System.out.println("----------------------------------------executando");
//	}
	
	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws JRException {
		List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(inicio, fim);
		
		//converte
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(inicio));
		parametros.put("DT_FIM", Date.valueOf(fim));
		parametros.put("REPORTE_LOCALE", new Locale("pt", "BR"));
		
		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");
	
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,
				new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
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
