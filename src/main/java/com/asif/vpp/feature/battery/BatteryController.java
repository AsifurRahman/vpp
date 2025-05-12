package com.asif.vpp.feature.battery;

import com.asif.vpp.common.constant.Message;
import com.asif.vpp.generic.payload.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batteries")
public class BatteryController {

    private final BatteryService service;

    public BatteryController(BatteryServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/save-batteries")
    public ResponseEntity<MessageResponse> saveBatteries(@RequestParam("requestId") String requestId,
            @Valid @RequestBody List<BatteryRequestDto> batteries) {
        service.bulkCreate(batteries, requestId);
        return ResponseEntity.ok(new MessageResponse(Message.BATTERY_SAVING_STARTED));
    }

    @GetMapping("/filter")
    public ResponseEntity<BatteryFilterResponse> filterBatteries(
            @RequestParam int from,
            @RequestParam int to,
            @RequestParam(required = false) Double minCapacity,
            @RequestParam(required = false) Double maxCapacity
    ) {
        return ResponseEntity.ok(
                service.filterBatteriesByRangeAndCapacity(from, to, minCapacity, maxCapacity)
        );
    }
}
