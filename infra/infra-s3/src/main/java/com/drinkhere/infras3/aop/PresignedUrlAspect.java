package com.drinkhere.infras3.aop;

import com.drinkhere.common.response.ApiResponse;
import com.drinkhere.infras3.aop.Interface.TransformToPresignedUrl;
import com.drinkhere.infras3.application.PresignedUrlService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
@Aspect
@Component
@RequiredArgsConstructor
public class PresignedUrlAspect {

    private final PresignedUrlService presignedUrlService;


    /**
     * RestController이면서 GetMapping일때 처리를 수행
     */
    @Around("execution(public * com.drinkhere..*.*(..)) " +
            "&& @target(org.springframework.web.bind.annotation.RestController)" +
            "&& @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object handlePresignedUrl(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        // ResponseEntity 객체가 반환되면
        if (result instanceof ResponseEntity<?> responseEntity) {
            Object body = responseEntity.getBody();
            if (body instanceof ApiResponse<?> apiResponse) {
                Object data = apiResponse.getData();
                // 데이터가 존재하면 Presigned URL 처리
                if (data != null) {
                    processIfPresignedUrlApplicable(data);
                }
            }
        }

        return result;
    }

    /**
     * Presigned URL을 생성하여 데이터 처리
     */
    private void processIfPresignedUrlApplicable(Object data) {
        // 단일 객체일 경우 처리
        if (data.getClass().isAnnotationPresent(TransformToPresignedUrl.class)) {
            processPresignedUrl(data);
        }
        // 컬렉션 객체 처리
        else if (data instanceof Collection<?> collection) {
            collection.stream()
                    .filter(item -> item.getClass().isAnnotationPresent(TransformToPresignedUrl.class))
                    .forEach(this::processPresignedUrl);
        }
        // 맵 객체 처리
        else if (data instanceof Map<?, ?> map) {
            map.values().stream()
                    .filter(value -> value.getClass().isAnnotationPresent(TransformToPresignedUrl.class))
                    .forEach(this::processPresignedUrl);
        }
    }

    /**
     * Presigned URL을 생성하고 해당 값을 업데이트
     */
    private void processPresignedUrl(Object data) {
        for (Field field : data.getClass().getDeclaredFields()) {
            // filePath 필드만 찾아서 처리
            if (field.getType().equals(String.class) && (field.getName().contains("filePath") || field.getName().contains("Paths"))) {
                field.setAccessible(true);
                try {
                    // 단일 filePath 값 처리
                    String filePath = (String) field.get(data);
                    if (filePath != null) {
                        // Presigned URL 생성
                        String presignedUrl = presignedUrlService.getPresignedUrlForGet(filePath);
                        // 필드 값 업데이트
                        field.set(data, presignedUrl);
                    }
                } catch (IllegalAccessException e) {
                    // 필드 접근 시 예외 처리
                    throw new IllegalStateException("Failed to update field value", e);
                }
            }
            // filePath가 List<String>인 경우 처리
            else if (field.getType().equals(List.class) && field.getName().contains("filePath")) {
                field.setAccessible(true);
                try {
                    // List<String> 타입의 filePath를 처리
                    List<String> filePaths = (List<String>) field.get(data);
                    if (filePaths != null) {
                        // 각 fileName에 대해 Presigned URL 처리
                        for (int i = 0; i < filePaths.size(); i++) {
                            String filePath = filePaths.get(i);
                            if (filePath != null) {
                                // Presigned URL 생성
                                String presignedUrl = presignedUrlService.getPresignedUrlForGet(filePath);
                                // Presigned URL로 업데이트
                                filePaths.set(i, presignedUrl);
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    // 필드 접근 시 예외 처리
                    throw new IllegalStateException("Failed to update field value", e);
                }
            }
        }
    }
}
