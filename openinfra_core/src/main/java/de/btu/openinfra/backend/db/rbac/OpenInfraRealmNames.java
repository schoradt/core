package de.btu.openinfra.backend.db.rbac;

/**
 * This enumeration might help to identify the information which is stored in
 * it's principal collection. However, this is currently not used very
 * intensively. Thus, this might be removed in the future.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public enum OpenInfraRealmNames {
	
	/**
	 * This variable defines the login value which might be the user name of
	 * the current subject.
	 */
	LOGIN,
	
	/**
	 * This variable defines the identifier of the current subject. 
	 */
	ID
	

}
