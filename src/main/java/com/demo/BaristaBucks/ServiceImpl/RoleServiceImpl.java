package com.demo.BaristaBucks.ServiceImpl;

import com.demo.BaristaBucks.Entity.Role;
import com.demo.BaristaBucks.Repository.RoleRepository;
import com.demo.BaristaBucks.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role addRole(String role) {
        Role newRole = new Role();
        newRole.setName(role);
        roleRepository.save(newRole);
        return newRole;
    }
}
