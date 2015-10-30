package de.btu.openinfra.backend.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import de.btu.openinfra.backend.db.PojoPrimer;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

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



		System.out.println(System.getProperty("os.name"));

		System.out.println(PojoPrimer.class.getPackage().getName());

		Reflections reflection = new Reflections(new ConfigurationBuilder().
		        addUrls(ClasspathHelper.forPackage("de.btu.openinfra.backend.db.pojos")).
		        filterInputsBy(new FilterBuilder().
		            include("de\\.btu\\.openinfra\\.backend\\.db\\.pojos\\..*\\.class").
		            exclude("de\\.btu\\.openinfra\\.backend\\.db\\.pojos\\..*\\..*\\.class")));

		List<String> classes = new ArrayList<>();

		for(Class<? extends OpenInfraPojo> cls : reflection.getSubTypesOf(OpenInfraPojo.class)) {
		    classes.add(cls.getSimpleName());

		}

		Collections.sort(classes);

		System.out.println(classes.size());
		for(String className : classes) {
		    System.out.println(className);
		}
	}

}
