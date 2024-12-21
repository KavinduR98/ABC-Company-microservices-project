package com.ushan.inventory.service;

import com.ushan.inventory.dto.InventoryDTO;
import com.ushan.inventory.exceptions.ItemNotFoundException;
import com.ushan.inventory.model.Inventory;
import com.ushan.inventory.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<InventoryDTO> getAllItems(){
        List<Inventory> itemList = inventoryRepository.findAll();
        return modelMapper.map(itemList, new TypeToken<List<InventoryDTO>>(){}.getType());
    }

    public InventoryDTO saveItem(InventoryDTO inventoryDTO){
        inventoryRepository.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    public InventoryDTO updateItem(InventoryDTO inventoryDTO){
        inventoryRepository.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    public String deleteItem(Integer itemId){
        // Check if the item exists
        boolean exists = inventoryRepository.existsById(itemId);

        if (!exists) {
            return "Item with ID " + itemId + " not found!";
        }

        // If the item exists, delete it
        inventoryRepository.deleteById(itemId);
        return "Item Deleted!";
    }

    public InventoryDTO getItem(Integer itemId){
        Inventory inventory = inventoryRepository.getItemById(itemId);
        return modelMapper.map(inventory, InventoryDTO.class);
    }

}
