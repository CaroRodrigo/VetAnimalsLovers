package com.api.vet.service.imp;

import com.api.vet.entity.Image;
import com.api.vet.repository.ImageRepository;
import com.api.vet.service.ImageService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rodrigo Caro
 */
@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository imageRepository;
    
    @Override
    public Image saveImage(MultipartFile archive) throws IOException {
        if (archive != null) {

            try {
                Image image = new Image();
                image.setName(archive.getName());
                image.setMime(archive.getContentType());
                image.setContents(archive.getBytes());

                imageRepository.save(image);
                return image;
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
}
