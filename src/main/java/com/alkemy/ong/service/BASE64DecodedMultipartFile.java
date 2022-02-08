package com.alkemy.ong.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.http.entity.ContentType;
import org.springframework.web.multipart.MultipartFile;

public class BASE64DecodedMultipartFile implements MultipartFile {
    private final byte[] imgContent;
    private final UUID myId = UUID.randomUUID();
    public BASE64DecodedMultipartFile(byte[] imgContent) {
        this.imgContent = imgContent;
    }

    @Override
    public String getName() {
        // TODO - implementation depends on your requirements 
    	  return myId.toString();
    }

    @Override
    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
    	   return myId + ".png";
    }

    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return ContentType.IMAGE_PNG.getMimeType();
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException { 
        new FileOutputStream(dest).write(imgContent);
    }


}
