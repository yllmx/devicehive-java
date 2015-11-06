package io.swagger.client.api;

import io.swagger.client.CollectionFormats.*;

import retrofit.Callback;
import retrofit.http.*;
import retrofit.mime.*;

import io.swagger.client.model.DeviceClass;
import io.swagger.client.model.Equipment;
import io.swagger.client.model.EquipmentUpdate;
import io.swagger.client.model.DeviceClassUpdate;

import java.util.*;

public interface DeviceClassApi {
  
  /**
   * List device classes
   * Sync method
   * Gets list of device classes.
   * @param name Filter by device class name.
   * @param namePattern Filter by device class name pattern.
   * @param version Filter by device class version.
   * @param sortField Result list sort field.
   * @param sortOrder Result list sort order.
   * @param take Number of records to take from the result list.
   * @param skip Number of records to skip from the result list.
   * @return List<DeviceClass>
   */
  
  @GET("/device/class")
  List<DeviceClass> getDeviceClassList(
    @Query("name") String name, @Query("namePattern") String namePattern, @Query("version") String version, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * List device classes
   * Async method
   * @param name Filter by device class name.
   * @param namePattern Filter by device class name pattern.
   * @param version Filter by device class version.
   * @param sortField Result list sort field.
   * @param sortOrder Result list sort order.
   * @param take Number of records to take from the result list.
   * @param skip Number of records to skip from the result list.
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/class")
  void getDeviceClassList(
    @Query("name") String name, @Query("namePattern") String namePattern, @Query("version") String version, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, Callback<List<DeviceClass>> cb
  );
  
  /**
   * Create device class
   * Sync method
   * Creates new device class.
   * @param body Device class body
   * @return DeviceClass
   */
  
  @POST("/device/class")
  DeviceClass insertDeviceClass(
    @Body DeviceClass body
  );

  /**
   * Create device class
   * Async method
   * @param body Device class body
   * @param cb callback method
   * @return void
   */
  
  @POST("/device/class")
  void insertDeviceClass(
    @Body DeviceClass body, Callback<DeviceClass> cb
  );
  
  /**
   * Create equipment
   * Sync method
   * Creates equipment
   * @param deviceClassId Device class id
   * @param body Equipment body
   * @return Void
   */
  
  @POST("/device/class/{deviceClassId}/equipment")
  Void insertEquipment(
    @Path("deviceClassId") Long deviceClassId, @Body Equipment body
  );

  /**
   * Create equipment
   * Async method
   * @param deviceClassId Device class id
   * @param body Equipment body
   * @param cb callback method
   * @return void
   */
  
  @POST("/device/class/{deviceClassId}/equipment")
  void insertEquipment(
    @Path("deviceClassId") Long deviceClassId, @Body Equipment body, Callback<Void> cb
  );
  
  /**
   * Get equipment
   * Sync method
   * Returns equipment by device class id and equipment id
   * @param deviceClassId Device class id
   * @param id Equipment id
   * @return Void
   */
  
  @GET("/device/class/{deviceClassId}/equipment/{id}")
  Void getEquipment(
    @Path("deviceClassId") Long deviceClassId, @Path("id") Long id
  );

  /**
   * Get equipment
   * Async method
   * @param deviceClassId Device class id
   * @param id Equipment id
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/class/{deviceClassId}/equipment/{id}")
  void getEquipment(
    @Path("deviceClassId") Long deviceClassId, @Path("id") Long id, Callback<Void> cb
  );
  
  /**
   * Update equipment
   * Sync method
   * Updates equipment
   * @param deviceClassId Device class id
   * @param id Equipment id
   * @param body Equipment body
   * @return Void
   */
  
  @PUT("/device/class/{deviceClassId}/equipment/{id}")
  Void updateEquipment(
    @Path("deviceClassId") Long deviceClassId, @Path("id") Long id, @Body EquipmentUpdate body
  );

  /**
   * Update equipment
   * Async method
   * @param deviceClassId Device class id
   * @param id Equipment id
   * @param body Equipment body
   * @param cb callback method
   * @return void
   */
  
  @PUT("/device/class/{deviceClassId}/equipment/{id}")
  void updateEquipment(
    @Path("deviceClassId") Long deviceClassId, @Path("id") Long id, @Body EquipmentUpdate body, Callback<Void> cb
  );
  
  /**
   * Delete equipment
   * Sync method
   * Deletes equipment
   * @param deviceClassId Device class id
   * @param id Equipment id
   * @return Void
   */
  
  @DELETE("/device/class/{deviceClassId}/equipment/{id}")
  Void deleteEquipment(
    @Path("deviceClassId") Long deviceClassId, @Path("id") Long id
  );

  /**
   * Delete equipment
   * Async method
   * @param deviceClassId Device class id
   * @param id Equipment id
   * @param cb callback method
   * @return void
   */
  
  @DELETE("/device/class/{deviceClassId}/equipment/{id}")
  void deleteEquipment(
    @Path("deviceClassId") Long deviceClassId, @Path("id") Long id, Callback<Void> cb
  );
  
  /**
   * Get device class
   * Sync method
   * Gets information about device class and its equipment.
   * @param id Device class identifier.
   * @return DeviceClass
   */
  
  @GET("/device/class/{id}")
  DeviceClass getDeviceClass(
    @Path("id") Long id
  );

  /**
   * Get device class
   * Async method
   * @param id Device class identifier.
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/class/{id}")
  void getDeviceClass(
    @Path("id") Long id, Callback<DeviceClass> cb
  );
  
  /**
   * Update device class
   * Sync method
   * Updates an existing device class.
   * @param id Device class identifier.
   * @param body Device class body
   * @return Void
   */
  
  @PUT("/device/class/{id}")
  Void updateDeviceClass(
    @Path("id") Long id, @Body DeviceClassUpdate body
  );

  /**
   * Update device class
   * Async method
   * @param id Device class identifier.
   * @param body Device class body
   * @param cb callback method
   * @return void
   */
  
  @PUT("/device/class/{id}")
  void updateDeviceClass(
    @Path("id") Long id, @Body DeviceClassUpdate body, Callback<Void> cb
  );
  
  /**
   * Update device class
   * Sync method
   * Deletes an existing device class.
   * @param id Device class identifier.
   * @return Void
   */
  
  @DELETE("/device/class/{id}")
  Void deleteDeviceClass(
    @Path("id") Long id
  );

  /**
   * Update device class
   * Async method
   * @param id Device class identifier.
   * @param cb callback method
   * @return void
   */
  
  @DELETE("/device/class/{id}")
  void deleteDeviceClass(
    @Path("id") Long id, Callback<Void> cb
  );
  
}
