package com.drinkhere.apihealthcheck.dto.response;

import com.drinkhere.infras3.aop.Interface.TransformToPresignedUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@TransformToPresignedUrl
public class GetPresignedUrlResponse {
    private String filePath;
}