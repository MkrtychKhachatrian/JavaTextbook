package khachatrian.service;

import khachatrian.model.RoleUser;

import java.util.List;

public interface RoleService {

    List<RoleUser> getAllRole();

    RoleUser getRoleById(Long id);

    RoleUser saveRole(RoleUser user);
    void deleteRole(Long id);
    void updateRole(Long id, RoleUser roleUser);
}
