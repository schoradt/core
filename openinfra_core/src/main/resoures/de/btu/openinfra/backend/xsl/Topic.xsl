<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.1" 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:barcode="org.krysalis.barcode4j.xalan.BarcodeExt" xmlns:common="http://exslt.org/common"
                xmlns:xalan="http://xml.apache.org" exclude-result-prefixes="barcode common xalan java"
                xmlns:java="http://xml.apache.org/xslt/java">

<!-- http://www.torsten-horn.de/techdocs/java-xsl.htm -->

   <!-- Attribut-Sets fuer Tabellenzellen -->
   <xsl:attribute-set name="cell-style">
      <xsl:attribute name="border-width">0.5pt</xsl:attribute>
      <xsl:attribute name="border-style">solid</xsl:attribute>
      <xsl:attribute name="border-color">black</xsl:attribute>
   </xsl:attribute-set>
   <xsl:attribute-set name="block-style">
      <xsl:attribute name="font-size">  10pt</xsl:attribute>
      <xsl:attribute name="line-height">15pt</xsl:attribute>
      <xsl:attribute name="start-indent">1mm</xsl:attribute>
      <xsl:attribute name="end-indent">  1mm</xsl:attribute>
   </xsl:attribute-set>
   <xsl:attribute-set name="block-style-head">
      <xsl:attribute name="font-size">  12pt</xsl:attribute>
      <xsl:attribute name="line-height">15pt</xsl:attribute>
      <xsl:attribute name="start-indent">1mm</xsl:attribute>
      <xsl:attribute name="end-indent">  1mm</xsl:attribute>
   </xsl:attribute-set>
                
                
   <xsl:template match="/">
      <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
         <fo:layout-master-set>
            <fo:simple-page-master master-name="DIN-A4"
                  page-height="29.7cm" page-width="21cm"
                  margin-top="2cm"     margin-bottom="2cm"
                  margin-left="2.5cm"  margin-right="2.5cm">
               <fo:region-body
                  margin-top="1.5cm" margin-bottom="1.8cm"
                  margin-left="2cm"  margin-right="2.5cm"/>
               <fo:region-before region-name="header" extent="1.3cm"/>
               <fo:region-after  region-name="footer" extent="1.5cm"/>
               <fo:region-start  region-name="left"   extent="1cm"/>
               <fo:region-end    region-name="right"  extent="2cm"/>
            </fo:simple-page-master>
         </fo:layout-master-set>
         <fo:page-sequence master-reference="DIN-A4">
            <fo:static-content flow-name="header">
               <fo:block font-size="14pt" text-align="center">
               	 <!-- Name of the topic instance -->
                 <xsl:value-of 
                 select="topicPojo/topicInstance/topicCharacteristic/topic/names/localizedStrings/characterString"/>
               </fo:block>
            </fo:static-content>
            <fo:static-content flow-name="footer">
               <fo:block text-align="center">
                  Seite <fo:page-number/> von <fo:page-number-citation ref-id="LastPage"/>
                  <xsl:text> </xsl:text>
                  <xsl:value-of select="java:format(java:java.text.SimpleDateFormat.new
                                        ('MMMM dd, yyyy, hh:mm:ss a (zz)'), java:java.util.Date.new())"/>
               </fo:block>
            </fo:static-content>
            <fo:flow flow-name="xsl-region-body">
               
               <!-- ### ### Apply templates ### ### -->
               <xsl:apply-templates/>
               
               <fo:block id="LastPage"/>
            </fo:flow>
         </fo:page-sequence>
      </fo:root>
   </xsl:template>
      
   <xsl:template match="attributeTypeGroupsToValues">
       <!-- Name of the attribute type group -->
       <fo:block white-space-collapse="false" 
                 white-space-treatment="preserve"
                 font-size="0pt" line-height="10pt">.
       </fo:block>
	   <fo:block font-size="14pt" text-align="left">
	      <xsl:value-of 
	      select="attributeTypeGroup/names/localizedStrings/characterString"/>
	   </fo:block>
   
      <fo:table border-style="solid" table-layout="fixed" width="100%">
         <fo:table-column column-width="5cm"/>
         <fo:table-column column-width="7cm"/>
         <fo:table-header>
            <xsl:call-template name="table-head"/>
         </fo:table-header>
         <fo:table-body>
            <xsl:apply-templates select="attributeTypesToValues"/>
         </fo:table-body>
      </fo:table>
   </xsl:template>
   
   <xsl:template match="attributeTypesToValues">
      <fo:table-row>
         <fo:table-cell xsl:use-attribute-sets="cell-style">
            <fo:block xsl:use-attribute-sets="block-style">
               <fo:inline font-weight="bold">
               <xsl:value-of 
               	select="attributeType/names/localizedStrings/characterString"/>
               </fo:inline>
            </fo:block>
         </fo:table-cell>
         <fo:table-cell xsl:use-attribute-sets="cell-style">
            <fo:block xsl:use-attribute-sets="block-style">
             <xsl:apply-templates select="attributeValue"/>
            </fo:block>
         </fo:table-cell>
      </fo:table-row>
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
   </xsl:template>
   
   <!-- Tabellenkopf -->
   <xsl:template name="table-head">
      <fo:table-row>
         <fo:table-cell xsl:use-attribute-sets="cell-style">
            <fo:block xsl:use-attribute-sets="block-style-head"
                      text-align="center">Attribut</fo:block>
         </fo:table-cell>
         <fo:table-cell xsl:use-attribute-sets="cell-style">
            <fo:block xsl:use-attribute-sets="block-style-head"
                      text-align="center">Wert</fo:block>
         </fo:table-cell>
      </fo:table-row>
   </xsl:template>
   
</xsl:stylesheet>