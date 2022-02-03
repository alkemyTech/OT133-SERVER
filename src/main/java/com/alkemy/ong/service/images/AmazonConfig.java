package com.alkemy.ong.service.images;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

  @Value("${cloud.aws.credentials.endpointUrl}")
  private String endpointUrl;
  @Value("${cloud.aws.credentials.accessKey}")
  private String accessKey;
  @Value("${cloud.aws.credentials.secretKey}")
  private String secretKey;
  @Value("${cloud.aws.credentials.bucketName}")
  private String bucketName;

  @Bean
  public AmazonS3 s3(){
      AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
      return AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).
      withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build(); 
  }
} 
