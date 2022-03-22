package com.example.boot.fileProcessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class RestFileUploadAndDownloadApplicationTests {
	
	private static final String FILE_DOWNLOAD_PATH="http://localhost:8080/download/";
	private static final String FILE_UPLOAD_PATH="http://localhost:8080/upload";
	@Autowired
	RestTemplate restTemplate;
	@Test
	void testUpload() {
		HttpHeaders header=new HttpHeaders();
		header.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body=new LinkedMultiValueMap<>();
		body.add("file", new ClassPathResource(("a.jpeg")));
		HttpEntity<MultiValueMap<String, Object>> httpEntity=new HttpEntity<>(body,header);
		ResponseEntity<Boolean> postForEntity = restTemplate.postForEntity(FILE_UPLOAD_PATH, httpEntity, Boolean.class);
		System.out.println(postForEntity);
	}
	
	@Test
	void testDownload() throws IOException {
		HttpHeaders header=new HttpHeaders(); 
		header.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
		HttpEntity<String> httpEntity=new HttpEntity<>(header);
		String fileName="a.jpeg";
		ResponseEntity<byte[]> response=restTemplate.exchange(FILE_DOWNLOAD_PATH+fileName,HttpMethod.GET,httpEntity,byte[].class);
		Files.write(Paths.get("F:\\workspace\\Downloads\\"+fileName), response.getBody());
		
	}

}
