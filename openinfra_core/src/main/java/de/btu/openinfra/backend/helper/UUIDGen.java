package de.btu.openinfra.backend.helper;

import java.util.UUID;

/**
 * This class is only used for testing purposes. It generates a bunch of UUIDs.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class UUIDGen {
	
	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
			System.out.println(UUID.randomUUID());
		}
	}

}
