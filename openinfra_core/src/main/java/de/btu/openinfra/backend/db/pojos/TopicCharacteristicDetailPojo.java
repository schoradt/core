package de.btu.openinfra.backend.db.pojos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Read the fucking Quellltext.. it'll never {d|l}ie!
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@XmlRootElement
public class TopicCharacteristicDetailPojo {

	private List<AttributeTypeGroupToAttributeTypes>
		attributeTypeGroupToAttributeTypes;

	public List<AttributeTypeGroupToAttributeTypes> getAttributeTypeGroupToAttributeTypes() {
		return attributeTypeGroupToAttributeTypes;
	}

	public void setAttributeTypeGroupToAttributeTypes(
			List<AttributeTypeGroupToAttributeTypes> attributeTypeGroupToAttributeTypes) {
		this.attributeTypeGroupToAttributeTypes = attributeTypeGroupToAttributeTypes;
	}

}
