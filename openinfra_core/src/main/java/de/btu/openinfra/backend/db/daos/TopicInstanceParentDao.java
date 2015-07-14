package de.btu.openinfra.backend.db.daos;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.TopicInstanceXTopicInstance;
import de.btu.openinfra.backend.db.pojos.TopicInstanceParentPojo;

public class TopicInstanceParentDao {
		
	private TopicInstanceAssociationDao ass;
	private TopicInstanceDao ti;

	public TopicInstanceParentDao(
			UUID currentProjectId, 
			OpenInfraSchemas schema) {
		ass = new TopicInstanceAssociationDao(currentProjectId, schema);
		ti = new TopicInstanceDao(currentProjectId, schema);
	}
	
	public List<TopicInstanceParentPojo> readParents(
			Locale locale, UUID self) {
		List<TopicInstanceParentPojo> parents = 
				new LinkedList<TopicInstanceParentPojo>();
		
		TopicInstanceXTopicInstance parent = ass.readParent(locale, self);
				
		while(true) {
			if(parent != null) {
				parents.add(new TopicInstanceParentPojo(
						ti.mapToPojo(locale, parent.getTopicInstance1Bean())));			
				parent = ass.readParent(
						locale, parent.getTopicInstance1Bean().getId());
			} else {
				break;
			}
		} // end while
		
		return parents;
	}

}
