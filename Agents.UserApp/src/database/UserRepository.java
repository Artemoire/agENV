package database;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class UserRepository {

	@EJB
	private UserAppDbContext dbContext;

	public UserAppDbContext getDbContext() {
		return dbContext;
	}

	public void setDbContext(UserAppDbContext dbContext) {
		this.dbContext = dbContext;
	}

}
