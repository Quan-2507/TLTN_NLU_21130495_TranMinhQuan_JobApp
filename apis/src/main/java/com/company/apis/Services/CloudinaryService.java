package com.company.apis.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @SuppressWarnings("rawtypes")
    public Map uploadImage(MultipartFile file, String folderName) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "Images", folderName));
    }
    @SuppressWarnings("rawtypes")
    public Map uploadFileImage(File file, String folderName) throws IOException {
        return cloudinary.uploader().upload(file.isFile(),
                ObjectUtils.asMap(
                        "Images", folderName));
    }

    @SuppressWarnings("rawtypes")
    public Map uploadCV(MultipartFile file, String folderName) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "Images", folderName));
    }
    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}