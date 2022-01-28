package com.alkemy.ong.service.images;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("AmazonS3")
public class AmazonS3Uploader implements ImageUploader {

  // --------------------------------------------------------------------------------------------
  // Autowireds
  // --------------------------------------------------------------------------------------------

  private AmazonS3 s3;

  // --------------------------------------------------------------------------------------------
  // Public
  // --------------------------------------------------------------------------------------------


  @Override
  public String upload(String path, String fileName, Optional<Map<String, String>> metadata,
      InputStream inputStream) {

    try {
      s3.putObject(path, fileName, inputStream, createMetadata(metadata));
      URL s3Url = s3.getUrl(path, fileName);
      return s3Url.getPath();
    } catch (AmazonServiceException e) {
      throw new IllegalStateException("Failed to store file to S3");
    }

  }

  // --------------------------------------------------------------------------------------------
  // Internal Methods
  // --------------------------------------------------------------------------------------------

  private ObjectMetadata createMetadata(Optional<Map<String, String>> optionalMetadata) {

    ObjectMetadata metadata = new ObjectMetadata();

    optionalMetadata.ifPresent(map -> {
      if (!map.isEmpty()) {
        map.forEach(metadata::addUserMetadata);
      }
    });

    return metadata;
  }

}
