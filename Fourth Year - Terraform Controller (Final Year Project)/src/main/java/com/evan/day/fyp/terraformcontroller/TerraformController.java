package com.evan.day.fyp.terraformcontroller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TerraformController {
    String[] args;

    @RequestMapping("/init")
    public void terraformInit() throws IOException {
        // Get File Download URL
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add( new StringHttpMessageConverter());
    
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
    
        HttpEntity<String> entity = new HttpEntity<String>(headers);
    
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/filedownload?id=main.tf", HttpMethod.GET, entity, String.class, "1");

        String mainTerraformUrl = response.getBody();

        // Get Main Terraform File
        InputStream in = new URL(mainTerraformUrl).openStream();
        Files.copy(in, Paths.get("main.tf"), StandardCopyOption.REPLACE_EXISTING);

        // Get Variables Terraform File

        ResponseEntity<String> secondResponse = restTemplate.exchange("http://localhost:8081/filedownload?id=variables.tf", HttpMethod.GET, entity, String.class, "1");

        String variablesTerraformUrl = secondResponse.getBody();

        InputStream inTwo = new URL(variablesTerraformUrl).openStream();
        Files.copy(inTwo, Paths.get("variables.tf"), StandardCopyOption.REPLACE_EXISTING);
        
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//terraform-controller//src//main//resources//terraform", "init").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/create")
    public void terraformCreate() {
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//terraform-controller//src//main//resources//terraform", "apply", "-auto-approve").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        storageUpload();
    }

    @RequestMapping("/destroy")
    public void terraformDestroy() {
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//terraform-controller//src//main//resources//terraform", "destroy", "-auto-approve").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @RequestMapping("/storage")
    public void storageUpload() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", getTestFile());


        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String serverUrl = "http://localhost:8081/fileUpload";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
        System.out.println("Response code: " + response.getStatusCode());
    }

    public static Resource getTestFile() {
        File kubeFile = new File("//home//evan-day//IdeaProjects//terraform-controller//kubeconfig.yaml");

        return new FileSystemResource(kubeFile);
    }
}