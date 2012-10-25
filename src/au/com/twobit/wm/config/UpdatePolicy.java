package au.com.twobit.wm.config;

public enum UpdatePolicy {
	REALTIME,
	CACHE_EXPIRE_ON_UPDATE,
	CACHE_EXPIRE_ON_UPDATE_AND_TIMEOUT(10);
	
	// timeout value in Seconds
	private int timeout = 0;
	
	private UpdatePolicy() {
	}
	
	private UpdatePolicy(int timeout) {
		this.timeout = timeout;
	}
	
	/** Gets the timeout for updates
	 * 
	 * @return int Returns the timeout value for cache to expire (in seconds)
	 */
	public int getTimeout() {
		return timeout;
	}
}
