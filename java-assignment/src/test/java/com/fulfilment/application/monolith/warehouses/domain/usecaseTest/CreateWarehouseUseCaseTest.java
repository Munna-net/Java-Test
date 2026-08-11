package com.fulfilment.application.monolith.warehouses.domain.usecaseTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;

import com.fulfilment.application.monolith.warehouses.domain.usecases.CreateWarehouseUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateWarehouseUseCaseTest {

    @Mock
    private WarehouseStore warehouseStore;

    private CreateWarehouseUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateWarehouseUseCase(warehouseStore);
    }

    @Test
    void shouldCreateWarehouse() {

        Warehouse warehouse = new Warehouse();

        warehouse.businessUnitCode = "MWH.100";
        warehouse.location = "ZWOLLE-001";
        warehouse.capacity = 30;
        warehouse.stock = 10;

        useCase.create(warehouse);

        verify(warehouseStore, times(1)).create(warehouse);
    }

    @Test
    void shouldNotCreateNullWarehouse() {

        assertThrows(
                IllegalArgumentException.class,
                () -> useCase.create(null)
        );

        verify(warehouseStore, never()).create(any());
    }

    @Test
    void shouldPassCorrectWarehouseToStore() {

        Warehouse warehouse = new Warehouse();

        warehouse.businessUnitCode = "MWH.100";
        warehouse.location = "AMSTERDAM-001";
        warehouse.capacity = 50;
        warehouse.stock = 20;

        useCase.create(warehouse);

        verify(warehouseStore).create(
                argThat(w ->
                        w.businessUnitCode.equals("MWH.100")
                                && w.location.equals("AMSTERDAM-001")
                                && w.capacity == 50
                                && w.stock == 20
                )
        );
    }
}