package com.btk.bsd.controler;


import com.btk.bsd.dto.RoleDTO;

import com.btk.bsd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // GET
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@RequestParam(required = true) Long id){
        RoleDTO roleDTO = roleService.getRoleById(id);
        if (roleDTO != null){
            return new ResponseEntity<>(roleDTO, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND)
        }
    }

    // GET ALL
    @GetMapping("/get-roles")
    public ResponseEntity<List<RoleDTO>> getAllRole(){
        List<RoleDTO> roleDTO = roleService.getAllRoles();
        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }

    // CREATE ROLE
    @PostMapping("/create-role")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO){
        RoleDTO createdRole = roleService.createRole(roleDTO);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping("/update-role")
    public ResponseEntity<RoleDTO> updateRole(@RequestParam Long id, @RequestBody RoleDTO roleDTO){
        RoleDTO updatedRole = roleService.updateRole(roleDTO);
        if (updatedRole != null){
            return new ResponseEntity<>(updatedRole, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // DELETE
    @DeleteMapping("/delete-role")
    public ResponseEntity<Void> deleteUser(@RequestBody Long id){
        roleService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}





