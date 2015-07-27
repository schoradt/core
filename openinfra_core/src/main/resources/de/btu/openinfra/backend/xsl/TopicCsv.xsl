<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output method="text" encoding="utf-8"/>
 <xsl:variable name="delimiter" select="';'"/>
 
 <xsl:template match="/">
	<xsl:text>Attributtypgruppe</xsl:text><xsl:value-of select="$delimiter"/>
 	<xsl:apply-templates select="topicPojo/attributeTypeGroupsToValues"/>
	<xsl:text>&#10;</xsl:text>
	<xsl:apply-templates 
	select="topicPojo/attributeTypeGroupsToValues/attributeTypesToValues/attributeValue"/>
 </xsl:template>
 
 <xsl:template match="attributeTypeGroupsToValues">
	<xsl:apply-templates select="attributeTypesToValues/attributeType"/>
 </xsl:template>
 
 <xsl:template match="attributeType">
	<xsl:value-of 
               	select="names/localizedStrings/characterString"/>
				<xsl:value-of select="$delimiter"/>
 </xsl:template>
 
 <xsl:template match="attributeValue">
	   <xsl:choose>
   		<xsl:when test="attributeValueGeom">
   			<xsl:value-of select="attributeValueGeom/geom"/>
   		</xsl:when>
   		<xsl:when test="attributeValueValue">
   			<xsl:value-of 
   			select="attributeValueValue/value/localizedStrings/characterString"/>
   		</xsl:when>
   	</xsl:choose>
	<xsl:value-of select="$delimiter"/>
 </xsl:template>
 
 <xsl:template match="text()">
 </xsl:template>
  
</xsl:stylesheet>