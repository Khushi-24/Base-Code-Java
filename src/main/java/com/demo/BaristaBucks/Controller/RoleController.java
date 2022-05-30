package com.demo.BaristaBucks.Controller;

import com.demo.BaristaBucks.Entity.Role;
import com.demo.BaristaBucks.Service.RoleService;
import com.demo.BaristaBucks.Util.ApiResponse;
import com.demo.BaristaBucks.Util.EndPoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping(EndPoints.Role.ADD_ROLE)
    public ResponseEntity<?> addRole(@RequestBody String role){
        Role newRole = roleService.addRole(role);
        return ApiResponse.sendCreatedResponse("Created", newRole);
    }
}
