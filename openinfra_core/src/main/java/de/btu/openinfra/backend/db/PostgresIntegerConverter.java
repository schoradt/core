package de.btu.openinfra.backend.db;

import java.sql.SQLException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.postgresql.util.PGobject;

@Converter
public class PostgresIntegerConverter implements AttributeConverter<Integer, PGobject> {

    public PostgresIntegerConverter() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public PGobject convertToDatabaseColumn(Integer arg0) {
        PGobject pgObject = new PGobject();
        pgObject.setType("integer");
        try {
            pgObject.setValue(arg0.toString());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return pgObject;
    }

    @Override
    public Integer convertToEntityAttribute(PGobject arg0) {
        return Integer.parseInt(arg0.getValue());
    }

}
