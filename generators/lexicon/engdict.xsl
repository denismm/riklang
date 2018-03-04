<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0">

    <xsl:import href="rikbasics.xsl"/>
    <xsl:strip-space elements="*"/>

    <xsl:output method="html" encoding="iso-8859-1"/>


    <xsl:template match="/" mode="engdict">
      <dl>
        <xsl:apply-templates select="//translation|//gloss" mode="engdict">
	        <xsl:sort select="replace(lower-case(normalize-space(text)), '^a |^an |^the |^to ', '')"/><!-- not text() -->
        </xsl:apply-templates>
      </dl>
    </xsl:template>

    <!-- skips -->
    <xsl:template match="example/translation" mode="engdict" priority="2"/>
    <xsl:template match="note/translation" mode="engdict" priority="2"/>
    <xsl:template match="role/translation" mode="engdict" priority="2"/>
    <xsl:template match="translation[not(ancestor::morpheme)]" mode="engdict" priority="4"/>
    <!--xsl:template match="translation[not(text//text())]" mode="engdict" priority="3"/-->
    <xsl:template match="gloss[not(text//text())]" mode="engdict" priority="3"/>
    <xsl:template match="translation[../gloss]" mode="engdict" priority="2"/>
    <xsl:template match="reading/translation[../../../gloss and count(../../reading) = 1]" mode="engdict" priority="2"/>


    <xsl:template match="gloss" mode="engdict">
      <xsl:message><xsl:value-of select="normalize-space(text)"/></xsl:message>
      <dt><xsl:value-of select="normalize-space(text)"/></dt>
      <dd>
	<xsl:apply-templates select=".." mode="basiclinkentry"/>
      </dd>
    </xsl:template>

    <xsl:template match="translation" mode="engdict">
      <xsl:message>Need gloss in <xsl:value-of select="node-name(..)"/>
	<xsl:text> in </xsl:text>
	<xsl:value-of select="ancestor::morpheme/@name"/>
	<xsl:text> &quot;</xsl:text>
	<xsl:value-of select="normalize-space(text)"/>
	<xsl:text>&quot;.</xsl:text>
      </xsl:message>
      
      <dt><xsl:value-of select="normalize-space(text)"/></dt>
      <dd>
	<xsl:apply-templates select=".." mode="basiclinkentry"/>
      </dd>
    </xsl:template>

</xsl:stylesheet>

