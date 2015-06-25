package de.btu.openinfra.backend.db;

/**
 * This enumeration defines the specific database variables. Keys are defined
 * in another enumeration. {@see OpenInfraPropertyKeys}
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public enum OpenInfraPropertyValues {
	
	/**
	 * This variable defines the specific JDBC driver.
	 */
	JDBC_DRIVER {
		@Override
		public String toString() {
			return "org.postgresql.Driver";
		}
	},

	/**
	 * This variable defines the connection URL which should be formatted via
	 * String.format()
	 */
	URL {
		@Override
		public String toString() {
			return "jdbc:postgresql://%s:%s/%s?";
		}
	},
	
	/**
	 * The definition of the search path for the PostgreSQL data base.
	 */
	SEARCH_PATH {
		@Override
		public String toString() {
			return "public,constraints";
		}
	},
	
	/**
	 * The static search path for the system schema.
	 */
	SYSTEM_SEARCH_PATH {
		@Override
		public String toString() {
			return "system";
		}
	},
	
	/**
	 * The static search path for the meta data schema
	 */
	META_DATA_SEARCH_PATH {
		@Override
		public String toString() {
			return "meta_data";
		}
	}

}
