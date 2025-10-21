package com.onlinevoting.service;

import java.util.List;

import com.onlinevoting.dto.MenuDto;

public interface MenuService {
    List<MenuDto> getMenuItemsByRoleId(Long roleId);
}
