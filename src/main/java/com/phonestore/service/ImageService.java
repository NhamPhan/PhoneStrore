package com.phonestore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_hhmmss");

    public String store(Part part, String folders) throws IOException {
        logger.info("store image");
        if (part.getSize() == 0) {
            return null;
        }

        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        fileName = dateFormatter.format(new Date())+extension;
        part.write(folders+"/"+fileName);
        return "/images/products/"+fileName;
    }
}