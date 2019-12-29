package my.example.springbootapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import my.example.springbootapp.entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRolename(String name);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_roles (user_id, role_id) VALUES (:user_id, :role_id)", nativeQuery = true)
    void insertRoles(@Param("user_id") Long user_id, @Param("role_id") Long role_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_roles WHERE (user_id = :user_id) and (role_id = :role_id)", nativeQuery = true)
    void deleteRoles(@Param("user_id") Long user_id, @Param("role_id") Long role_id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO roles (id, rolename) VALUES (:id, :rolename)", nativeQuery = true)
    void insertNewRoles(@Param("id") Long id, @Param("rolename") String rolename);
}
