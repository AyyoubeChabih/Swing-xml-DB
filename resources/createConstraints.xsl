<?xml version='1.0'?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
							xmlns:xsd="http://www.w3.org/2001/XMLSchema"
>

<xsl:template match="/">
	<xsl:element name="xsd:schema">
		<xsl:element name="xsd:element">
			<xsl:attribute name="name">
				<xsl:value-of select="/table/@name"/>
			</xsl:attribute>
			<xsl:element name="xsd:complexType">
				<xsl:element name="xsd:sequence">
					<xsl:for-each select="/table/column">
						<xsl:element name="xsd:element">
							<xsl:attribute name="name">
								<xsl:value-of select="name"/>
							</xsl:attribute>
							<xsl:attribute name="type">		
								<xsl:choose>
									<xsl:when test="type = 'number'">xsd:decimal</xsl:when>
									<xsl:when test="type = 'string'">xsd:string</xsl:when>
									<xsl:when test="type = 'date'">xsd:date</xsl:when>
								</xsl:choose>
							</xsl:attribute>
						</xsl:element>
					</xsl:for-each>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:element>
</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="livres.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->