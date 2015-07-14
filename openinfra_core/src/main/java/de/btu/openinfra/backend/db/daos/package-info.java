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
 * There are two exceptions of the aforementioned rule. The following classes
 * do not implement the above-stated classes:
 * {@see TopicDao}
 * {@see TopicInstanceParentDao}
 * 
 * 
 * Moreover, there is a distinction between 'ordinary' DAO classes and 'view'
 * DAO classes. Ordinary DAO classes are used for both data access and data 
 * storage. View DAO classes are special view classes which must not be used 
 * for data storage {@see TopicInstanceDao}. 
 * 
 * In order to map locale objects of the form 'de-DE' which are used in the HTML
 * view you should use the forLanguageTag method located in the class 
 * {@see PtLocaleDao}.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
package de.btu.openinfra.backend.db.daos;