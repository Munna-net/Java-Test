package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class WarehouseResourceImplTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private WarehouseResourceImpl warehouseResource;

    @Test
    void testListAllWarehousesUnits() {
        Warehouse warehouse1 = new Warehouse();
        warehouse1.businessUnitCode = "MWH.001";
        warehouse1.location = "ZWOLLE-001";
        warehouse1.capacity = 40;
        warehouse1.stock = 10;

        Warehouse warehouse2 = new Warehouse();
        warehouse2.businessUnitCode = "MWH.012";
        warehouse2.location = "AMSTERDAM-001";
        warehouse2.capacity = 50;
        warehouse2.stock = 20;

        when(warehouseRepository.getAll())
                .thenReturn(List.of(warehouse1, warehouse2));
        List<Warehouse> result =
                warehouseResource.listAllWarehousesUnits();
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("MWH.001",
                result.get(0).getBusinessUnitCode());

        assertEquals("ZWOLLE-001",
                result.get(0).getLocation());

        assertEquals(40,
                result.get(0).getCapacity());

        assertEquals(10,
                result.get(0).getStock());

        verify(warehouseRepository, times(1)).getAll();
    }

    @Test
    void testCreateWarehouseNotImplemented() {
        Warehouse data =
                new Warehouse();
        assertThrows(
                UnsupportedOperationException.class,
                () -> warehouseResource.createANewWarehouseUnit(data)
        );

        verifyNoInteractions(warehouseRepository);
    }

    @Test
    void testGetWarehouseNotImplemented() {
        assertThrows(
                UnsupportedOperationException.class,
                () -> warehouseResource.getAWarehouseUnitByID("MWH.001")
        );

        verifyNoInteractions(warehouseRepository);
    }

    @Test
    void testArchiveWarehouseNotImplemented() {
        assertThrows(
                UnsupportedOperationException.class,
                () -> warehouseResource.archiveAWarehouseUnitByID("MWH.001")
        );

        verifyNoInteractions(warehouseRepository);
    }

    @Test
    void testReplaceWarehouseNotImplemented() {

        Warehouse data =
                new Warehouse();

        assertThrows(
                UnsupportedOperationException.class,
                () -> warehouseResource.replaceTheCurrentActiveWarehouse(
                        "MWH.001",
                        data
                )
        );

        verifyNoInteractions(warehouseRepository);
    }
}