package com.sportyfind.webapi.controllers;

import com.sportyfind.webapi.repositories.OwnerRepository;
import com.sportyfind.webapi.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/getAll")
    public Object getAll() {
        return ownerService.getAllOwners();
    }
}
