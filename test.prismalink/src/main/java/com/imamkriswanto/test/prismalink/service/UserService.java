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
import com.imamkriswanto.test.prismalink.dto.UserDto;
import com.imamkriswanto.test.prismalink.dto.RoleDto.DetailRole;
import com.imamkriswanto.test.prismalink.dto.UserDto.CreateUser;
import com.imamkriswanto.test.prismalink.dto.UserDto.CreatedDateFilter;
import com.imamkriswanto.test.prismalink.dto.UserDto.DeleteUser;
import com.imamkriswanto.test.prismalink.dto.UserDto.DetailUser;
import com.imamkriswanto.test.prismalink.dto.UserDto.UpdateUser;
import com.imamkriswanto.test.prismalink.model.Role;
import com.imamkriswanto.test.prismalink.model.User;
import com.imamkriswanto.test.prismalink.repository.RoleRepository;
import com.imamkriswanto.test.prismalink.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ModelMapperConfig mapper;
	
	public Map<String, Object> findAll(CreatedDateFilter dateFilter, Integer pageNumber, Integer pageSize, String sortBy, String sortDir)
			throws Exception {

		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			
			if(dateFilter.getCreatedDateFrom() == null || dateFilter.getCreatedDateTo() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "createdDateFrom and createdDateTo must be filled");
			}
			
			Page<User> page = userRepo.findAllByCreatedDateBetween(dateFilter.getCreatedDateFrom(), dateFilter.getCreatedDateTo(), 
					PageRequest.of(pageNumber, pageSize, Direction.valueOf(sortDir.toUpperCase()), sortBy));

			List<UserDto> dtoResult = new ArrayList<>();
			for (User model : page) {
				UserDto dto = mapper.modelMapper().map(model, UserDto.class);
		 	    dtoResult.add(dto);
			}

			List<User> modelList = userRepo.findAll();

			resultMap.put("data", dtoResult);
			resultMap.put("dataCount", dtoResult.size());
			resultMap.put("totalData", modelList.size());
			resultMap.put("meta", page.getPageable());
			return resultMap;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public Map<String, Object> detail(DetailUser dto) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		try {

			User model = new User();
			model = userRepo.findById(dto.getId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
			
			UserDto userDto = mapper.modelMapper().map(model, UserDto.class);
			resultMap.put("result", userDto);
			resultMap.put("resultStatus", "OK");
			return resultMap;
			
		} catch (Exception e) {			
			throw new Exception(e);
		}
	}
	
	public Map<String, Object> create(CreateUser dto) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		try {

			User model = new User();
			
			model.setFullName(dto.getFullName());
			model.setEmail(dto.getEmail());
			model.setCreatedDate(LocalDate.now());
			userRepo.save(model);
			
			if(dto.getRoles().size() > 0) {
				for(int i = 1; i<=dto.getRoles().size(); i++) {
					String id = String.valueOf(dto.getRoles().get(i).getId());
					roleRepo.findById(dto.getRoles().get(i).getId())
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with id #"+id+" not found"));
					roleRepo.assignRole(model.getId(), dto.getRoles().get(i).getId());
				}
			}

			resultMap.put("id", model.getId());
			resultMap.put("resultMessage", "User berhasil disimpan");
			resultMap.put("resultStatus", "OK");

			return resultMap;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public Map<String, Object> update(UpdateUser dto) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		try {

			User model = userRepo.findById(dto.getId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
			
			model.setFullName(dto.getFullName());
			model.setEmail(dto.getEmail());
			userRepo.save(model);
			
			roleRepo.deleteRolesByUserId(dto.getId());
			if(dto.getRoles().size() > 0) {
				for(int i = 0; i<dto.getRoles().size(); i++) {
					String id = String.valueOf(dto.getRoles().get(i).getId());
					roleRepo.findById(dto.getRoles().get(i).getId())
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with id #"+id+" not found"));
					roleRepo.assignRole(model.getId(), dto.getRoles().get(i).getId());
				}
			}

			resultMap.put("id", model.getId());
			resultMap.put("resultMessage", "User berhasil diubah");
			resultMap.put("resultStatus", "OK");

			return resultMap;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public Map<String, Object> delete(DeleteUser dto) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		try {

			User model = userRepo.findById(dto.getId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
			
			roleRepo.deleteRolesByUserId(dto.getId());
			userRepo.deleteById(dto.getId());

			resultMap.put("id", model.getId());
			resultMap.put("resultMessage", "User berhasil dihapus");
			resultMap.put("resultStatus", "OK");

			return resultMap;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}