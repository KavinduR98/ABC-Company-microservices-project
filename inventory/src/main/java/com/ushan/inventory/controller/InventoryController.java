package com.ushan.inventory.controller;

import com.ushan.inventory.dto.InventoryDTO;
import com.ushan.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping(path = "/test")
    public String test(){
        return "This is an Inventory service!";
    }

    @GetMapping(path = "/get_items")
    public List<InventoryDTO> getItems(){
        return inventoryService.getAllItems();
    }

    @PostMapping(path = "/save_item")
    public InventoryDTO saveItem(@RequestBody InventoryDTO inventoryDTO){
        System.out.println("Received OrderDTO: " + inventoryDTO); // Log the received data
        return inventoryService.saveItem(inventoryDTO);
    }

    @PutMapping(path = "/update_item")
    public InventoryDTO updateItem(@RequestBody InventoryDTO inventoryDTO){
        return inventoryService.updateItem(inventoryDTO);
    }

    @DeleteMapping(path = "/delete_item/{itemId}")
    public String deleteItem(@PathVariable Integer itemId){
        return inventoryService.deleteItem(itemId);
    }

    @GetMapping(path = "/get_item/{itemId}")
    public InventoryDTO getItem(@PathVariable Integer itemId){
        return inventoryService.getItem(itemId);
    }

}
