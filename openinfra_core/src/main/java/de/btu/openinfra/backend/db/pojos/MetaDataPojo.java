package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MetaDataPojo extends OpenInfraPojo {
    
    private String tableName;
    private String pkColumn;
    private String data;
    
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public String getPkColumn() {
        return pkColumn;
    }
    
    public void setPkColumn(String pkColumn) {
        this.pkColumn = pkColumn;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
}
