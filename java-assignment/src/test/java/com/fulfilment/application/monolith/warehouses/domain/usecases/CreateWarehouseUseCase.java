package com.fulfilment.application.monolith.warehouses.domain.usecases;


import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;

public class CreateWarehouseUseCase {

    private final WarehouseStore warehouseStore;

    public CreateWarehouseUseCase(WarehouseStore warehouseStore) {
        this.warehouseStore = warehouseStore;
    }

    public void create(Warehouse warehouse) {

        if (warehouse == null) {
            throw new IllegalArgumentException("Warehouse cannot be null");
        }

        warehouseStore.create(warehouse);
    }
}