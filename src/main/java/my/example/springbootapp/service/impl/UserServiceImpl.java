package my.example.springbootapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import my.example.springbootapp.entities.Role;
import my.example.springbootapp.entities.User;
import my.example.springbootapp.exception.DBException;
import my.example.springbootapp.repository.UserRepository;
import my.example.springbootapp.service.RoleService;
import my.example.springbootapp.service.UserService;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Iterable<User> getAllUsers() throws DBException {
        return userRepository.findAll();
    }

    @Override
    public User getUserByName(String name) throws DBException {
        return userRepository.findByUsername(name).get();
    }

    @Override
    public User getUserById(Long id) throws DBException {
        return userRepository.findById(id).get();
    }

    @Override
    public Long getUserIdByName(String name) throws DBException {
        return userRepository.findIdByUsername(name).get().getId();
    }

    @Override
    public void addUser(User user) throws DBException {
        Long role_ID = 0L;

        if (StringUtils.isEmpty(user.getAge())) {
            user.setAge(0);
        }

        if (StringUtils.isEmpty(user.getRole())) {
            user.setRole("admin");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole().toUpperCase());

        if (user.getRole().contains("ADMIN")) {
            user.setRole("ROLE_ADMIN");
            role_ID = roleService.getRoleIdByName(user.getRole());
        }

        if (user.getRole().contains("USER")) {
            user.setRole("ROLE_USER");
            role_ID = roleService.getRoleIdByName(user.getRole());
        }

        user.setRole_id(role_ID);

        userRepository.saveAndFlush(user);
        roleService.addRoles(getUserIdByName(user.getUsername()), role_ID);
    }

    @Override
    public void editUser(User user) throws DBException {
        roleService.deleteRoles(user.getId(), user.getRole_id());

        if (StringUtils.isEmpty(user.getUsername())) {
            user.setUsername(getUserById(user.getId()).getUsername());
        }

        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(getUserById(user.getId()).getPassword());
        } else {
            if (!user.getPassword().contains("$")) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }

        if (ObjectUtils.isEmpty(user.getAge())) {
            user.setAge(getUserById(user.getId()).getAge());
        }

        if (StringUtils.isEmpty(user.getRole())) {
            user.setRole(getUserById(user.getId()).getRole());
            user.setRole_id(getUserById(user.getId()).getRole_id());
        } else {
            user.setRole(user.getRole().toUpperCase());

            if (user.getRole().contains("ADMIN")) {
                user.setRole("ROLE_ADMIN");
                user.setRole_id(roleService.getRoleIdByName(user.getRole()));
            }

            if (user.getRole().contains("USER")) {
                user.setRole("ROLE_USER");
                user.setRole_id(roleService.getRoleIdByName(user.getRole()));
            }
        }

        userRepository.saveAndFlush(user);
        roleService.addRoles(user.getId(), user.getRole_id());
    }

    @Override
    public void deleteUserById(Long id) throws DBException {
        userRepository.deleteById(id);
    }

    @Override
    public Boolean isExistsUser(String name) throws DBException {
        try {
            return getUserByName(name) != null;
        } catch (NoSuchElementException e) {
            e.getMessage();

            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = getUserByName(username);
        } catch (DBException e) {
            e.getMessage();
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRolename()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities
        );
    }
}
