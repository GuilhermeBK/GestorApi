package com.br.gestor.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.br.gestor.model.Lancamento;
import com.br.gestor.model.Usuario;

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
	
	public void avisarSobreLancamentosVencidos(
			List<Lancamento> vencidos, List<Usuario> destinatarios) {
		
		String template = "mail/aviso-lancamentos-vencidos.html";
		
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", vencidos);
		
		//TRANSFORMA LISTA DE OBJ USUARIO EM STRING
		List<String> emails = destinatarios
				.stream().map(usuario -> usuario.getEmail()).collect(Collectors.toList());
		
		try {
			this.enviarEmail("guilhermekirsch12@gmail.com", emails, "Lançamentos vencidos", template, variaveis);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	
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
