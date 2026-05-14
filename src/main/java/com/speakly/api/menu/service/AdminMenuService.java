package com.speakly.api.menu.service;

import com.speakly.api.menu.dto.MenuMetaDTO;
import com.speakly.api.menu.dto.MenuResponse;
import com.speakly.api.entity.AdminMenu;
import com.speakly.api.repository.AdminMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminMenuService {

    private final AdminMenuRepository adminMenuRepository;

    public List<MenuResponse> getSimpleMenus() {
        List<AdminMenu> menus = adminMenuRepository.findByEnabledTrueOrderBySortOrderAsc();

        return menus.stream()
                .filter(menu -> menu.getParentId() == null || menu.getParentId() == 0)
                .sorted(Comparator.comparing(AdminMenu::getSortOrder))
                .map(menu -> buildMenuTree(menu, menus))
                .toList();
    }

    private MenuResponse buildMenuTree(AdminMenu menu, List<AdminMenu> allMenus) {
        MenuResponse response = convertToDTO(menu);

        List<MenuResponse> children = allMenus.stream()
                .filter(item -> Objects.equals(item.getParentId(), menu.getId()))
                .sorted(Comparator.comparing(AdminMenu::getSortOrder))
                .map(child -> buildMenuTree(child, allMenus))
                .toList();

        response.setChildren(children.isEmpty() ? null : children);

        return response;
    }

    private MenuResponse convertToDTO(AdminMenu menu) {
        MenuResponse response = new MenuResponse();

        response.setName(menu.getName());
        response.setPath(menu.getPath());
        response.setComponent(menu.getComponent());

        MenuMetaDTO meta = new MenuMetaDTO();
        meta.setTitle(menu.getTitle());
        meta.setIcon(menu.getIcon());
        meta.setKeepAlive(menu.getKeepAlive());
        meta.setHideInMenu(menu.getIsHide());
        meta.setHideInTab(menu.getIsHideTab());
        meta.setFullPage(menu.getIsFullPage());
        meta.setActivePath(menu.getActivePath());
        meta.setLink(menu.getLink());

        response.setMeta(meta);

        return response;
    }
}
