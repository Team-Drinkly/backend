package com.drinkhere.infras3.aop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "presigned-url")
public class PresignedUrlPointCutProperties {
    private List<String> pointcuts;
}