<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="1.0">
 <xsl:import href="morphemepages.xsl"/>
 <xsl:import href="nameindex.xsl"/>
 <xsl:import href="todo.xsl"/>

 <xsl:output method="html"/>

 <xsl:template match="/">
  <xsl:document href="todo.html" method="html" encoding="iso-8859-1">
   <xsl:apply-templates select="language" mode="todopage"/>
  </xsl:document>
  <xsl:apply-templates select="//paradigms" mode="paradigmpages"/>
  <xsl:apply-templates select="//morphemes" mode="morphemepages"/>
  <xsl:document href="dictionary.html" method="html" encoding="iso-8859-1">
   <xsl:apply-templates select="language" mode="indexpage"/>
  </xsl:document>
 </xsl:template>

</xsl:stylesheet>