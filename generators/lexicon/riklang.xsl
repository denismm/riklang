<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="1.0">
 <xsl:import href="morphemepages.xsl"/>
 <xsl:import href="nameindex.xsl"/>
 <xsl:import href="todo.xsl"/>
 <xsl:import href="engdict.xsl"/>

 <xsl:output method="html"/>

 <!-- for the riklang document -->
 <xsl:template match="/">
  <!-- put todopage in todo.html -->
  <xsl:result-document href="todo.html" method="html" encoding="iso-8859-1">
   <xsl:apply-templates select="language" mode="todopage"/>
  </xsl:result-document>
  <!-- create the paradigms -->
  <xsl:apply-templates select="//paradigms" mode="paradigmpages"/>
  <!-- create the morphemes -->
  <xsl:apply-templates select="//morphemes" mode="morphemepages"/>
  <!-- create the dictionary index -->
  <xsl:result-document href="dictionary.html" method="html" encoding="iso-8859-1">
   <xsl:apply-templates select="language" mode="indexpage"/>
  </xsl:result-document>
  <!-- create the english dictionary index -->
  <xsl:result-document href="engdict.html" method="html" encoding="iso-8859-1">
   <xsl:apply-templates select="/" mode="engdict"/>
  </xsl:result-document>
 </xsl:template>

</xsl:stylesheet>
