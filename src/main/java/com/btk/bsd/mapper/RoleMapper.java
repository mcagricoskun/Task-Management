package com.btk.bsd.mapper;

import com.btk.bsd.dto.RoleDTO;

import com.btk.bsd.model.Role;
import org.modelmapper.ModelMapper;

public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleMapper() {
        this.modelMapper = new ModelMapper();
    }

    public RoleDTO roleToRoleDTO(Role role){
        // Role entity'sini RoleDTO'ya dönüştürüyoruz
        RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);

        // users alanını dolduruyoruz
        roleDTO.setUsers(role.getUsers().stream()
                .map(this::user));
        return roleDTO


    }
}
