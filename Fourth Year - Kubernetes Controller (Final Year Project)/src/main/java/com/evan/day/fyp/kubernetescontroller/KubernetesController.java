package com.evan.day.fyp.kubernetescontroller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class KubernetesController {
    String[] args;

    @RequestMapping("/rbac")
    public void rbacPrerequisites() throws IOException {
        // Download kubeconfig.yaml
        // Get File Download URL
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add( new StringHttpMessageConverter());
    
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
    
        HttpEntity<String> entity = new HttpEntity<String>(headers);
    
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/filedownload?id=kubeconfig.yaml", HttpMethod.GET, entity, String.class, "1");

        String mainTerraformUrl = response.getBody();

        // Get Kube Config File
        InputStream in = new URL(mainTerraformUrl).openStream();
        Files.copy(in, Paths.get("kubeconfig.yaml"), StandardCopyOption.REPLACE_EXISTING);

        // Get RBAC Config File
        ResponseEntity<String> secondResponse = restTemplate.exchange("http://localhost:8081/filedownload?id=rbac-config.yml", HttpMethod.GET, entity, String.class, "1");

        String variablesTerraformUrl = secondResponse.getBody();

        InputStream inTwo = new URL(variablesTerraformUrl).openStream();
        Files.copy(inTwo, Paths.get("rbac-config.yml"), StandardCopyOption.REPLACE_EXISTING);
        //Begin Health Check On Kubernetes Cluster
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//kubectl", "rollout", "status", "deployment/cilium-operator", "-n", "kube-system", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //End Health Check On Kubernetes Cluster
        //Begin Creating RBAC Configuration
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//kubectl","create", "-f",  "rbac-config.yml", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //End Creating RBAC Configuration
    }
    @RequestMapping("/helminit")
    public void helmInitialize() {
        //Begin Initialising Helm
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//helm", "init", "--service-account", "tiller", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //End Initialising Helm
        //Begin Wait for Tiller to be ready
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//kubectl", "rollout", "status", "deployment/tiller-deploy", "-n", "kube-system", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //End Wait for Tiller to be ready
    }
    @RequestMapping("/artifactorycreate")
    public void createArtifactoryInstance() {
        //Start Deploying Artifactory
        //Start Adding Helm Repo for Artifactory
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//helm", "repo", "add", "jfrog", "https://charts.jfrog.io").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //End Adding Helm Repo for Artifactory
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//helm", "install", "--name", "artifactory", "--set", "artifactory.image.repository=docker.bintray.io/jfrog/artifactory-oss", "--set", "nginx.service.type=NodePort", "jfrog/artifactory", "--version", "7.13.2", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //Start Artifactory Health Check
        //Postgresql Deployment
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//kubectl", "rollout", "status", "deployment/artifactory-postgresql", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //Primary Deployment
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//kubectl", "rollout", "status", "deployment/artifactory-artifactory-nginx", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //End Artifactory Health Check
        //End Deploying Artifactory
    }
    @RequestMapping("/ingresscreate")
    public void createNginxIngress() {
        //Start Ingress Prerequisites
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//kubectl", "create", "-f", "https://raw.githubusercontent.com/kubernetes/ingress-nginx/master/deploy/mandatory.yaml",  "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //End Ingress Prerequisites
        //Start Ingress Cloud Provider
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//kubectl", "create", "-f", "https://raw.githubusercontent.com/kubernetes/ingress-nginx/master/deploy/provider/cloud-generic.yaml",  "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //End Ingress Cloud Provider
        //Start Ingress Object
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//kubectl", "create", "-f", "/home/evan-day/IdeaProjects/kubernetes-controller/src/main/resources/ingress.yml",  "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //End Ingress Object
    }
    @RequestMapping("/concoursecreate")
    public void createConcourseInstance() {
        //Start Deploying Concourse
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//helm", "install", "--name", "concourse", "--set", "web.service.type=NodePort", "--set", "concourse.web.externalUrl=concourse.evanday.io", "stable/concourse", "--version", "5.0.1", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //Start Concourse Health Checking
        //Postgres Check
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//kubectl", "rollout", "status", "deployment/concourse-postgresql", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //Primary Check
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//kubectl", "rollout", "status", "deployment/concourse-web", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //End Concourse Health Checking
        //End Deploying Concourse
    }
    @RequestMapping("/sonarqubecreate")
    public void createSonarqubeInstance() {
        //Start Deploying Sonarqube Instance
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//helm", "install", "--name", "sonarqube", "--set", "service.type=NodePort", "stable/sonarqube", "--version", "0.12.0", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //Start Sonarqube Health Checking
        //Postgres Check
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//kubectl", "rollout", "status", "deployment/sonarqube-postgresql", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //Primary Check
        try {
            Process process = new ProcessBuilder("//home//evan-day//IdeaProjects//kubernetes-controller//src//main//resources//kubectl", "rollout", "status", "deployment/sonarqube-sonarqube", "--kubeconfig=kubeconfig.yaml").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //End Sonarqube Health Checking
        //End Deploying Sonarqube Instance
    }
}
