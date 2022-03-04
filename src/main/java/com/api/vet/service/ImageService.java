package com.api.vet.service;

import com.api.vet.entity.Image;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rodrigo Caro
 */
public interface ImageService {
    public Image saveImage(MultipartFile archive) throws IOException;
}
