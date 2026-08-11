package com.fulfilment.application.monolith.warehouses.domain.usecaseTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;

import java.time.LocalDateTime;

import com.fulfilment.application.monolith.warehouses.domain.usecases.ArchiveWarehouseUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArchiveWarehouseUseCaseTest {

    @Mock
    private WarehouseStore warehouseStore;

    private ArchiveWarehouseUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ArchiveWarehouseUseCase(warehouseStore);
    }

    @Test
    void shouldArchiveWarehouse() {

        Warehouse warehouse = new Warehouse();
        warehouse.businessUnitCode = "MWH.001";
        warehouse.location = "ZWOLLE-001";
        warehouse.capacity = 40;
        warehouse.stock = 20;

        useCase.archive(warehouse);

        assertNotNull(warehouse.archivedAt);

        verify(warehouseStore, times(1)).update(warehouse);
    }

    @Test
    void shouldPreserveWarehouseInformationWhenArchived() {

        Warehouse warehouse = new Warehouse();

        warehouse.businessUnitCode = "MWH.001";
        warehouse.location = "ZWOLLE-001";
        warehouse.capacity = 40;
        warehouse.stock = 20;

        useCase.archive(warehouse);

        assertEquals("MWH.001", warehouse.businessUnitCode);
        assertEquals("ZWOLLE-001", warehouse.location);
        assertEquals(40, warehouse.capacity);
        assertEquals(20, warehouse.stock);

        assertNotNull(warehouse.archivedAt);

        verify(warehouseStore).update(warehouse);
    }

    @Test
    void shouldNotArchiveNullWarehouse() {

        assertThrows(
                IllegalArgumentException.class,
                () -> useCase.archive(null)
        );

        verify(warehouseStore, never()).update(any());
    }

    @Test
    void shouldNotArchiveAlreadyArchivedWarehouse() {

        Warehouse warehouse = new Warehouse();

        warehouse.businessUnitCode = "MWH.001";
        warehouse.archivedAt = LocalDateTime.now().minusDays(1);

        assertThrows(
                IllegalStateException.class,
                () -> useCase.archive(warehouse)
        );

        verify(warehouseStore, never()).update(any());
    }
}