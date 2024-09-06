package com.btk.bsd.service;

import com.btk.bsd.dto.RoleDTO;
import com.btk.bsd.mapper.RoleMapper;
import com.btk.bsd.model.Role;
import com.btk.bsd.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Autowired
    private RoleMapper roleMapper;

    // GET
    @Cacheable(value = "role", key = "#id")
    public RoleDTO getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::roleToRoleDTO)
                .orElse(null);
    }

    // GET ALL
    @Cacheable(value = "roles")
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::roleToRoleDTO)
                .collect(Collectors.toList());
    }

    // CREATE
    @CachePut(value = "role", key = "#result.id")
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = roleMapper.roleDTOToRole(roleDTO);
        Role savedRole = roleRepository.save(role);
        return roleMapper.roleToRoleDTO(savedRole);
    }

    // UPDATE
    @CachePut(value = "role", key = "#id")
    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role existingRole  = roleRepository.findById(id).orElse(null);
        if (existingRole != null){
            existingRole.setName(roleDTO.getName());
            existingRole.setUsers(roleMapper.roleDTOToRole(roleDTO).getUsers());
            existingRole.setPermissions(roleMapper.roleDTOToRole(roleDTO).getPermissions());

            Role savedRole = roleRepository.save(existingRole);
            return roleMapper.roleToRoleDTO(savedRole);
        }

        return null;
    }

    // DELETE
    @CacheEvict(value = {"user", "users"}, key = "#id", allEntries = true)
    public void deleteUser(Long id) {
        roleRepository.deleteById(id);
    }
}
