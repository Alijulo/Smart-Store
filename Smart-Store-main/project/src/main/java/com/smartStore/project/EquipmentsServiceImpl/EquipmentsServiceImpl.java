package com.smartStore.project.EquipmentsServiceImpl;

import com.smartStore.project.EmployeeConstants.EmployeeConstants;
import com.smartStore.project.EmployeeUtility.EmployeeUtility;
import com.smartStore.project.EquipmentsModel.EquipmentsEntity;
import com.smartStore.project.EquipmentsRepository.EquipmentsRepo;
import com.smartStore.project.EquipmentsService.EquipmentsService;
import com.smartStore.project.JWTUtil.JwtFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class EquipmentsServiceImpl implements EquipmentsService {
    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EquipmentsRepo equipmentsRepo;

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public ResponseEntity<String> addNewEquipments(Map<String, String> requestMap) {

        try {
            if(jwtFilter.isAdmin()){

                if(validateEquipmentsMap(requestMap,false)){
                    System.out.println("user details"+getEquipmentsFromMap(requestMap, false));

                    equipmentsRepo.save(getEquipmentsFromMap(requestMap,false));
                    System.out.println("Equipment saved successfully!");
                    return EmployeeUtility.getResponseUtility("Equipment Added Successfully!", HttpStatus.OK);
                }

            }
            return EmployeeUtility.getResponseUtility(EmployeeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception: " + e.getMessage());
            return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<String> updateEquipment(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if(validateEquipmentsMap(requestMap,true)){
                   Optional optional= equipmentsRepo.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        equipmentsRepo.save(getEquipmentsFromMap(requestMap,true));
                        return EmployeeUtility.getResponseUtility("Equipment Updated successfully",HttpStatus.OK);
                    }
                    else{
                        return EmployeeUtility.getResponseUtility("Equipment id does not exist",HttpStatus.OK);
                    }
                }
                return EmployeeUtility.getResponseUtility(EmployeeConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
            } else {
                return EmployeeUtility.getResponseUtility(EmployeeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
          e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateEquipmentsMap(Map<String, String> requestMap, boolean validateId) {

        if(requestMap.containsKey("materialName")&&
                requestMap.containsKey("productID")&&
                requestMap.containsKey("partNumber")&&
                requestMap.containsKey("procurementReferenceNumber")&&
                requestMap.containsKey("stockCount")&&
                requestMap.containsKey("status")&&
                requestMap.containsKey("rackNumber")&&
                requestMap.containsKey("pricePerItem")&&
                requestMap.containsKey("productDescription")
        )
        {
            if (requestMap.containsKey("id")) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;

    }

    private EquipmentsEntity getEquipmentsFromMap(Map<String, String> requestMap, boolean isAdd) throws Exception {
        EquipmentsEntity equipments = new EquipmentsEntity();
        try {
            if(isAdd){
                equipments.setId(Integer.parseInt(requestMap.get("id")));
            }
            equipments.setMaterialName(requestMap.get("materialName"));
            equipments.setProductID(requestMap.get("productID"));
            equipments.setPartNumber(requestMap.get("partNumber"));
            equipments.setProcurementReferenceNumber(requestMap.get("procurementReferenceNumber"));
            equipments.setStockCount(Integer.parseInt(requestMap.get("stockCount")));
            equipments.setStatus(requestMap.get("status"));
            equipments.setRackNumber(requestMap.get("rackNumber"));
            equipments.setPricePerItem(Integer.parseInt(requestMap.get("pricePerItem")));
            equipments.setProductDescription(requestMap.get("productDescription"));
        } catch (NullPointerException e) {
            throw new Exception("Missing key in request map: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new Exception("Invalid number format in request map: " + e.getMessage());
        }
        return equipments;
    }
}
