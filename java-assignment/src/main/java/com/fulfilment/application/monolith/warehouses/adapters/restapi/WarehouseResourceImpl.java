package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;


import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseResource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@RequestScoped
public class WarehouseResourceImpl implements WarehouseResource {

  @Inject
  private WarehouseRepository warehouseRepository;

  @Override
  public List<Warehouse> listAllWarehousesUnits() {
    return warehouseRepository.getAll()
            .stream()
            .map(this::toWarehouseResponse)
            .toList();
  }

  @Override
  public Warehouse createANewWarehouseUnit(@NotNull Warehouse data) {
    throw new UnsupportedOperationException(
            "Unimplemented method 'createANewWarehouseUnit'");
  }

  @Override
  public Warehouse getAWarehouseUnitByID(String id) {
    throw new UnsupportedOperationException(
            "Unimplemented method 'getAWarehouseUnitByID'");
  }

  @Override
  public void archiveAWarehouseUnitByID(String id) {
    throw new UnsupportedOperationException(
            "Unimplemented method 'archiveAWarehouseUnitByID'");
  }

  @Override
  public Warehouse replaceTheCurrentActiveWarehouse(
          String businessUnitCode,
          @NotNull Warehouse data) {

    throw new UnsupportedOperationException(
            "Unimplemented method 'replaceTheCurrentActiveWarehouse'");
  }

  private Warehouse toWarehouseResponse(
          com.fulfilment.application.monolith.warehouses.domain.models.Warehouse warehouse) {

    Warehouse response = new Warehouse();

    response.setBusinessUnitCode(warehouse.businessUnitCode);
    response.setLocation(warehouse.location);
    response.setCapacity(warehouse.capacity);
    response.setStock(warehouse.stock);

    return response;
  }
}