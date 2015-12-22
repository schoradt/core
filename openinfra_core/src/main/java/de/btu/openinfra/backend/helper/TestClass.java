package de.btu.openinfra.backend.helper;

import de.btu.openinfra.backend.db.pojos.solr.SolrResultDbPojo;
import de.btu.openinfra.backend.db.pojos.solr.SolrResultDocPojo;
import de.btu.openinfra.backend.db.pojos.solr.SolrResultPojo;
import de.btu.openinfra.backend.db.pojos.solr.SolrSearchPojo;
import de.btu.openinfra.backend.solr.SolrSearcher;





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




//	    OrderByPojo p = OrderByDao.read(OpenInfraSchemas.PROJECTS, "TopicCharacteristic");
//	    for (OpenInfraOrderByEnum string : p.getOrderByTypes()) {
//            System.out.println(string.toString());
//        }
//
//        OrderByNamesPojo np = OrderByDao.getNames(OpenInfraSchemas.SYSTEM);
//        for (String string : np.getClassNames()) {
//            System.out.println(string);
//        }

	    SolrSearchPojo searchPojo = new SolrSearchPojo();
	    searchPojo.setRawSolrQuery("Tino");
	    SolrResultPojo r = new SolrSearcher().search(searchPojo, 0, Integer.MAX_VALUE);

	    System.out.println("time: " + r.getElapsedTime());
	    System.out.println("count: " + r.getResultCount());
	    for (SolrResultDbPojo db : r.getDatabaseResult()) {
	        System.out.println(db.getTopicInstanceId() + " -- " + db.getHighlight());
	    }

	    System.out.println("----------------------------");

	    for (SolrResultDocPojo doc : r.getDocumentResult()) {
            System.out.println(doc.getHashCode() + " -- " + doc.getHighlight());
        }


//	    SolrIndexer si = new SolrIndexer();
//	    SolrIndexPojo p = new SolrIndexPojo();
//	    List<UUID> lst = new ArrayList<UUID>();
//	    lst.add(UUID.fromString("e7d42bff-4e40-4f43-9d1b-1dc5a190cd75"));
//	    p.setProjects(lst);
//	    si.indexAll(true);



	}



}
