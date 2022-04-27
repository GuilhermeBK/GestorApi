package com.br.gestor.mail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.br.gestor.model.Lancamento;
import com.br.gestor.repository.LancamentoRepository;

/*
 * A FUNÇÃO DO GOOGLE POR AUTENTICACAO BASICA POR APPS DE TERCEIROS PAROU DE FUNCIONAR
 * EM 30 DE MAIO DE 2022
 * 
 * ESSA CLASSE NAO VAI MAIS FUNCIONAR
 * 
 * */


@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
	
//	@Autowired
//	private LancamentoRepository repo;
//	
////		@EventListener
////		private void teste(ApplicationReadyEvent event) throws MessagingException {
////			String template = "mail/aviso-lancamentos-vencidos.html";
////			
////			List<Lancamento> lista = repo.findAll();
////			
////			Map<String, Object> values = new HashMap<>();
////			values.put("lancamentos", lista);
////			
////			this.enviarEmail("guilhermekirsch12@gmail.com", Arrays.asList("guilhermebkirsch@gmail.com"),
////					"Testando", template, values);
////		System.out.println("Terminado o envio de email");
////		}
	
	public void enviarEmail(String remetente,
			List<String> destinatarios, String assunto,
			String template, Map<String, Object> variaveis) throws MessagingException {
		
		Context context = new Context(new Locale("pt", "BR"));
		
		variaveis.entrySet().forEach(
				e -> context.setVariable(e.getKey(),e.getValue()));
		
		String mensagem = thymeleaf.process(template, context);
		
		this.enviarEmail(remetente, destinatarios, assunto, mensagem);
		
	}

	
	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
		helper.setFrom(remetente);
		helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
		helper.setSubject(assunto);
		helper.setText(mensagem, true);
		
		mailSender.send(message);
		
		
		
	}

}
