package de.btu.openinfra.backend;

/**
 * This enumeration defines the used property keys. The values are defined in
 * another enumeration. {@see OpenInfraDbProperties}
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public enum OpenInfraPropertyKeys {

	/**
	 * This variable defines the JDBC driver property.
	 */
	JDBC_DRIVER {
		@Override
		public String toString() {
			return "javax.persistence.jdbc.driver";
		}
	},
	
	/**
	 * This variable defines the user property.
	 */
	USER {
		@Override
		public String toString() {
			return "javax.persistence.jdbc.user";
		}
	},
	
	/**
	 * This variable defines the password property.
	 */
	PASSWORD {
		@Override
		public String toString() {
			return "javax.persistence.jdbc.password";
		}
	},
	
	/**
	 * This variable defines the used server.
	 */
	SERVER {
		@Override
		public String toString() {
			return "de.btu.openinfra.backend.db.jpa.server";
		}
	},	

	/**
	 * This variable defines the server port.
	 */
	PORT {
		@Override
		public String toString() {
			return "de.btu.openinfra.backend.db.jpa.port";
		}
	},
	
	/**
	 * This variable defines the database name.
	 */
	DB_NAME {
		@Override
		public String toString() {
			return "de.btu.openinfra.backend.db.jpa.dbname";
		}
	},

	/**
	 * This variable defines the URL property.
	 */
	URL {
		@Override
		public String toString() {
			return "javax.persistence.jdbc.url";
		}
	},
	
	/**
	 * This variable defines the default offset of the window functionality.
	 */
	DEFAULT_OFFSET {
	    @Override
	    public String toString() {
	        return "de.btu.openinfra.backend.rest.defaultOffset";
	    }
	},
	
	/**
     * This variable defines the default size of the window functionality.
     */
    DEFAULT_SIZE {
        @Override
        public String toString() {
            return "de.btu.openinfra.backend.rest.defaultSize";
        }
    },
	
    /**
     * This variable defines a max size for the window functionality.
     */
    MAX_SIZE {
        @Override
        public String toString() {
            return "de.btu.openinfra.backend.rest.maxSize";
        }
    },
    
    /**
     * This variable defines the default language key.
     */
    DEFAULT_LANGUAGE {
    	@Override
    	public String toString() {
    		return "de.btu.openinfra.backend.language.default";
    	}
    }

}
