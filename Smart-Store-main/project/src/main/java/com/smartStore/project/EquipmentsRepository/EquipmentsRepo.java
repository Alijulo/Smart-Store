package com.smartStore.project.EquipmentsRepository;


import com.smartStore.project.EquipmentsModel.EquipmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EquipmentsRepo extends JpaRepository <EquipmentsEntity,Integer>{
    List<EquipmentsEntity> getAllEquipments();
}
