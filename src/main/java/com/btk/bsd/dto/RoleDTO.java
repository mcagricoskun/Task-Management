package com.btk.bsd.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {
    private Long id;
    private String name;
    private List<UserDTO> users;
    private Object permissions;

}
