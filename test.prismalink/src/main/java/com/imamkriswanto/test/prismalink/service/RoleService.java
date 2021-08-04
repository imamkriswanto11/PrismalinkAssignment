package com.imamkriswanto.test.prismalink.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.imamkriswanto.test.prismalink.config.ModelMapperConfig;
import com.imamkriswanto.test.prismalink.dto.RoleDto;
import com.imamkriswanto.test.prismalink.dto.RoleDto.CreateRole;
import com.imamkriswanto.test.prismalink.dto.RoleDto.DeleteRole;
import com.imamkriswanto.test.prismalink.dto.RoleDto.DetailRole;
import com.imamkriswanto.test.prismalink.dto.RoleDto.UpdateRole;
import com.imamkriswanto.test.prismalink.dto.UserDto;
import com.imamkriswanto.test.prismalink.dto.UserDto.CreateUser;
import com.imamkriswanto.test.prismalink.dto.UserDto.CreatedDateFilter;
import com.imamkriswanto.test.prismalink.dto.UserDto.DeleteUser;
import com.imamkriswanto.test.prismalink.dto.UserDto.UpdateUser;
import com.imamkriswanto.test.prismalink.model.Role;
import com.imamkriswanto.test.prismalink.model.User;
import com.imamkriswanto.test.prismalink.repository.RoleRepository;
import com.imamkriswanto.test.prismalink.repository.UserRepository;

@Service
@Transactional
public class RoleService {
	
	
	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ModelMapperConfig mapper;
	
	public Map<String, Object> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir)
			throws Exception {

		Map<String, Object> resultMap = new HashMap<>();
		
		try {

			Page<Role> page = roleRepo.findAll(PageRequest.of(pageNumber, pageSize, Direction.valueOf(sortDir.toUpperCase()), sortBy));

			List<RoleDto> dtoResult = new ArrayList<>();
			for (Role model : page) {
				RoleDto dto = mapper.modelMapper().map(model, RoleDto.class);
		 	    dtoResult.add(dto);
			}

			List<Role> modelList = roleRepo.findAll();

			resultMap.put("data", dtoResult);
			resultMap.put("dataCount", dtoResult.size());
			resultMap.put("totalData", modelList.size());
			resultMap.put("meta", page.getPageable());
			return resultMap;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public Map<String, Object> detail(DetailRole dto) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		try {

			Role model = new Role();
			model = roleRepo.findById(dto.getId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
			
			RoleDto roleDto = mapper.modelMapper().map(model, RoleDto.class);
			resultMap.put("result", roleDto);
			resultMap.put("resultStatus", "OK");
			return resultMap;
			
		} catch (Exception e) {			
			throw new Exception(e);
		}
	}
	
	public Map<String, Object> create(CreateRole dto) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		try {

			Role model = new Role();
			
			model.setRoleName(dto.getRoleName());
			roleRepo.save(model);

			resultMap.put("id", model.getId());
			resultMap.put("resultMessage", "Role berhasil disimpan");
			resultMap.put("resultStatus", "OK");

			return resultMap;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public Map<String, Object> update(UpdateRole dto) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		try {

			Role model = roleRepo.findById(dto.getId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
			
			model.setRoleName(dto.getRoleName());
			roleRepo.save(model);

			resultMap.put("id", model.getId());
			resultMap.put("resultMessage", "Role berhasil diubah");
			resultMap.put("resultStatus", "OK");

			return resultMap;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public Map<String, Object> delete(DeleteRole dto) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		try {

			Role model = roleRepo.findById(dto.getId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
			
			roleRepo.deleteRoles(dto.getId());
			roleRepo.deleteById(dto.getId());

			resultMap.put("id", model.getId());
			resultMap.put("resultMessage", "Role berhasil dihapus");
			resultMap.put("resultStatus", "OK");

			return resultMap;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}