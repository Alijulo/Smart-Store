package com.smartStore.project.EquipmentsModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;



@NamedQuery(name= "EquipmentsEntity.getAllEquipments", query = "select e from EquipmentsEntity e")

@Entity
@Table(name="Store_Material")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "Material_Name")
    public String materialName;

    @Column(name = "product_ID")
    public String productID;

    @Column(name = "Part_Number")
    public String partNumber;

    @Column(name = "Procurement_Reference_Number")
    public String procurementReferenceNumber;

    @Column(name = "Number_of_Items_in_Stock")
    public int stockCount;

    @Column(name = "Status")
    public String status;

    @Column(name = "RackNumber")
    public  String rackNumber;

    @Column(name = "Price_Per_Item")
    public int pricePerItem;

    @Column(name = "Product_Description")
    public String productDescription;
    
}
