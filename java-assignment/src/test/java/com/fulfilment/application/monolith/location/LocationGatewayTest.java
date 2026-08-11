package com.fulfilment.application.monolith.location;

import static org.junit.jupiter.api.Assertions.*;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationGatewayTest {

    private LocationGateway locationGateway;

    @BeforeEach
    void setUp() {
        locationGateway = new LocationGateway();
    }

    @Test
    void testWhenResolveExistingLocationShouldReturnLocation() {
        Location location =
                locationGateway.resolveByIdentifier("ZWOLLE-001");
        assertNotNull(location);
        assertEquals("ZWOLLE-001", location.identification);
    }

    @Test
    void testWhenResolveNonExistingLocationShouldReturnNull() {
        Location location =
                locationGateway.resolveByIdentifier("UNKNOWN-001");
        assertNull(location);
    }

    @Test
    void testWhenResolveAmsterdamLocationShouldReturnLocation() {
        Location location =
                locationGateway.resolveByIdentifier("AMSTERDAM-001");
        assertNotNull(location);
        assertEquals("AMSTERDAM-001", location.identification);
    }

    @Test
    void testWhenResolveTilburgLocationShouldReturnLocation() {
        Location location =
                locationGateway.resolveByIdentifier("TILBURG-001");
        assertNotNull(location);
        assertEquals("TILBURG-001", location.identification);
    }
}