package com.smartStore.project.EquipmentsService;

import com.smartStore.project.EquipmentsModel.EquipmentsEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface EquipmentsService {
    ResponseEntity<String> addNewEquipments(Map<String, String> requestMap);
    ResponseEntity<String> updateEquipment(Map<String, String> requestMap);
}
