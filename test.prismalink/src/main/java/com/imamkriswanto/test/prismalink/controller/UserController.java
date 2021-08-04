package com.imamkriswanto.test.prismalink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.imamkriswanto.test.prismalink.dto.RoleDto.DetailRole;
import com.imamkriswanto.test.prismalink.dto.UserDto.CreateUser;
import com.imamkriswanto.test.prismalink.dto.UserDto.CreatedDateFilter;
import com.imamkriswanto.test.prismalink.dto.UserDto.DeleteUser;
import com.imamkriswanto.test.prismalink.dto.UserDto.DetailUser;
import com.imamkriswanto.test.prismalink.dto.UserDto.UpdateUser;
import com.imamkriswanto.test.prismalink.service.UserService;

import io.swagger.annotations.ApiOperation;
import java.util.Map;


@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get All User")
    public Map<String, Object> getAll(@RequestBody CreatedDateFilter dateFilter,
    		@RequestParam (value = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam (value = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam (value = "sortBy", defaultValue = "fullName") String sortBy,
			@RequestParam (value = "sortDirection", defaultValue = "ASC") String sortDirection) throws Exception{
        Map<String, Object> resultList = userService.findAll(dateFilter, pageNumber - 1, pageSize, sortBy.trim(), sortDirection);
        
        if (resultList.get("dataCount").equals(0)) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        
        return resultList;
    }
    
    @PostMapping(path="/detail")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
    @ApiOperation("Detail User")
	public Map<String, Object> detail(@RequestBody DetailUser dto) throws Exception{
		
		return userService.detail(dto);
	}
    
    @PostMapping(path="/create")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
    @ApiOperation("Create User")
	public Map<String, Object> create(@RequestBody CreateUser dto) throws Exception{
		
		return userService.create(dto);
	}
    
    @PutMapping(path="/update")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update User")
	public Map<String, Object> update(@RequestBody UpdateUser dto) throws Exception{
		
		return userService.update(dto);
	}
    
    @DeleteMapping(path="/delete")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
    @ApiOperation("Delete User")
	public Map<String, Object> delete(@RequestBody DeleteUser dto) throws Exception{
		
		return userService.delete(dto);
	}
}