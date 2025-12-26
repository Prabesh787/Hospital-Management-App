package com.HospitalManagementSystem.demo.dto.image;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UploadResultDto {

    private final String url;
    private final String publicId;
}
