package de.btu.openinfra.backend.helper;

import java.util.UUID;

import de.btu.openinfra.backend.db.rbac.OpenInfraRealm;



public class TestClass {
	
	public static void main(String[] args) {
		
//		PtLocale l = new PtLocaleDao(
//				UUID.fromString("fd27a347-4e33-4ed7-aebc-eeff6dbf1054"), 
//				OpenInfraSchemas.PROJECT).read(PtLocaleDao.forLanguageTag("de-DE"));
//		System.out.println(l.getId() + " " + l.getCountryCode().getCountryCode());
		
//		List<TopicCharacteristicPojo> list = new TopicCharacteristicDao(
//				UUID.fromString("fd27a347-4e33-4ed7-aebc-eeff6dbf1054"), 
//				OpenInfraSchemas.PROJECT).read(
//						PtLocaleDao.forLanguageTag("de-DE"), "%und%");
//		
//		System.out.println(list.size());
//		for(TopicCharacteristicPojo tcp : list) {
//			System.out.println(tcp.getDescriptions().getLocalizedStrings().get(0).getCharacterString());
//		}
//		
//		List<TopicInstancePojo> list = new TopicInstanceDao(
//				UUID.fromString("fd27a347-4e33-4ed7-aebc-eeff6dbf1054"), 
//				OpenInfraSchemas.PROJECTS).read(
//						PtLocaleDao.forLanguageTag("de-DE"), 
//						UUID.fromString("50d4cb6f-46f1-4422-954c-4c3ec371f063"),
//						"%afsiyeh%",
//						0,
//						30);

//		System.out.println(UUID.fromString(null));
		
//		List<TopicCharacteristicPojo> list = new TopicCharacteristicDao(
//		UUID.fromString("fd27a347-4e33-4ed7-aebc-eeff6dbf1054"), 
//		OpenInfraSchemas.PROJECT).read(
//				PtLocaleDao.forLanguageTag("de-DE"), "%und%");
//		
//		System.out.println(list.size());
//		for(TopicCharacteristicPojo tcp : list) {
//			System.out.println(tcp.getDescriptions().getLocalizedStrings().get(0).getCharacterString());
//		}

//		
//		MultiplicityDao dao = new MultiplicityDao(UUID.fromString("fd27a347-4e33-4ed7-aebc-eeff6dbf1054"), OpenInfraSchemas.PROJECTS);
//		MultiplicityPojo m = dao.read(null, UUID.fromString("09d832bc-2ea8-4874-8f60-9f5878cb89e3"));
//		
//		m.setMax(new Integer(5));
//		System.out.println(m.getTrid());
//		
//		dao.createOrUpdate(m);
		
		// encrypt the raw passwords
		for(int i = 0; i < 3; i++) {
			System.out.println("--" + i + "--");
			System.out.println(OpenInfraRealm.encrypt("root", UUID.fromString("ffdb477f-a0be-44e5-a377-c2d342eb0242")));
			System.out.println(OpenInfraRealm.encrypt("max", UUID.fromString("eff3a947-6dfc-4bc5-a940-036c7bbc518d")));
			System.out.println(OpenInfraRealm.encrypt("lieschen", UUID.fromString("07a506f3-0a2c-4b6d-a807-fdd8552315b2")));
			System.out.println(OpenInfraRealm.encrypt("anonymous", UUID.fromString("966a91eb-328f-4df0-9e66-4dafed830088")));
		}
		
	}

}
