package models;

import java.util.List;

public class Group {

	private String id;
	private String name;
	private List<String> userIds;

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

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	
	/**
	 *  Group owner is first in list.
	 * @param userId
	 * @return true if the given userId is equal to the first element of the list.
	 */
	public boolean isOwner(String userId) {
		return userIds.get(0).equals(userId);
	}

}
