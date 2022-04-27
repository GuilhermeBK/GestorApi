package com.br.gestor.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.br.gestor.dto.LancamentoEstatisticaPessoa;
import com.br.gestor.mail.Mailer;
import com.br.gestor.model.Lancamento;
import com.br.gestor.model.Pessoa;
import com.br.gestor.model.Usuario;
import com.br.gestor.repository.LancamentoRepository;
import com.br.gestor.repository.PessoaRepository;
import com.br.gestor.repository.UsuarioRepository;
import com.br.gestor.service.exception.PessoaInexistenteOuInativaException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {

	private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";
	
	private static final Logger log = LoggerFactory.getLogger(LancamentoService.class);
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private Mailer mailer;
	
	
	//@Scheduled(cron = "0 0 6 * * *") envia todo dia as 6 da manha
	//@Scheduled(fixedDelay = 5000) envia a cara 5 segundos
	public void avisarSobreLancamentosVencidos() {
		if (log.isDebugEnabled()) {
			log.debug("iniciando envio de emails");
			
		}
		
		List<Lancamento> vencidos = lancamentoRepository
				.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
		
		if (vencidos.isEmpty()) {
			log.info("Sem lançamentos vencidos");
			//nao envia o email
			return;
		}
		
		log.info("Existem {} lançamentos vencidos", vencidos.size());
		
		
		List<Usuario> destinatarios = usuarioRepository
				.findByPermissoesDescricao(DESTINATARIOS);
		
		if (destinatarios.isEmpty()) {
			log.warn("Existem lançamentos vencidos, mas o sistema nao tem usuarios");
			//nao envia o email
			return;
		}
		
		mailer.avisarSobreLancamentosVencidos(vencidos, destinatarios);
		
		log.info("envio de email concluido");
	}
	
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
