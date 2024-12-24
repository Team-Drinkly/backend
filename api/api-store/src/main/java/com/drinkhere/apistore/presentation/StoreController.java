package com.drinkhere.apistore.presentation;

import com.drinkhere.apistore.dto.GetStoreCoordinatesRequest;
import com.drinkhere.apistore.service.GetStoreCoordinatesUseCase;
import com.drinkhere.clientgeocoding.dto.CoordinatesResponse;
import com.drinkhere.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.drinkhere.clientgeocoding.response.StoreSuccessStatus.GET_COORDINATES_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {
    private final GetStoreCoordinatesUseCase getStoreCoordinatesUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<CoordinatesResponse>> getCoordinatesTest(
            @RequestBody GetStoreCoordinatesRequest getStoreCoordinatesRequest
    ) {
        return ApiResponse.success(
                GET_COORDINATES_SUCCESS,
                getStoreCoordinatesUseCase.getStoreGeocoding(getStoreCoordinatesRequest.address())
        );
    }
}
