package com.fulfilment.application.monolith.warehouses.domain.usecaseTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;

import java.time.LocalDateTime;

import com.fulfilment.application.monolith.warehouses.domain.usecases.ReplaceWarehouseUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReplaceWarehouseUseCaseTest {

    @Mock
    private WarehouseStore warehouseStore;

    private ReplaceWarehouseUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ReplaceWarehouseUseCase(warehouseStore);
    }

    @Test
    void shouldReplaceExistingWarehouse() {

        Warehouse oldWarehouse = new Warehouse();
        oldWarehouse.businessUnitCode = "MWH.001";
        oldWarehouse.location = "ZWOLLE-001";
        oldWarehouse.capacity = 40;
        oldWarehouse.stock = 20;

        Warehouse newWarehouse = new Warehouse();
        newWarehouse.businessUnitCode = "MWH.001";
        newWarehouse.location = "ZWOLLE-001";
        newWarehouse.capacity = 60;
        newWarehouse.stock = 0;

        when(warehouseStore.findByBusinessUnitCode("MWH.001"))
                .thenReturn(oldWarehouse);

        useCase.replace(newWarehouse);

        assertNotNull(oldWarehouse.archivedAt);

        assertEquals(
                "MWH.001",
                newWarehouse.businessUnitCode
        );

        assertNull(newWarehouse.archivedAt);

        verify(warehouseStore).findByBusinessUnitCode("MWH.001");

        verify(warehouseStore).update(oldWarehouse);

        verify(warehouseStore).create(newWarehouse);
    }

    @Test
    void shouldFailWhenWarehouseDoesNotExist() {

        Warehouse newWarehouse = new Warehouse();

        newWarehouse.businessUnitCode = "MWH.999";
        newWarehouse.location = "ZWOLLE-001";
        newWarehouse.capacity = 50;

        when(warehouseStore.findByBusinessUnitCode("MWH.999"))
                .thenReturn(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> useCase.replace(newWarehouse)
        );

        verify(warehouseStore, never()).update(any());
        verify(warehouseStore, never()).create(any());
    }

    @Test
    void shouldNotReplaceAlreadyArchivedWarehouse() {

        Warehouse oldWarehouse = new Warehouse();

        oldWarehouse.businessUnitCode = "MWH.001";
        oldWarehouse.location = "ZWOLLE-001";
        oldWarehouse.archivedAt = LocalDateTime.now();

        Warehouse newWarehouse = new Warehouse();

        newWarehouse.businessUnitCode = "MWH.001";
        newWarehouse.location = "ZWOLLE-001";

        when(warehouseStore.findByBusinessUnitCode("MWH.001"))
                .thenReturn(oldWarehouse);

        assertThrows(
                IllegalStateException.class,
                () -> useCase.replace(newWarehouse)
        );

        verify(warehouseStore, never()).update(any());
        verify(warehouseStore, never()).create(any());
    }

    @Test
    void shouldPreserveOldWarehouseHistory() {

        Warehouse oldWarehouse = new Warehouse();

        oldWarehouse.businessUnitCode = "MWH.001";
        oldWarehouse.location = "ZWOLLE-001";
        oldWarehouse.capacity = 40;
        oldWarehouse.stock = 25;

        Warehouse newWarehouse = new Warehouse();

        newWarehouse.businessUnitCode = "MWH.001";
        newWarehouse.location = "ZWOLLE-001";
        newWarehouse.capacity = 60;
        newWarehouse.stock = 0;

        when(warehouseStore.findByBusinessUnitCode("MWH.001"))
                .thenReturn(oldWarehouse);

        useCase.replace(newWarehouse);
        assertEquals("MWH.001", oldWarehouse.businessUnitCode);
        assertEquals("ZWOLLE-001", oldWarehouse.location);
        assertEquals(40, oldWarehouse.capacity);
        assertEquals(25, oldWarehouse.stock);
        assertNotNull(oldWarehouse.archivedAt);
        assertNull(newWarehouse.archivedAt);
    }

    @Test
    void shouldArchiveBeforeCreatingNewWarehouse() {

        Warehouse oldWarehouse = new Warehouse();
        oldWarehouse.businessUnitCode = "MWH.001";

        Warehouse newWarehouse = new Warehouse();
        newWarehouse.businessUnitCode = "MWH.001";

        when(warehouseStore.findByBusinessUnitCode("MWH.001"))
                .thenReturn(oldWarehouse);

        useCase.replace(newWarehouse);

        InOrder inOrder = inOrder(warehouseStore);

        inOrder.verify(warehouseStore)
                .findByBusinessUnitCode("MWH.001");

        inOrder.verify(warehouseStore)
                .update(oldWarehouse);

        inOrder.verify(warehouseStore)
                .create(newWarehouse);
    }
}