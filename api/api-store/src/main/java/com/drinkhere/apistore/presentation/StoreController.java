package com.drinkhere.apistore.presentation;

import com.drinkhere.apistore.dto.GetStoreCoordinatesRequest;
import com.drinkhere.apistore.service.Impl.GetStoreCoordinatesUseCase;
import com.drinkhere.clientgeocoding.dto.Coordinates;
import com.drinkhere.common.response.ApplicationResponse;
import com.drinkhere.domainrds.store.dto.CreateStoreRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {
    private final GetStoreCoordinatesUseCase getStoreCoordinatesUseCase;

    // TODO: 업체 등록 API
    @PostMapping
    public ApplicationResponse<String> createStore(
            @RequestBody CreateStoreRequest createStoreRequest
    ) {

        return ApplicationResponse.created("업체 등록");
    }

    // TODO: 지도 뷰에서 좌표 및 store_id 반환
    // TODO: 매장 하단 슬라이드 조회
    // TODO: 매장 상세 정보 조회
    // TODO: 검색

    @PostMapping("/coordinates")
    public ApplicationResponse<Coordinates> getCoordinatesTest(
            @RequestBody GetStoreCoordinatesRequest getStoreCoordinatesRequest
    ) {
        return ApplicationResponse.ok(getStoreCoordinatesUseCase.convertAddressToCoordinates(getStoreCoordinatesRequest.address()));
    }
}
