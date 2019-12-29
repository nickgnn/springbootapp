package my.example.springbootapp.service;

import my.example.springbootapp.exception.DBException;

public interface RoleService {
    Long getRoleIdByName(String name) throws DBException;
    void addRoles(Long user_id, Long role_id) throws DBException;
    void deleteRoles(Long user_id, Long role_id) throws DBException;
}