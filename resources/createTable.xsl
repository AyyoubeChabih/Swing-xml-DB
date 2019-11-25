<?xml version='1.0'?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
								xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>

<xsl:template match="/">
	<xsl:variable name="tableName">
		<xsl:value-of select="table/@name"/>
	</xsl:variable>
	<xsl:element name="{$tableName}">
		<xsl:for-each select="/table/column">
			<xsl:variable name="ColumnName">
				<xsl:value-of select="name"/>
			</xsl:variable>
			<xsl:element name="{$ColumnName}"/>
		</xsl:for-each>
	</xsl:element>
</xsl:template>

</xsl:stylesheet>