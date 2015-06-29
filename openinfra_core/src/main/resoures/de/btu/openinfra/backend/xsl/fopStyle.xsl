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
                  Angestelltenliste
               </fo:block>
            </fo:static-content>
            <fo:static-content flow-name="footer">
               <fo:block text-align="center">
                  Seite <fo:page-number/> von <fo:page-number-citation ref-id="LastPage"/>
                  <xsl:text> </xsl:text>
                  <xsl:value-of select="java:format(java:java.text.SimpleDateFormat.new
                                        ('MMMM d, yyyy, h:mm:ss a (zz)'), java:java.util.Date.new())"/>
               </fo:block>
            </fo:static-content>
            <fo:flow flow-name="xsl-region-body">
	            <fo:block>
				   <fo:inline>
		      			<fo:external-graphic src="C:/Users/Tino/Workspace_OpenInfRA_2_0/JerseyTutorial/src/main/webapp/img/header-banner_update.jpg"
		      			content-height="scale-to-fit" height="15mm"  content-width="140mm" scaling="non-uniform"/>    
		   			</fo:inline>
				</fo:block>
               <xsl:apply-templates/>
               
               <xsl:for-each select="//employee/bild">
                <fo:block padding="10mm 10mm 10mm 10mm">
                <xsl:variable name="imgSrc">
                	<xsl:value-of select="text()"/>
                </xsl:variable>
                bild <xsl:value-of select="$imgSrc"/>
                <fo:external-graphic src="{$imgSrc}" content-height="scale-to-fit" height="15mm"  content-width="140mm" scaling="non-uniform"/>         
                </fo:block>
               </xsl:for-each>
               
               <fo:block id="LastPage"/>
            </fo:flow>
         </fo:page-sequence>
      </fo:root>
   </xsl:template>
   
   <!-- Tabellenkopf -->
   <xsl:template name="table-head">
      <fo:table-row>
         <fo:table-cell xsl:use-attribute-sets="cell-style">
            <fo:block xsl:use-attribute-sets="block-style"
                      text-align="center">Vorname</fo:block>
         </fo:table-cell>
         <fo:table-cell xsl:use-attribute-sets="cell-style">
            <fo:block xsl:use-attribute-sets="block-style"
                      text-align="center">Nachname</fo:block>
         </fo:table-cell>
         <fo:table-cell xsl:use-attribute-sets="cell-style">
            <fo:block xsl:use-attribute-sets="block-style"
                      text-align="center">Email</fo:block>
         </fo:table-cell>
         <fo:table-cell xsl:use-attribute-sets="cell-style">
            <fo:block xsl:use-attribute-sets="block-style"
                      text-align="center">Department</fo:block>
         </fo:table-cell>
      </fo:table-row>
   </xsl:template>
   
   <!-- Adressen-Root-Element-Template -->
   <xsl:template match="employees">
      <fo:table border-style="solid" table-layout="fixed" width="100%">
         <fo:table-column column-width="3cm"/>
         <fo:table-column column-width="3cm"/>
         <fo:table-column column-width="5cm"/>
         <fo:table-column column-width="3cm"/>
         <fo:table-header>
            <xsl:call-template name="table-head"/>
         </fo:table-header>
         <fo:table-body>
            <xsl:apply-templates select="employee"/>
         </fo:table-body>
      </fo:table>
   </xsl:template>
   
   <!-- Template der 'adresse'-Elemente -->
   <xsl:template match="employee">
      <fo:table-row>
         <fo:table-cell xsl:use-attribute-sets="cell-style">
            <fo:block xsl:use-attribute-sets="block-style">
               <fo:inline color="#FF0000"><xsl:value-of select="firstName"/></fo:inline>
            </fo:block>
         </fo:table-cell>
         <fo:table-cell xsl:use-attribute-sets="cell-style">
            <fo:block xsl:use-attribute-sets="block-style">
               <xsl:value-of select="lastName"/>
            </fo:block>
         </fo:table-cell>
         <fo:table-cell xsl:use-attribute-sets="cell-style">
            <fo:block xsl:use-attribute-sets="block-style">
               <fo:inline color="#0EDF49"><xsl:value-of select="email"/></fo:inline>
               <!-- gruen 0EDF49 -->
               <!-- rot FF0000 -->
            </fo:block>
         </fo:table-cell>
         <fo:table-cell xsl:use-attribute-sets="cell-style">
            <fo:block xsl:use-attribute-sets="block-style">
               <xsl:value-of select="department/name"/>
               <xsl:text> </xsl:text>
               <xsl:value-of select="department/id"/>
            </fo:block>
         </fo:table-cell>

      </fo:table-row>
   </xsl:template>
   
</xsl:stylesheet>