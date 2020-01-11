package com.evan.day.fyp.infrastructurecontroller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
@RestController
public class InfrastructureController {

    @RequestMapping("/createpipeline")
    public void createPipeline() {
        //REST Template responsible for enabling communication to the other services
        RestTemplate restTemplate = new RestTemplate();

        //Begin setting URLs for each controller operation
        String storageInitUrl = new String("http://localhost:8081/platforminit");
        String terraformInitUrl = new String("http://localhost:8080/init");
        String terraformCreateUrl = new String("http://localhost:8080/create");
        String kubernetesCreateRbacUrl = new String("http://localhost:8082/rbac");
        String kubernetesInitialiseHelmUrl = new String("http://localhost:8082/helminit");
        String kubernetesArtifactoryCreateUrl = new String("http://localhost:8082/artifactorycreate");
        String kubernetesConcourseCreateUrl = new String("http://localhost:8082/concoursecreate");
        String kubernetesSonarqubeCreateUrl = new String("http://localhost:8082/sonarqubecreate");
        String kubernetesIngressCreateUrl = new String("http://localhost:8082/ingresscreate");
        //End setting URLs for each controller operation
        //Begin sending GET requests
        restTemplate.getForEntity(storageInitUrl, String.class);
        restTemplate.getForEntity(terraformInitUrl, String.class);
        restTemplate.getForEntity(terraformCreateUrl, String.class);
        restTemplate.getForEntity(kubernetesCreateRbacUrl, String.class);
        restTemplate.getForEntity(kubernetesInitialiseHelmUrl, String.class);
        restTemplate.getForEntity(kubernetesArtifactoryCreateUrl, String.class);
        restTemplate.getForEntity(kubernetesConcourseCreateUrl, String.class);
        restTemplate.getForEntity(kubernetesSonarqubeCreateUrl, String.class);
        restTemplate.getForEntity(kubernetesIngressCreateUrl, String.class);
        //End sending GET requests
    }
    @RequestMapping("/deletepipeline")
    public void deletePipeline() {
        //REST Template responsible for enabling communication to the other services
        RestTemplate restTemplate = new RestTemplate();

        //Begin setting URL for the delete operation
        String terraformDestroyUrl = new String("http://localhost:8080/destroy");
        //End setting URL for the delete operation
        //Begin sending the GET request
        restTemplate.getForEntity(terraformDestroyUrl, String.class);
        //End sending GET request
    }

}
