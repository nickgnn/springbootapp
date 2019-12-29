package my.example.springbootapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import my.example.springbootapp.exception.DBException;
import my.example.springbootapp.repository.RoleRepository;
import my.example.springbootapp.service.RoleService;

import java.util.NoSuchElementException;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Long getRoleIdByName(String name) throws DBException {
        if (isExistsRole(name)) {
            return roleRepository.findRoleByRolename(name).get().getId();
        } else {
            roleRepository.insertNewRoles(1l, "ROLE_USER");

            return roleRepository.findRoleByRolename(name).get().getId();
        }
    }

    @Override
    public void addRoles(Long user_id, Long role_id) throws DBException {
        roleRepository.insertRoles(user_id, role_id);
    }

    @Override
    public void deleteRoles(Long user_id, Long role_id) throws DBException {
        roleRepository.deleteRoles(user_id, role_id);
    }

    private Boolean isExistsRole(String name) throws DBException {
        try {
            return roleRepository.findRoleByRolename(name).get() != null;
        } catch (NoSuchElementException e) {
            e.getMessage();

            return false;
        }
    }
}
