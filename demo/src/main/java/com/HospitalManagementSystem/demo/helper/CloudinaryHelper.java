package com.HospitalManagementSystem.demo.helper;

import com.HospitalManagementSystem.demo.dto.image.UploadResultDto;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryHelper {

    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;

    public UploadResultDto upload(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of(
                            "folder", "hospital/patients",
                            "resource_type", "image"
                    )
            );
            String url = (String) uploadResult.get("secure_url");
            String publicId = (String) uploadResult.get("public_id");

            return new UploadResultDto(url, publicId);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload patient image", e);
        }
    }

    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, Map.of());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image", e);
        }
    }

}


