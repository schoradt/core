package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the attribute_type database table.
 *
 */
@Entity
@Table(name="attribute_type")
@NamedQueries({
	@NamedQuery(name="AttributeType.findAll",
		query="SELECT a FROM AttributeType a ORDER BY a.id"),
	@NamedQuery(name="AttributeType.findAllByLocale",
		query="SELECT a "
	            + "FROM AttributeType a "
	            + "INNER JOIN a.ptFreeText1.localizedCharacterStrings l1 "
	            + "INNER JOIN a.ptFreeText2.localizedCharacterStrings l2 "
	            + "INNER JOIN a.valueList.ptFreeText1.localizedCharacterStrings vl1 "
	            + "INNER JOIN a.valueList.ptFreeText2.localizedCharacterStrings vl2 "
	            + "WHERE l1.ptLocale = :locale "
	            + "AND l2.ptLocale = :locale "
	            + "AND vl1.ptLocale = :locale "
	            + "AND vl2.ptLocale = :locale "),

	@NamedQuery(name="AttributeType.count",
		query="SELECT COUNT(a) FROM AttributeType a"),
	@NamedQuery(name="AttributeType.findByAttributeTypeGroup",
		query="SELECT a "
				+ "FROM AttributeType a "
				+ "INNER JOIN a.attributeTypeToAttributeTypeGroups g "
				+ "WHERE g.attributeTypeGroup = :value"),
	@NamedQuery(name="AttributeType.countByAttributeTypeGroup",
		query="SELECT COUNT(a) "
				+ "FROM AttributeTypeToAttributeTypeGroup a "
				+ "WHERE a.attributeTypeGroup = :value"),
	@NamedQuery(name="AttributeType.findByDataType",
	    query="SELECT DISTINCT a "
	            + "FROM AttributeType a "
	            + "JOIN a.valueListValue1.ptFreeText2 ptf "
	            + "WHERE ptf.id = ANY ( "
                    + "SELECT l.ptFreeText.id "
                    + "FROM LocalizedCharacterString l "
                    + "WHERE l.ptLocale = :ptl "
                    + "AND l.freeText = :dataType)")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="AttributeType.findAllByLocaleAndOrder",
            query="SELECT at.*, at.xmin "
                  + "FROM attribute_type AS at "
                  + "LEFT OUTER JOIN ( "
                    + "SELECT * "
                    + "FROM attribute_type AS a, localized_character_string AS b "
                    + "WHERE a.%s = b.pt_free_text_id "
                    + "AND b.pt_locale_id = cast(? as uuid) ) AS sq "
                    + "ON (at.id = sq.id) "
                    + "ORDER BY free_text ",
            resultClass=AttributeType.class)
})
public class AttributeType extends OpenInfraModelObject
    implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to PtFreeText
	@ManyToOne
	@JoinColumn(name="description")
	private PtFreeText ptFreeText1;

	//bi-directional many-to-one association to PtFreeText
	@ManyToOne
	@JoinColumn(name="name")
	private PtFreeText ptFreeText2;

	//bi-directional many-to-one association to ValueList
	@ManyToOne
	@JoinColumn(name="domain")
	private ValueList valueList;

	//bi-directional many-to-one association to ValueListValue
	@ManyToOne
	@JoinColumn(name="data_type")
	private ValueListValue valueListValue1;

	//bi-directional many-to-one association to ValueListValue
	@ManyToOne
	@JoinColumn(name="unit")
	private ValueListValue valueListValue2;

	//bi-directional many-to-one association to AttributeTypeToAttributeTypeGroup
	@OneToMany(mappedBy="attributeType")
	private List<AttributeTypeToAttributeTypeGroup> attributeTypeToAttributeTypeGroups;

	//bi-directional many-to-one association to AttributeTypeXAttributeType
	@OneToMany(mappedBy="attributeType1Bean")
	private List<AttributeTypeXAttributeType> attributeTypeXAttributeTypes1;

	//bi-directional many-to-one association to AttributeTypeXAttributeType
	@OneToMany(mappedBy="attributeType2Bean")
	private List<AttributeTypeXAttributeType> attributeTypeXAttributeTypes2;

	public AttributeType() {
	}

	public PtFreeText getPtFreeText1() {
		return this.ptFreeText1;
	}

	public void setPtFreeText1(PtFreeText ptFreeText1) {
		this.ptFreeText1 = ptFreeText1;
	}

	public PtFreeText getPtFreeText2() {
		return this.ptFreeText2;
	}

	public void setPtFreeText2(PtFreeText ptFreeText2) {
		this.ptFreeText2 = ptFreeText2;
	}

	public ValueList getValueList() {
		return this.valueList;
	}

	public void setValueList(ValueList valueList) {
		this.valueList = valueList;
	}

	public ValueListValue getValueListValue1() {
		return this.valueListValue1;
	}

	public void setValueListValue1(ValueListValue valueListValue1) {
		this.valueListValue1 = valueListValue1;
	}

	public ValueListValue getValueListValue2() {
		return this.valueListValue2;
	}

	public void setValueListValue2(ValueListValue valueListValue2) {
		this.valueListValue2 = valueListValue2;
	}

	public List<AttributeTypeToAttributeTypeGroup> getAttributeTypeToAttributeTypeGroups() {
		return this.attributeTypeToAttributeTypeGroups;
	}

	public void setAttributeTypeToAttributeTypeGroups(List<AttributeTypeToAttributeTypeGroup> attributeTypeToAttributeTypeGroups) {
		this.attributeTypeToAttributeTypeGroups = attributeTypeToAttributeTypeGroups;
	}

	public AttributeTypeToAttributeTypeGroup addAttributeTypeToAttributeTypeGroup(AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup) {
		getAttributeTypeToAttributeTypeGroups().add(attributeTypeToAttributeTypeGroup);
		attributeTypeToAttributeTypeGroup.setAttributeType(this);

		return attributeTypeToAttributeTypeGroup;
	}

	public AttributeTypeToAttributeTypeGroup removeAttributeTypeToAttributeTypeGroup(AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup) {
		getAttributeTypeToAttributeTypeGroups().remove(attributeTypeToAttributeTypeGroup);
		attributeTypeToAttributeTypeGroup.setAttributeType(null);

		return attributeTypeToAttributeTypeGroup;
	}

	public List<AttributeTypeXAttributeType> getAttributeTypeXAttributeTypes1() {
		return this.attributeTypeXAttributeTypes1;
	}

	public void setAttributeTypeXAttributeTypes1(List<AttributeTypeXAttributeType> attributeTypeXAttributeTypes1) {
		this.attributeTypeXAttributeTypes1 = attributeTypeXAttributeTypes1;
	}

	public AttributeTypeXAttributeType addAttributeTypeXAttributeTypes1(AttributeTypeXAttributeType attributeTypeXAttributeTypes1) {
		getAttributeTypeXAttributeTypes1().add(attributeTypeXAttributeTypes1);
		attributeTypeXAttributeTypes1.setAttributeType1Bean(this);

		return attributeTypeXAttributeTypes1;
	}

	public AttributeTypeXAttributeType removeAttributeTypeXAttributeTypes1(AttributeTypeXAttributeType attributeTypeXAttributeTypes1) {
		getAttributeTypeXAttributeTypes1().remove(attributeTypeXAttributeTypes1);
		attributeTypeXAttributeTypes1.setAttributeType1Bean(null);

		return attributeTypeXAttributeTypes1;
	}

	public List<AttributeTypeXAttributeType> getAttributeTypeXAttributeTypes2() {
		return this.attributeTypeXAttributeTypes2;
	}

	public void setAttributeTypeXAttributeTypes2(List<AttributeTypeXAttributeType> attributeTypeXAttributeTypes2) {
		this.attributeTypeXAttributeTypes2 = attributeTypeXAttributeTypes2;
	}

	public AttributeTypeXAttributeType addAttributeTypeXAttributeTypes2(AttributeTypeXAttributeType attributeTypeXAttributeTypes2) {
		getAttributeTypeXAttributeTypes2().add(attributeTypeXAttributeTypes2);
		attributeTypeXAttributeTypes2.setAttributeType2Bean(this);

		return attributeTypeXAttributeTypes2;
	}

	public AttributeTypeXAttributeType removeAttributeTypeXAttributeTypes2(AttributeTypeXAttributeType attributeTypeXAttributeTypes2) {
		getAttributeTypeXAttributeTypes2().remove(attributeTypeXAttributeTypes2);
		attributeTypeXAttributeTypes2.setAttributeType2Bean(null);

		return attributeTypeXAttributeTypes2;
	}

}