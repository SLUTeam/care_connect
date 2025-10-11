package com.app.thirdparty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonS3ClientConfig {
    
    private static final Logger LOG = LoggerFactory.getLogger(AmazonS3ClientConfig.class);
	
    
	@Bean("s3Client")
	public AmazonS3 s3Client(@Value("${aws.user.accessKey}") String awsAccessKey, @Value("${aws.user.accessSecret}") String awsSecretKey,
			@Value("${aws.s3.bucket.in.general.region}") String region) {
	    AWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
	    AmazonS3 s3Client = AmazonS3ClientBuilder.standard().
                withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).
                withRegion(region).build();
	    
	    return s3Client;
    }
	
	 
	/*
	@Bean("protectedS3Client")
	public AmazonS3 protectedS3Client(@Value("${aws.user.accessKey}") String awsAccessKey, 
			@Value("${aws.user.accessSecret}") String awsSecretKey,
			@Value("${aws.s3.encryption.keystore.path}") Resource keyStore,
			@Value("${aws.s3.encryption.keystore.password}") String keyStorePassword, 
			@Value("${aws.s3.encryption.key.alias}") String keyAlias,
			@Value("${aws.s3.bucket.in.protected.region}") String region) {
	    //EncryptionMaterials encMaterials = new EncryptionMaterials(loadKeyPairFromJks(keyStore, keyAlias, keyStorePassword));
	    AWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

	    AmazonS3 protectedS3Client = AmazonS3EncryptionClientBuilder.standard().
            withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).
            withEncryptionMaterials(new StaticEncryptionMaterialsProvider(encMaterials)).
            withRegion(region).build();
	    
	    return protectedS3Client;
    }
    */
/*
    private KeyPair loadKeyPairFromJks(Resource keyStore, String keyAlias, String keyStorePassword) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(keyStore.getInputStream(), keyStorePassword.toCharArray());
            KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(
                keyAlias, new KeyStore.PasswordProtection(keyStorePassword.toCharArray()));
            PrivateKey privateKey = keyEntry.getPrivateKey();

            return new KeyPair(keyEntry.getCertificate().getPublicKey(), privateKey);

        } catch (Exception e) {
            LOG.error("Keystore issue...", e);
            throw new RuntimeException("Unable to load the RSA Keys from keystore for S3 Encryption", e);
        }
    }
    */
}
