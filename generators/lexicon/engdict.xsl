<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.1">

    <xsl:import href="rikbasics.xsl"/>
    <xsl:strip-space elements="*"/>

    <xsl:output method="html" encoding="iso-8859-1"/>


    <xsl:template match="/" mode="engdict">
      <xsl:apply-templates select="//translation|//gloss" mode="engdict">
	<xsl:sort select="replace(lower-case(normalize-space(text)), '^a |^an |^the |^to ', '')"/><!-- not text() -->
      </xsl:apply-templates>
    </xsl:template>

    
    <xsl:template match="example/translation" mode="engdict" priority="2"/>
    <xsl:template match="note/translation" mode="engdict" priority="2"/>
    <xsl:template match="translation[not(ancestor::morpheme)]" mode="engdict" priority="4"/>
    <xsl:template match="translation[not(text//text())]" mode="engdict" priority="3"/>
    <xsl:template match="gloss[not(text//text())]" mode="engdict" priority="3"/>
    <xsl:template match="translation[../gloss]" mode="engdict" priority="2"/>
    <xsl:template match="reading/translation[../../../gloss and count(../../reading) = 1]" mode="engdict" priority="2"/>
    <xsl:template match="translation|gloss" mode="engdict">
      <xsl:message><xsl:value-of select="normalize-space(text)"/></xsl:message>
    </xsl:template>

</xsl:stylesheet>

