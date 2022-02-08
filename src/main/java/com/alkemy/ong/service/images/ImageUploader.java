package com.alkemy.ong.service.images;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface ImageUploader {

  public String upload(String fileName, Optional<Map<String, String>> metadata,
      InputStream inputStream);
}
