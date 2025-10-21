package com.onlinevoting.dto;

import java.util.List;

public class MenuDto {

    private String menuId;
    private String menuName;
    private String icon;
    private String link;
    private List<MenuDto> subMenus;
    
    // Default constructor
    public MenuDto() {}
    
    // Parameterized constructor
    public MenuDto(String menuId, String menuName, String icon, String link) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.icon = icon;
        this.link = link;
    }

    public MenuDto(String menuId, String menuName, String icon, String link, List<MenuDto> subMenus) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.icon = icon;
        this.link = link;
        this.subMenus = subMenus;
    }
    
    public List<MenuDto> getSubMenus() {
        return subMenus;
    }
    
    public void setSubMenus(List<MenuDto> subMenus) {
        this.subMenus = subMenus;
    }
    
    // Getters and Setters
    public String getMenuId() {
        return menuId;
    }
    
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    
    public String getMenuName() {
        return menuName;
    }
    
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getLink() {
        return link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
}
