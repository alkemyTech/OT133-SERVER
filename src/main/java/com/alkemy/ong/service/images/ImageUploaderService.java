package com.alkemy.ong.service.images;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploaderService {

  @Autowired
  @Qualifier("AmazonS3")
  private ImageUploader uploader;

  // --------------------------------------------------------------------------------------------
  // Public
  // --------------------------------------------------------------------------------------------

  public String uploadImage(String path, MultipartFile file) {

    // Verifico la integridad del archivo.
    verifyFile(file);

    // Obtengo la metadata del archivo.
    Map<String, String> metadata = obtainMetadata(file);

    // Subo al service
    try {
      return uploader.upload(path, file.getOriginalFilename(), Optional.of(metadata),
          file.getInputStream());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

  }

  // --------------------------------------------------------------------------------------------
  // Internal methods
  // --------------------------------------------------------------------------------------------

  /**
   * Verifies that a file is an image and is not empty.
   * 
   * @param file to be verified as an image
   */
  private void verifyFile(MultipartFile file) {
    // 1. Checkeo de NO VACÍO
    isEmptyFile(file);
    // 2. Checkeo de imagen
    isImage(file);
  }

  private void isEmptyFile(MultipartFile file) {
    if (file.isEmpty()) {

      throw new IllegalStateException(
          String.format("Cannot upload empty file [%d]", file.getSize()));
    }
  }

  private void isImage(MultipartFile file) {
    if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType(),
        ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
      throw new IllegalStateException(
          String.format("File must be an image [%s]", file.getContentType()));
    }
  }

  /**
   * Retrieves optional metadata information of a file.
   * 
   * @param file to be read the metadata
   * @return a map of metadata
   */
  private Map<String, String> obtainMetadata(MultipartFile file) {
    Map<String, String> metadata = new HashMap<>();
    metadata.put("Content-Type", file.getContentType());
    metadata.put("Content-Length", String.valueOf(file.getSize()));
    return metadata;
  }

}
