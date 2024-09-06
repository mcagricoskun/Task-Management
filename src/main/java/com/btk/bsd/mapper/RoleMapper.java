package com.btk.bsd.mapper;

import com.btk.bsd.dto.RoleDTO;

import com.btk.bsd.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleMapper() {
        this.modelMapper = new ModelMapper();
    }

    public RoleDTO roleToRoleDTO(Role role){
        // Role entity'sini RoleDTO'ya dönüştürüyoruz
        RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
        return modelMapper.map(role, RoleDTO.class);

    }

    public Role roleDTOToRole(RoleDTO roleDTO) {
        // RoleDTO'yu Role entity'sine dönüştürüyoruz
        return modelMapper.map(roleDTO, Role.class);
    }
}
