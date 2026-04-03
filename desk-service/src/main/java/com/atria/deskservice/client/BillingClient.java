package com.atria.deskservice.client;

import com.atria.deskservice.dto.BillingResponse;
import com.atria.deskservice.dto.CreateFolioRequest;
import com.atria.deskservice.dto.FolioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "atria-billing",
        url = "${billing.service.url}" // configurable
)
public interface BillingClient {

    @PostMapping("/api/v1/folios")
    BillingResponse<FolioResponse> createFolio(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestBody CreateFolioRequest request
    );
}