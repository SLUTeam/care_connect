package com.app.thirdparty;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Service("awsProtectedS3Helper")
public class AWSProtectedS3Helper {

    private static Logger LOG = LoggerFactory.getLogger(AWSProtectedS3Helper.class);

    @Value("${aws.s3.bucket.in.protected}")
    private String bucket;

    private AmazonS3 protectedS3Client;

    @Autowired
    public AWSProtectedS3Helper(AmazonS3 protectedS3Client) {
        this.protectedS3Client = protectedS3Client;
    }

    public InputStream get(String location) {
        GetObjectRequest getRequest = new GetObjectRequest(bucket, location);
        S3Object s3Object = protectedS3Client.getObject(getRequest);
        return s3Object.getObjectContent();
    }

    public Map<String, Object> getMetadata(String location) {
        GetObjectMetadataRequest getRequest = new GetObjectMetadataRequest(bucket, location);
        ObjectMetadata metadata = protectedS3Client.getObjectMetadata(getRequest);
        Map<String, Object> rawMetadata = metadata.getRawMetadata();
        LOG.debug("rawMetadata: {}", rawMetadata);
        return rawMetadata;
    }
    
    public void put(File file, String location) {
        PutObjectRequest putRequest = new PutObjectRequest(bucket, location, file);
        putRequest.setMetadata(updateMetadata(new ObjectMetadata()));
        protectedS3Client.putObject(putRequest);
    }

    /* Needs to be replaced, same method as below */
    public void put(InputStream stream, String location, String mimeType) {
        ObjectMetadata objMetadata = new ObjectMetadata();
        objMetadata.setContentType(mimeType);
        updateMetadata(objMetadata);

        PutObjectRequest putRequest = new PutObjectRequest(bucket, location, stream, objMetadata);
        protectedS3Client.putObject(putRequest);
    }

    public void put(byte[] bytes, String location, String mimeType) {
        ObjectMetadata objMetadata = new ObjectMetadata();
        objMetadata.setContentType(mimeType);
        objMetadata.setContentLength(bytes.length);
        updateMetadata(objMetadata);

        PutObjectRequest putRequest = new PutObjectRequest(bucket, location, new ByteArrayInputStream(bytes),
                objMetadata);
        protectedS3Client.putObject(putRequest);
        LOG.debug("Uploaded file: {}", location);
    }

    private ObjectMetadata updateMetadata(ObjectMetadata objMetadata) {
        objMetadata.setCacheControl("public, max-age=31536000, must-revalidate");
        return objMetadata;
    }

    public void delete(String location) {
        DeleteObjectRequest delRequest = new DeleteObjectRequest(bucket, location);
        protectedS3Client.deleteObject(delRequest);
        LOG.debug("Deleted file: {}", location);
    }

    public void delete(List<String> locations) {
        DeleteObjectsRequest delRequest = new DeleteObjectsRequest(bucket);
        List<KeyVersion> keys = new ArrayList<>();
        for (String location : locations) {
            keys.add(new KeyVersion(location));
        }
        delRequest.setKeys(keys);
        protectedS3Client.deleteObjects(delRequest);
    }

    public boolean isFilePresent(String location) {
        boolean isPresent = false;
        try {
            protectedS3Client.getObjectMetadata(bucket, location);
            isPresent = true;
        } catch (AmazonS3Exception ase) {
            LOG.warn("S3 file not found " + location);
        } catch (Exception e) {
            LOG.error("Failed to check if S3 file is present " + location, e);
        }

        return isPresent;
    }

    public String getBucket() {
        return bucket;
    }

}
