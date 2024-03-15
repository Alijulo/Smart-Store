package com.smartStore.project.EquipmentsRestConImp;

import com.google.common.base.Strings;
import com.smartStore.project.EmployeeConstants.EmployeeConstants;
import com.smartStore.project.EmployeeUtility.EmployeeUtility;
import com.smartStore.project.EquipmentsModel.EquipmentsEntity;
import com.smartStore.project.EquipmentsRepository.EquipmentsRepo;
import com.smartStore.project.EquipmentsRestCon.EquipmentsRestController;
import com.smartStore.project.EquipmentsService.EquipmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EquipmentsRestConImp implements EquipmentsRestController {
    @Autowired
    EquipmentsService equipmentsService;

    @Autowired
    EquipmentsRepo equipmentsRepo;

    @Override
    public ResponseEntity<String> addNewEquipments(Map<String, String> requestMap) {
        try {
            return equipmentsService.addNewEquipments(requestMap);

        }
        catch (Exception e){
            e.printStackTrace();

        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<EquipmentsEntity>>  getAllEquipments(String filterVale) {
        try{
            if(!Strings.isNullOrEmpty(filterVale)&& filterVale.equalsIgnoreCase("true"))
                return new ResponseEntity<>(equipmentsRepo.getAllEquipments(), HttpStatus.OK);
        return new ResponseEntity<>(equipmentsRepo.findAll(),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateEquipment(Map<String, String> requestMap) {
        try{
            return equipmentsService.updateEquipment(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
