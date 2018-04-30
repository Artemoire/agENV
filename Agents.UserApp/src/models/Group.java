package models;

import java.util.List;

public class Group {
	
	private Long id;
	private Long groupAdminId;
	private String name;
	private List<Long> usersIds;
	
	public Group() {
		
	}

	public Group(Long id, Long groupAdminId, String name, List<Long> usersIds) {
		super();
		this.id = id;
		this.groupAdminId = groupAdminId;
		this.name = name;
		this.usersIds = usersIds;
	}



	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	public List<Long> getUsersIds() {
		return usersIds;
	}


	public void setUsersIds(List<Long> usersIds) {
		this.usersIds = usersIds;
	}

	public Long getGroupAdminId() {
		return groupAdminId;
	}

	public void setGroupAdminId(Long groupAdminId) {
		this.groupAdminId = groupAdminId;
	}
	
	
}
