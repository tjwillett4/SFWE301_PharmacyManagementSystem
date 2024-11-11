package BackEnd;

/*
 * ACCOUNT DATABASE
 * Note: This is of course not the database. that will be an encrypted text file. 
 * The system shall store all created accounts in a secure location. Individual account types shall have different access 
 * permissions to this database. (i.e. only certain roles may create accounts and customer roles may only read their own data.)
 * 
 * ACCOUNT HANDLING
 * The system shall allow Account Handling to serve as the communication between front end and back end to access the account database, returning restricted information based on the user/requester's permissions.
 * Shall communicate with the Account Database and Encryption. 
 * 
 * ACCOUNT DELETION
 * The system shall allow only certain user types to update account information. 
 * All user types can update their account's password while logged in, but only the pharmacy manager can update a user's account type.
 * 
 * ACCOUNT LOCK OUT:
 * The system shall lock any account that performs 5 unsuccessful login attempts. 
 * When an account is locked, no further login attempts are allowed until the account is manually unlocked by the pharmacy manager.
 * 
 * UNLOCK ACCOUNT: 
 * The system shall allow only the pharmacy manager to unlock an account that has been locked due to unsuccessful login attempts.
 * 
 * MANAGEMENT USER ACCOUNT CREATION:
 * The system shall allow for the pharmacy manager to add new user accounts to the system and assign them with a user type.
 */

//Handle account creation, deletion, and editing. 
//MUST verify accessing account role in the backend. 

public class AccountHandling {

}
