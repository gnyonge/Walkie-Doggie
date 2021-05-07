package com.ssafy.pet.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Component
public class S3Util {
	
	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey; // 액세스 키

	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey; //보안엑세스키
	
	@Value("${cloud.aws.region.static}")
	private String region;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	private AmazonS3 conn;
	
	@PostConstruct
	public AmazonS3 setS3Client() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		//키를 가져와서 자격증명ㅇ르 하죠
		
		conn = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(region)
				.build();
		
		return conn;
	}
	//AmazonS3ClientBuilder 통해서 s3client를 가져와야하는데, 자격증명을 해줘야 s3client를 가져올수있어
	
	

}
