package quiz;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
	/* Instance Variables */
	//private Map<String, String> manager = new HashMap<String, String>();
	UserMySQLAccess userAccess = new UserMySQLAccess();
	
	/* CONSTANTS */
	private final String TESTACCOUNT1USERNAME = "Patrick";
	private final String TESTACCOUNT1PASSWORD = "1234";
	private final String TESTACCOUNT2USERNAME = "Molly";
	private final String TESTACCOUNT2PASSWORD = "FloPup";
	
	
	/* Constructor */
	public AccountManager() {
		// Do not call because it will add Patrick and Molly to the Database every time.
		//createTestAccounts();
	}
	
	// Stores the two accounts used for testing (Patrick and Molly)
	private void createTestAccounts() {
		createAccount(null, null, TESTACCOUNT1USERNAME, TESTACCOUNT1PASSWORD);
		createAccount(null, null, TESTACCOUNT2USERNAME, TESTACCOUNT2PASSWORD);
	}
	
	/*
	 * Returns true if the given string representing a password prompt entry 
	 * matches the password for the given account.
	 */
	public boolean passwordMatches(String account, String attempt) {
		byte[] attemptBytes = attempt.getBytes();
		byte[] resultBytes = Hash.computeHash(attemptBytes);
		String hashedAttempt = Hash.hexToString(resultBytes);
		return hashedAttempt.equals(userAccess.getHashedPassword(userAccess.getUser(account)));
	}
	
	/*
	 * Allows the user to create a new account with a unique username and password.
	 */
	public void createAccount(String firstName, String lastName, String userName, String password) {
		String hashedPassword = computePasswordHash(password);
		userAccess.addUser(firstName, lastName, userName, hashedPassword);
	}

	public static String computePasswordHash(String password) {
		byte[] passwordBytes = password.getBytes();
		byte[] resultBytes = Hash.computeHash(passwordBytes);
		return Hash.hexToString(resultBytes);
	}
	/* 
	 * Returns true if the given username is already present in the manager.
	 */
	public boolean accountExists(String name) {
		return userAccess.userNameInUse(name);
	}
	
}
