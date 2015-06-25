package de.btu.openinfra.backend.helper;

/**
 * This enumeration provides a list of potential URLS.
 *  
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public enum ImgUrls {
	
	IMG_BAALBEK {
		@Override
		public String toString() {
			return "http://www.tu-cottbus.de/cisar/baalbek/upload/foto/";
		}
	},
	
	MAP_BAALBEK {
		@Override
		public String toString() {
			return "http://www.tu-cottbus.de/cisar/baalbek/upload/plprev/";
		}		
	},
	
	MS_BAALBEK {
		@Override
		public String toString() {
			return "http://www.tu-cottbus.de/cisar/baalbek/upload/mpprev/";
		}		
	},
			
	IMG_PALATIN {
		@Override
		public String toString() {
			return "http://www.tu-cottbus.de/cisar/palatin/upload/foto/";
		}		
	},
			
	MAP_PALATIN {
		@Override
		public String toString() {
			return "http://www.tu-cottbus.de/cisar/palatin/upload/plprev/";
		}		
	}

}
