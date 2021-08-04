package com.imamkriswanto.test.prismalink.repository;


import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.imamkriswanto.test.prismalink.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Page<User> findAllByCreatedDateBetween(LocalDate createdDateFrom, LocalDate createdDateTo, Pageable pageable);

}
