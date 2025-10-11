package com.app.thirdparty;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

@Service("s3Services")
public class AWSS3Helper {
    private static Logger logger = LoggerFactory.getLogger(AWSS3Helper.class);

    @Value("${aws.s3.bucket.in.general}")
    private String bucket;

    private AmazonS3 s3Client;

    @Autowired
    public AWSS3Helper(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public InputStream get(String location) {

        GetObjectRequest getRequest = new GetObjectRequest(bucket, location);
        S3Object s3Object = s3Client.getObject(getRequest);
        return s3Object.getObjectContent();
    }

    public void put(File file, String location) {
        PutObjectRequest putRequest = new PutObjectRequest(bucket, location, file);
        PutObjectResult result = s3Client.putObject(putRequest);
        logger.info("Location of stored image: " + location);
        logger.info("Result ETag: " + result.getETag());
    }
    
    public void put(InputStream stream, String location, String mimeType, int contentLength) {
        ObjectMetadata objMetadata = new ObjectMetadata();
        objMetadata.setContentType(mimeType);
        objMetadata.setContentLength(contentLength);
        setMaxAgeMetadata(objMetadata);

        location = fixKey(location);
        PutObjectRequest putRequest = new PutObjectRequest(bucket, location, stream, objMetadata);

        PutObjectResult result = s3Client.putObject(putRequest);
        logger.info("Stored image: " + location + " ETag: " + result.getETag());
    }

    public void put(InputStream stream, String location, String mimeType) {
        ObjectMetadata objMetadata = new ObjectMetadata();
        objMetadata.setContentType(mimeType);
        setMaxAgeMetadata(objMetadata);

        location = fixKey(location);
        PutObjectRequest putRequest = new PutObjectRequest(bucket, location, stream, objMetadata);
        PutObjectResult result = s3Client.putObject(putRequest);
        logger.info("Location of stored image: " + location);
        logger.info("Result ETag: " + result.getETag());
    }

    public void put(byte[] bytes, String location, String mimeType) {
        ObjectMetadata objMetadata = new ObjectMetadata();
        objMetadata.setContentType(mimeType);
        objMetadata.setContentLength(bytes.length);
        updateMetadata(objMetadata);

        PutObjectRequest putRequest = new PutObjectRequest(bucket, location, new ByteArrayInputStream(bytes),
                objMetadata);
        s3Client.putObject(putRequest);
        logger.debug("Uploaded file: {}", location);
    }
    
    public CopyObjectResult copy(String srcLocation, String destLocation) {

        CopyObjectRequest req = new CopyObjectRequest(bucket, fixKey(srcLocation), bucket, fixKey(destLocation));
        CopyObjectResult res = s3Client.copyObject(req);
        return res;
    }

    private void setMaxAgeMetadata(ObjectMetadata objMetadata) {
        objMetadata.setCacheControl("public, max-age=31536000, must-revalidate");
    }

    public void delete(String location) {
        DeleteObjectRequest delRequest = new DeleteObjectRequest(bucket, location);
        s3Client.deleteObject(delRequest);
    }

    public void delete(List<String> locations) {
        DeleteObjectsRequest delRequest = new DeleteObjectsRequest(bucket);
        List<KeyVersion> keys = new ArrayList<KeyVersion>();
        for (String location : locations) {
            keys.add(new KeyVersion(location));
        }
        delRequest.setKeys(keys);
        s3Client.deleteObjects(delRequest);
    }

    private String fixKey(String targetPath) {
        return targetPath.charAt(0) == '/' ? targetPath.substring(1) : targetPath;
    }
    
    private ObjectMetadata updateMetadata(ObjectMetadata objMetadata) {
        objMetadata.setCacheControl("public, max-age=31536000, must-revalidate");
        return objMetadata;
    }
    
    public boolean isFilePresent(String location) {
        boolean isPresent = false;
        try {
            s3Client.getObjectMetadata(bucket, location);
            isPresent = true;
        } catch (AmazonS3Exception ase) {
            logger.warn("S3 file not found " + location);
        } catch (Exception e) {
            logger.error("Failed to check if S3 file is present " + location, e);
        }

        return isPresent;
    }

    public String getBucket() {
        return bucket;
    }

    
    public void uploadFileToS3Bucket(MultipartFile multipartFile, String directoryPath, String fileName) throws Exception {

 

        try {
            //creating the file in the server (temporarily)
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

            PutObjectRequest putObjectRequest = new PutObjectRequest(this.bucket, directoryPath + "/" + fileName, file);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            putObjectRequest.setMetadata(metadata);

 

            s3Client.putObject(putObjectRequest);
            //removing the file created in the server
            file.delete();
        } catch (IOException | SdkClientException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
            throw new Exception("Error occurred while uploading file to s3 bucket",ex);
        }
    }
    
    
    public void uploadFileToS3Bucket(byte[] bytes, String directoryPath, String fileName) throws Exception {
        try {
            ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentLength(bytes.length);
            s3Client.putObject(new PutObjectRequest(this.bucket, directoryPath + "/" + fileName, new ByteArrayInputStream(bytes),  metaData));
        } catch (SdkClientException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
            throw new Exception("Error occurred while uploading file to s3 bucket",ex);
        }
    }
    

    public byte[] getFileFromS3Bucket(String directoryPath, String fileName) throws Exception {
        S3Object s3Object = null;
        try {
            GetObjectRequest getRequest = new GetObjectRequest(bucket, directoryPath + "/" + fileName);
            s3Object = s3Client.getObject(getRequest);
            return s3Object.getObjectContent().readAllBytes();
        } catch (IOException | SdkClientException e) {
            logger.error("error [" + e.getMessage() + "] occurred while getting [" + fileName + "] ");
            throw new Exception("Error occurred while retrieving file from s3 bucket",e);
        } finally {
            // To ensure that the network connection doesn't remain open, close any open input streams.
            if (s3Object != null) {
                s3Object.close();
            }
        }

 

    }
    
}
