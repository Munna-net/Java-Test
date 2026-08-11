package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;

@ApplicationScoped
public class ReplaceWarehouseUseCase implements ReplaceWarehouseOperation {

  private final WarehouseStore warehouseStore;

  public ReplaceWarehouseUseCase(WarehouseStore warehouseStore) {
    this.warehouseStore = warehouseStore;
  }

  @Override
  public void replace(Warehouse newWarehouse) {

    Warehouse oldWarehouse =
            warehouseStore.findByBusinessUnitCode(
                    newWarehouse.businessUnitCode
            );

    if (oldWarehouse == null) {
      throw new IllegalArgumentException(
              "Warehouse does not exist"
      );
    }

    if (oldWarehouse.archivedAt != null) {
      throw new IllegalStateException(
              "Warehouse is already archived"
      );
    }

    oldWarehouse.archivedAt = LocalDateTime.now();

    warehouseStore.update(oldWarehouse);

    newWarehouse.createdAt = LocalDateTime.now();
    newWarehouse.archivedAt = null;

    warehouseStore.create(newWarehouse);
  }
}
