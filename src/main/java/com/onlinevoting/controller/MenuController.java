package com.onlinevoting.controller;

import com.onlinevoting.dto.MenuDto;
import com.onlinevoting.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {
    
    @Autowired
    private MenuService menuService;

    @GetMapping(path="/v1/menu/role/{roleId}", produces = "application/json")
    public ResponseEntity<List<MenuDto>> getMenuByRole(@PathVariable Long roleId) {
        List<MenuDto> roleMenuItems = menuService.getMenuItemsByRoleId(roleId);
        return ResponseEntity.ok(roleMenuItems);
    }
}
