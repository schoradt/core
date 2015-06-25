/**
 * This package implements all DAO classes which are used to access the JPA 
 * persistence layer (database). All DAO classes should derive the abstract
 * {@see OpenInfraDao} class which is used to provide a generalized method for 
 * instantiating these DAO objects.
 * 
 * Furthermore, there exists a second abstract class {@see OpenInfraValueDao}
 * which ads the functionality to retrieve a specific POJO object from database
 * by a specified value.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
package de.btu.openinfra.backend.db.daos.meta;