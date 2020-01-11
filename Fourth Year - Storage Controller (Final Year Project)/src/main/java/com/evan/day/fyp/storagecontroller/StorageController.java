package com.evan.day.fyp.storagecontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import io.minio.MinioClient;
import io.minio.errors.MinioException;


@RestController
public class StorageController {
    String[] args;

    @RequestMapping("/createbucket")
    public void createBucket() throws NoSuchAlgorithmException, IOException, InvalidKeyException, XmlPullParserException{
        try {
            MinioClient minioClient = new MinioClient("http://localhost:9001", "S3_USER", "S3_USER_PASSWORD");

            // Check if the bucket already exists.
            boolean isExist = minioClient.bucketExists("test-bucket");

            if(isExist) {
                System.out.println("Bucket already exists.");
            } else {

                minioClient.makeBucket("test-bucket");
            }

        } catch(MinioException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/platforminit")
    public void platformInit() throws NoSuchAlgorithmException, IOException, InvalidKeyException, XmlPullParserException{
        try {
            MinioClient minioClient = new MinioClient("http://localhost:9001", "S3_USER", "S3_USER_PASSWORD");

            boolean isExist = minioClient.bucketExists("platform-master-bucket");

            if (isExist) {
                //Upload Main Terraform File
                minioClient.putObject("platform-master-bucket","main.tf","/home/evan-day/IdeaProjects/storage-controller/src/main/resources/main.tf");
                //Upload Terraform Variables File
                minioClient.putObject("platform-master-bucket", "variables.tf", "/home/evan-day/IdeaProjects/storage-controller/src/main/resources/variables.tf");
                //Upload RBAC File
                minioClient.putObject("platform-master-bucket","rbac-config.yml","/home/evan-day/IdeaProjects/storage-controller/src/main/resources/rbac-config.yml");
            } else {
                //Make Bucket
                minioClient.makeBucket("platform-master-bucket");
                //Upload Main Terraform File
                minioClient.putObject("platform-master-bucket","main.tf","/home/evan-day/IdeaProjects/storage-controller/src/main/resources/main.tf");
                //Upload Terraform Variables File
                minioClient.putObject("platform-master-bucket", "variables.tf", "/home/evan-day/IdeaProjects/storage-controller/src/main/resources/variables.tf");
                //Upload RBAC File
                minioClient.putObject("platform-master-bucket","rbac-config.yml","/home/evan-day/IdeaProjects/storage-controller/src/main/resources/rbac-config.yml");
            }

        } catch (MinioException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping(path = "/fileUpload", method = RequestMethod.POST)
    public ResponseEntity<String> processFile(@RequestParam("file") MultipartFile file) throws NoSuchAlgorithmException, IOException, InvalidKeyException, XmlPullParserException{

        byte[] bytes = file.getBytes();

        try {
            MinioClient minioClient = new MinioClient("http://localhost:9001", "S3_USER", "S3_USER_PASSWORD");

            minioClient.putObject("platform-master-bucket","kubeconfig.yaml", new ByteArrayInputStream(bytes), bytes.length, "application/octet-stream");
        } catch (MinioException e) {
            e.printStackTrace();
        }

        return (new ResponseEntity<>("Successful", null, HttpStatus.OK));
    }

    @RequestMapping("/filedownload")
    public String downloadFile(@RequestParam String id) throws NoSuchAlgorithmException, IOException, InvalidKeyException, XmlPullParserException{
        String downloadUrl = "";

        try {
            MinioClient minioClient = new MinioClient("http://localhost:9001", "S3_USER", "S3_USER_PASSWORD");

            downloadUrl = minioClient.presignedGetObject("platform-master-bucket",id);

        } catch (MinioException e) {
            e.printStackTrace();
        }
        return downloadUrl;
    }
}
