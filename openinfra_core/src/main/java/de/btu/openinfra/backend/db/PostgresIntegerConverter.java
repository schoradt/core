package de.btu.openinfra.backend.db;

import java.sql.SQLException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.postgresql.util.PGobject;

/**
 * This class will convert an Integer object into a PostgreSQL object and
 * vice versa. This converter is necessary to set or update the attribute 'xmin'
 * of the OpenInfraModelObject automatically after a persistent insert and
 * update (see method 'OpenInfraDao.createOrUpdate()').
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Converter
public class PostgresIntegerConverter implements
    AttributeConverter<Integer, PGobject> {

    @Override
    public PGobject convertToDatabaseColumn(Integer integer) {
        PGobject pgObject = new PGobject();
        // set the type of the pgObject to Integer
        pgObject.setType("integer");
        try {
         // set the value of the pgObject to the Integer value
            pgObject.setValue(integer.toString());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return pgObject;
    }

    @Override
    public Integer convertToEntityAttribute(PGobject pgObject) {
        return Integer.parseInt(pgObject.getValue());
    }

}
