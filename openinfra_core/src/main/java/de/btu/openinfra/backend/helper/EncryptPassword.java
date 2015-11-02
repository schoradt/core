package de.btu.openinfra.backend.helper;

import java.util.UUID;

import de.btu.openinfra.backend.db.rbac.OpenInfraRealm;

public class EncryptPassword {

	public static void main(String[] args) {

		for(int i = 0; i < 1; i++) {
//			System.out.println("--" + i + "--");
//			System.out.println(OpenInfraRealm.encrypt("root", UUID.fromString("ffdb477f-a0be-44e5-a377-c2d342eb0242")));
//			System.out.println(OpenInfraRealm.encrypt("max", UUID.fromString("eff3a947-6dfc-4bc5-a940-036c7bbc518d")));
//			System.out.println(OpenInfraRealm.encrypt("lieschen", UUID.fromString("07a506f3-0a2c-4b6d-a807-fdd8552315b2")));
//			System.out.println(OpenInfraRealm.encrypt("anonymous", UUID.fromString("966a91eb-328f-4df0-9e66-4dafed830088")));
//			System.out.println(OpenInfraRealm.encrypt("sysadmin", UUID.fromString("e9159662-5767-4efc-adcc-0f9ad9fa0bcb")));
//			System.out.println(OpenInfraRealm.encrypt("syseditor", UUID.fromString("81c23f40-8a3c-46ca-b561-7fc259113d77")));
//			System.out.println(OpenInfraRealm.encrypt("sysguest", UUID.fromString("581d9c8f-3a59-4d49-9aad-f06ad0aa6d16")));
//			System.out.println(OpenInfraRealm.encrypt("projectadmin_baal", UUID.fromString("ca0202b3-3675-46d2-8e59-4d4bd666489a")));
//			System.out.println(OpenInfraRealm.encrypt("projectguest_baal", UUID.fromString("2d38ac64-1242-490b-8569-18477bf18a9c")));
//			System.out.println(OpenInfraRealm.encrypt("projecteditor_baal", UUID.fromString("64573585-8546-429c-98dc-e1b379744d42")));
//			System.out.println(OpenInfraRealm.encrypt("siehstnix", UUID.fromString("db848094-0ead-4214-a850-d3c8cb23971c")));
			System.out.println(OpenInfraRealm.encrypt("tino", UUID.fromString("f05d2d8a-7dec-44dc-83b2-eb9e00b8fc03")));
			System.out.println(OpenInfraRealm.encrypt("frankh", UUID.fromString("9fa5e53e-95ff-4419-a618-6dc2c30ea82f")));
			System.out.println(OpenInfraRealm.encrypt("franks", UUID.fromString("afdffe4e-b391-4ca1-a9bd-018c530754d9")));
			System.out.println(OpenInfraRealm.encrypt("cornell", UUID.fromString("208bd57f-01ee-49b0-bdcb-23fb4ad685ff")));
			System.out.println(OpenInfraRealm.encrypt("alex", UUID.fromString("f3eaa8f9-5fc2-486b-9eab-e9a90d5281a6")));
			System.out.println(OpenInfraRealm.encrypt("philipp", UUID.fromString("3ffca723-6323-4181-84dd-d9f7e4fea5f2")));
			System.out.println(OpenInfraRealm.encrypt("hanna", UUID.fromString("dab1a46c-436f-4b80-a8ed-8672742a6aed")));
			System.out.println(OpenInfraRealm.encrypt("katja", UUID.fromString("1f53110a-5ccb-4713-8bac-8e49379bc688")));
		}
	}

}
