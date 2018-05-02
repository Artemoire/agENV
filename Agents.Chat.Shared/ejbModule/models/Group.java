package models;

import java.util.List;

public class Group {
	
	private String id;
	private String groupAdminId;
	private String name;
	private List<String> usersIds;
	
	public Group() {
		
	}

	public Group(String id, String groupAdminId, String name, List<String> usersIds) {
		super();
		this.id = id;
		this.groupAdminId = groupAdminId;
		this.name = name;
		this.usersIds = usersIds;
	}



	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	public List<String> getUsersIds() {
		return usersIds;
	}


	public void setUsersIds(List<String> usersIds) {
		this.usersIds = usersIds;
	}

	public String getGroupAdminId() {
		return groupAdminId;
	}

	public void setGroupAdminId(String groupAdminId) {
		this.groupAdminId = groupAdminId;
	}
	
	
}
