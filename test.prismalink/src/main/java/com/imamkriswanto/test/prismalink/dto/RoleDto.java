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
public class RoleDto {
	
	 private long id;
	 private String roleName;
	 
	 
	 	@Getter
		@Setter
		public static class DetailRole{
	 		
	 		private long id;
	 		
		}
	 
	 	@Getter
		@Setter
		public static class CreateRole{
	 		
	 		private String roleName;
	 		
		}

	 	@Getter
		@Setter
		public static class UpdateRole{
	 		
	 		private long id;
	 		private String roleName;
	 		
		}
	 	
	 	@Getter
		@Setter
		public static class AssignRoleToUser{
	 		
	 		private long userId;
	 		private long roleId;
	 		
		}
	 	
	 	@Getter
		@Setter
		public static class DeleteRole{
	 		
	 		private long id;
	 		
		}
}




