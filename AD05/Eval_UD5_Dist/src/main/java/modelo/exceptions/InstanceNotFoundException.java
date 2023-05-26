package modelo.exceptions;

/**
 *
 * @author maria
 */
public class InstanceNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object key;
	private String className;

	private static final String DEFAULT_MSG = "Instance not found";

	public InstanceNotFoundException(Object key, String className) {

		super(DEFAULT_MSG + " (key = '" + key + "' - className = '" + className + "')");
		this.key = key;
		this.className = className;

	}

	public Object getKey() {
		return key;
	}

	public String getClassName() {
		return className;
	}
}
