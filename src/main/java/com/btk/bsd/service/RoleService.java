package com.btk.bsd.service;

import com.btk.bsd.dto.RoleDTO;
import com.btk.bsd.mapper.RoleMapper;
import com.btk.bsd.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Autowired
    private RoleMapper roleMapper;

    public RoleDTO getRoleById(Long id) {
    }

    public List<RoleDTO> getAllRoles() {
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
    }

    public RoleDTO updateRole(RoleDTO roleDTO) {
    }

    public void deleteUser(Long id) {
    }
}
