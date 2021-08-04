package com.imamkriswanto.test.prismalink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.imamkriswanto.test.prismalink.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	@Modifying
	@Query(value = "insert into user_role (user_id, role_id) values (:userId, :roleId)", nativeQuery = true)
	void assignRole(@Param("userId") long userId, @Param("roleId") long roleId);
	
	@Modifying
	@Query(value = "delete from user_role where role_id = :roleId", nativeQuery = true)
	void deleteRoles(@Param("roleId") long roleId);
	
	@Modifying
	@Query(value = "delete from user_role where user_id = :userId", nativeQuery = true)
	void deleteRolesByUserId(@Param("userId") long userId);

}