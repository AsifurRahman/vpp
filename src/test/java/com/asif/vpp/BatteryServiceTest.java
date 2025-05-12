package com.asif.vpp;

import com.asif.backend.common.exception.RmsServerException;
import com.asif.backend.config.io.BatteryWebSocketHandler;
import com.asif.backend.feature.battery.*;
import com.asif.backend.feature.batterysave.BSStatus;
import com.asif.backend.feature.batterysave.BatterySaveService;
import com.asif.backend.feature.batterysave.BatterySaveStatus;
import com.asif.vpp.feature.battery.BatteryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

class BatteryServiceTest {

    @Mock
    private BatteryRepository batteryRepository;

    @Mock
    private BatteryWebSocketHandler wsHandler;

    @Mock
    private BatterySaveService batterySaveService;

    @InjectMocks
    private BatteryServiceImpl batteryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(
                new TestingAuthenticationToken("user123", null));
        SecurityContextHolder.setContext(context);
    }

    @Test
    void testBulkCreate_shouldSaveBatteriesAsync() {
        BatteryRequestDto dto = new BatteryRequestDto("Battery A", 1000, 500.0);
        BatterySaveStatus status = new BatterySaveStatus();
        status.setRequestId("req-1");

        when(batterySaveService.saveBatteryStatus(eq(BSStatus.STARTED), anyString(), eq("req-1")))
                .thenReturn(status);

        doNothing().when(wsHandler).sendStatusToSession(any());

        batteryService.bulkCreate(List.of(dto), "req-1");

        verify(batterySaveService, times(1)).saveBatteryStatus(BSStatus.STARTED, "user123", "req-1");
        verify(wsHandler, times(1)).sendStatusToSession(status);
    }

    @Test
    void testFilterBatteriesByRangeAndCapacity_shouldReturnValidResponse() {
        Battery b1 = new Battery("A", 1200, 200.0);
        Battery b2 = new Battery( "B", 1300, 300.0);

        when(batteryRepository.findByPostcodeBetween(1000, 1500)).thenReturn(List.of(b1, b2));

        BatteryFilterResponse res = batteryService.filterBatteriesByRangeAndCapacity(1000, 1500, 100.0, 300.0);

        assertEquals(2, res.getNames().size());
        assertEquals(500.0, res.getTotalWattCapacity(), 0.001);
        assertEquals(250.0, res.getAverageWattCapacity(), 0.001);
        assertTrue(res.getNames().contains("A"));
    }

    @Test
    void testFilterBatteriesByRangeAndCapacity_shouldThrowExceptionIfNoBatteryFound() {
        when(batteryRepository.findByPostcodeBetween(1000, 2000)).thenReturn(List.of());

        assertThrows(RmsServerException.class,
                () -> batteryService.filterBatteriesByRangeAndCapacity(1000, 2000, 100.0, 200.0));
    }
}
