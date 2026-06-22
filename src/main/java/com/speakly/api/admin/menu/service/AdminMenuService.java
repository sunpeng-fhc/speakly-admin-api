package com.speakly.api.admin.menu.service;

import com.speakly.api.admin.menu.dto.AuthButtonDTO;
import com.speakly.api.admin.menu.dto.MenuMetaDTO;
import com.speakly.api.admin.menu.dto.MenuResponse;
import com.speakly.api.domain.entity.AdminButton;
import com.speakly.api.domain.entity.AdminMenu;
import com.speakly.api.repository.AdminButtonRepository;
import com.speakly.api.repository.AdminMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminMenuService {

    private final AdminMenuRepository adminMenuRepository;
    private final AdminButtonRepository adminButtonRepository;

    public List<MenuResponse> getSimpleMenus() {
        List<AdminMenu> menus = adminMenuRepository.findByEnabledTrueOrderBySortOrderAsc();

        List<Long> menuIds = menus.stream()
                .map(AdminMenu::getId)
                .toList();

        Map<Long, List<AdminButton>> buttonMap = adminButtonRepository
                .findByMenuIdInAndEnabledTrue(menuIds)
                .stream()
                .collect(Collectors.groupingBy(AdminButton::getMenuId));

        return menus.stream()
                .filter(menu -> menu.getParentId() == null || menu.getParentId() == 0)
                .sorted(Comparator.comparing(AdminMenu::getSortOrder))
                .map(menu -> buildMenuTree(menu, menus, buttonMap))
                .toList();
    }

    private MenuResponse buildMenuTree(
            AdminMenu menu,
            List<AdminMenu> allMenus,
            Map<Long, List<AdminButton>> buttonMap
    ) {
        MenuResponse response = convertToDTO(menu, buttonMap);

        List<MenuResponse> children = allMenus.stream()
                .filter(item -> Objects.equals(item.getParentId(), menu.getId()))
                .sorted(Comparator.comparing(AdminMenu::getSortOrder))
                .map(child -> buildMenuTree(child, allMenus, buttonMap))
                .toList();

        response.setChildren(children.isEmpty() ? null : children);

        return response;
    }

    private MenuResponse convertToDTO(
            AdminMenu menu,
            Map<Long, List<AdminButton>> buttonMap
    ) {
        MenuResponse response = new MenuResponse();

        response.setId(menu.getId());
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
        meta.setDate(
                menu.getUpdatedAt() == null
                        ? null
                        : menu.getUpdatedAt()
                          .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        meta.setEnabled(menu.getEnabled());

        List<AuthButtonDTO> authList = buttonMap
                .getOrDefault(menu.getId(), List.of())
                .stream()
                .sorted(Comparator.comparing(AdminButton::getId))
                .map(button -> new AuthButtonDTO(
                        button.getButtonName(),
                        button.getButtonCode()
                ))
                .toList();

        meta.setAuthList(authList.isEmpty() ? null : authList);

        response.setMeta(meta);

        return response;
    }
}