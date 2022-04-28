package com.br.gestor.storage;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Tag;
import com.br.gestor.config.property.GestorFinanceiroApiProperty;

@Component
public class S3 {

	private static final Logger log = LoggerFactory.getLogger(S3.class);
	
	@Autowired
	private GestorFinanceiroApiProperty apiProperty;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	public String salvarTemporariamente(MultipartFile arquivo) {
		AccessControlList accessControlList = new AccessControlList();
		accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(arquivo.getContentType());;
		metadata.setContentLength(arquivo.getSize());
		
		String nomeUnico = gerarNomeUnico(arquivo.getOriginalFilename());
		
		try {
			PutObjectRequest objectRequest = new PutObjectRequest(
					apiProperty.getS3().getBucket(),
					nomeUnico,
					arquivo.getInputStream(),
					metadata)
					.withAccessControlList(accessControlList);
			
			objectRequest.setTagging(new ObjectTagging(
					Arrays.asList(new Tag("expirar", "true"))));
			
			amazonS3.putObject(objectRequest);
			
			if (log.isDebugEnabled()) {
				log.debug("Arquivo {} enviado com sucesso para o S3", arquivo.getOriginalFilename());
			}
			
			return nomeUnico;
		} catch (IOException e) {
			throw new RuntimeException("Problemas ao enviar arquivo para o S3 ", e);
		}
	}
	
	public String configurarUrl(String objeto) {
		return "http://" + apiProperty.getS3().getBucket() + ".s3.amazonaws.com/" + objeto; 
	}

	private String gerarNomeUnico(String originalFilename) {
		return UUID.randomUUID().toString() + "_" + originalFilename;
	}
	
}
