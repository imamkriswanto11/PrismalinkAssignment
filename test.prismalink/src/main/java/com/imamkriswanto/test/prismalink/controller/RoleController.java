package com.imamkriswanto.test.prismalink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.imamkriswanto.test.prismalink.dto.RoleDto.CreateRole;
import com.imamkriswanto.test.prismalink.dto.RoleDto.DeleteRole;
import com.imamkriswanto.test.prismalink.dto.RoleDto.DetailRole;
import com.imamkriswanto.test.prismalink.dto.RoleDto.UpdateRole;
import com.imamkriswanto.test.prismalink.dto.UserDto.CreateUser;
import com.imamkriswanto.test.prismalink.dto.UserDto.CreatedDateFilter;
import com.imamkriswanto.test.prismalink.dto.UserDto.DeleteUser;
import com.imamkriswanto.test.prismalink.dto.UserDto.UpdateUser;
import com.imamkriswanto.test.prismalink.service.RoleService;
import com.imamkriswanto.test.prismalink.service.UserService;

import io.swagger.annotations.ApiOperation;
import java.util.Map;


@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get All Role")
    public Map<String, Object> getAll(@RequestParam (value = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam (value = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam (value = "sortBy", defaultValue = "fullName") String sortBy,
			@RequestParam (value = "sortDirection", defaultValue = "ASC") String sortDirection) throws Exception{
        Map<String, Object> resultList = roleService.findAll(pageNumber - 1, pageSize, sortBy.trim(), sortDirection);
        
        if (resultList.get("dataCount").equals(0)) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        
        return resultList;
    }
    
    @PostMapping(path="/detail")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
    @ApiOperation("Detail Role")
	public Map<String, Object> detail(@RequestBody DetailRole dto) throws Exception{
		
		return roleService.detail(dto);
	}
    
    @PostMapping(path="/create")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
    @ApiOperation("Create Role")
	public Map<String, Object> create(@RequestBody CreateRole dto) throws Exception{
		
		return roleService.create(dto);
	}
    
    @PutMapping(path="/update")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update Role")
	public Map<String, Object> update(@RequestBody UpdateRole dto) throws Exception{
		
		return roleService.update(dto);
	}
    
    @DeleteMapping(path="/delete")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
    @ApiOperation("Delete Role")
	public Map<String, Object> delete(@RequestBody DeleteRole dto) throws Exception{
		
		return roleService.delete(dto);
	}
}