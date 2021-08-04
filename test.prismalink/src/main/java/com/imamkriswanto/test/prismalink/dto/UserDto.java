package com.imamkriswanto.test.prismalink.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.imamkriswanto.test.prismalink.model.Role;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
	
	 private long id;
	 private String fullName;
	 private String email;
	 private LocalDate createdDate;
	 private List<Role> roles;
	 
	 	@Getter
		@Setter
		public static class Roles{
	 		
	 		private long id;
	 		
		}
	 
	 	@Getter
		@Setter
		public static class CreatedDateFilter{
	 		
	 		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	 	    @JsonDeserialize(using = LocalDateDeserializer.class)
	 		private LocalDate createdDateFrom;
	 		
	 		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	 	    @JsonDeserialize(using = LocalDateDeserializer.class)
	 		private LocalDate createdDateTo;
	 		
		}
	 	 	
	 	@Getter
		@Setter
		public static class DetailUser{
	 		
	 		private long id;
	 		
		}
	 	
	 	@Getter
		@Setter
		public static class CreateUser{
	 		
	 		private String fullName;
	 		private String email;
	 		private List<Roles> roles;
	 		
		}

	 	@Getter
		@Setter
		public static class UpdateUser{
	 		
	 		private long id;
	 		private String fullName;
	 		private String email;
	 		private List<Roles> roles;
	 		
		}
	 	
	 	@Getter
		@Setter
		public static class DeleteUser{
	 		
	 		private long id;
	 		
		}
}




