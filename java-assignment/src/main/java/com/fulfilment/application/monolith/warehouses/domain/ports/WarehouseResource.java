package com.fulfilment.application.monolith.warehouses.domain.ports;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public interface WarehouseResource {

    List<Warehouse> listAllWarehousesUnits();

    Warehouse createANewWarehouseUnit(@NotNull Warehouse data);

    Warehouse getAWarehouseUnitByID(String id);

    void archiveAWarehouseUnitByID(String id);

    Warehouse replaceTheCurrentActiveWarehouse(
            String businessUnitCode,
            @NotNull Warehouse data);
}