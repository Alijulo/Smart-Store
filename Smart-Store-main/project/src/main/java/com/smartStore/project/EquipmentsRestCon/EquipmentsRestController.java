package com.smartStore.project.EquipmentsRestCon;

import com.smartStore.project.EquipmentsModel.EquipmentsEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RequestMapping("/equipments")
public interface EquipmentsRestController {
    @PostMapping("/add")
    ResponseEntity<String> addNewEquipments(@RequestBody Map<String, String> requestMap);

    @GetMapping("/getEquipments")
    ResponseEntity<List<EquipmentsEntity>> getAllEquipments(@RequestParam(required = false)
                                                            String filterValue);

    @PostMapping("/update")
    ResponseEntity<String> updateEquipment(@RequestBody Map<String, String> requestMap);

}
