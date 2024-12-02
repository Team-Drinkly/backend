package com.drinkhere.infras3.dto.request;

import jakarta.validation.constraints.NotNull;

public record GetPresignedUrlRequest(
        @NotNull(message = "prefix는 null일 수 없습니다.") String prefix,
        @NotNull(message = "fileName는 null일 수 없습니다.") String fileName
) {
}
