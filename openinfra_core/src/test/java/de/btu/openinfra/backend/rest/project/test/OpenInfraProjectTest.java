package de.btu.openinfra.backend.rest.project.test;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import de.btu.openinfra.backend.db.daos.AttributeValueTypes;
import de.btu.openinfra.backend.db.pojos.PtFreeTextPojo;
import de.btu.openinfra.backend.db.pojos.project.AttributeValuePojo;
import de.btu.openinfra.backend.db.pojos.project.AttributeValueValuePojo;

/**
 * This JUnit class is used to test classes related to the project schema.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraProjectTest extends OpenInfraTest {

    public static final String ATTRIBUTE_VALUE_GET_PATH = TEST_PROJECT_PATH
            + "/attributevalues";
    public static final String ATTRIBUTE_VALUE_POST_PATH = TEST_PROJECT_PATH
            + "/attributevalues";
    public static final String PT_FREE_TEXT_PATH = TEST_PROJECT_PATH
            + "/ptfreetext";

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub
        rootLogin();
    }

    @Test
    public void getAttributeValueValue() {
        // attribute value id we want to get
        UUID entity = UUID.fromString("2174b9d1-785e-4b0f-9a7d-37649697eeec");
        // getting the attribute value object
        AttributeValuePojo avp = buildXml(ATTRIBUTE_VALUE_GET_PATH
                + "/" + entity)
                .get(AttributeValuePojo.class);
        assertEquals(entity, avp.getUuid());
    }

    @Test
    public void postAttributeValueValue() {
        UUID topicInstanceId =
                UUID.fromString("e01a9b89-a9dd-4a18-ac60-3bdf08dff492");
        UUID attributeTypeId =
                UUID.fromString("1f39489b-657d-4568-bbaa-ea191131814d");
        UUID attributeTypeToAttributeTypeGroupId =
                UUID.fromString("df853fd6-c1eb-48a0-91ab-9bf194ca4dd5");

        // get a attribute value pojo
        AttributeValuePojo avp = new AttributeValuePojo();

        // get a attribute value value pojo
        AttributeValueValuePojo avvp = new AttributeValueValuePojo();

        // get the new free text we want to insert
        PtFreeTextPojo pp = buildXml(PT_FREE_TEXT_PATH
                + "/bbb5af67-d5f9-4dba-9c74-fd5a8c6fbc8e")
                .get(PtFreeTextPojo.class);

        // set the data for the attribute value value pojo
        avvp.setTopicInstanceId(topicInstanceId);
        avvp.setValue(pp);
        avvp.setAttributeTypeToAttributeTypeGroupId(
                attributeTypeToAttributeTypeGroupId);

        // set the data for the attribute value pojo
        avp.setAttributeValueType(AttributeValueTypes.ATTRIBUTE_VALUE_VALUE);
        avp.setAttributeTypeId(attributeTypeId);
        avp.setAttributeValueValue(avvp);

        // send the new object
        Response res = buildJson(ATTRIBUTE_VALUE_POST_PATH)
                .post(Entity.json(avp));
        System.out.println(res);
        // test the result
        assertEquals(Status.OK.getStatusCode(), res.getStatus());
    }

    @Test
    public void postAttributeValueValueByword() {
        UUID topicInstanceId =
                UUID.fromString("fab4e287-3b8e-48fa-9282-63fc123dff47");
        UUID attributeTypeId =
                UUID.fromString("48f06316-f5e6-441a-a5e7-77291f3879e7");
        UUID attributeTypeToAttributeTypeGroupId =
                UUID.fromString("c05bbd28-a61f-4e98-9553-0dd599f755c8");

        // get a attribute value pojo
        AttributeValuePojo avp = new AttributeValuePojo();

        // get a attribute value value pojo
        AttributeValueValuePojo avvp = new AttributeValueValuePojo();

        // get the new free text we want to insert
        PtFreeTextPojo pp = buildXml(PT_FREE_TEXT_PATH
                + "/58d7f614-d7d1-4dd9-8116-506127736067")
                .get(PtFreeTextPojo.class);

        // set the data for the attribute value value pojo
        avvp.setTopicInstanceId(topicInstanceId);
        avvp.setValue(pp);
        avvp.setAttributeTypeToAttributeTypeGroupId(
                attributeTypeToAttributeTypeGroupId);

        // set the data for the attribute value pojo
        avp.setAttributeValueType(AttributeValueTypes.ATTRIBUTE_VALUE_VALUE);
        avp.setAttributeTypeId(attributeTypeId);
        avp.setAttributeValueValue(avvp);

        // send the new object
        Response res = buildJson(ATTRIBUTE_VALUE_POST_PATH)
                .post(Entity.json(avp));
        System.out.println(res);
        // test the result
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                res.getStatus());
    }

    @Test
    public void putAttributeValueValue() {
        // attribute value id that should be changed
        UUID entity = UUID.fromString("faaac2c3-07f5-4c9b-bdd1-ed65f146d14d");
        // getting the attribute value object we want to update
        AttributeValuePojo avp = buildXml(ATTRIBUTE_VALUE_GET_PATH
                + "/" + entity)
                .get(AttributeValuePojo.class);

        // get the new free text we want to insert
        PtFreeTextPojo pp = buildXml(PT_FREE_TEXT_PATH
                + "/d5a0b050-1a4c-4b7e-a412-8d408eb5ef2d")
                .get(PtFreeTextPojo.class);

        // set the new free text
        AttributeValueValuePojo avvp = avp.getAttributeValueValue();
        avvp.setValue(pp);
        avp.setAttributeValueValue(avvp);

        // send the new object
        Response res = buildJson(ATTRIBUTE_VALUE_POST_PATH + "/" + entity)
                .put(Entity.json(avp));
        System.out.println(res);
        // test the result
        assertEquals(Status.OK.getStatusCode(), res.getStatus());
    }

    @Test
    public void deleteAttributeValueValue() {
        // attribute value id that should be deleted
        UUID entity = UUID.fromString("48f0a8a4-06fe-4b9a-b4c5-cbbc9b2a16c7");

        Response res = buildJson(ATTRIBUTE_VALUE_POST_PATH + "/" + entity)
                .delete();
        System.out.println(res);
        assertEquals(Status.OK.getStatusCode(), res.getStatus());
    }

}
