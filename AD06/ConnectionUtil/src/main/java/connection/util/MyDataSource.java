package connection.util;

import java.io.Serializable;

public class MyDataSource implements Serializable {

	// Generated serial version ID
	private static final long serialVersionUID = -4893149409186041419L;
	
	private String user = null;
	private String pwd = null;
	private String url = null;
	private String driver = null;

	public MyDataSource() {
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

}
