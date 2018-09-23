<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">

  <xsl:output method="xml"/>

  <xsl:template match="*">
    <xsl:copy>
      <xsl:copy-of select="@*"/>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>


  <xsl:template match="*[not(ancestor-or-self::glyph or .//glyph)]" priority="7"/>
  <xsl:template match="glyph/note"/>
  <xsl:template match="text()"/>

</xsl:stylesheet>
