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
  <xsl:variable name="todopage-output">
    <xsl:apply-templates select="language" mode="todopage"/>
  </xsl:variable>
  <xsl:apply-templates select="$todopage-output" mode="output"/>  
  <!-- create the paradigms -->
  <xsl:apply-templates select="//paradigms" mode="paradigmpages"/>
  <!-- create the morphemes -->
  <xsl:apply-templates select="//morphemes" mode="morphemepages"/>
  <!-- create the dictionary index -->
  <xsl:variable name="indexpage-output">
    <xsl:apply-templates select="language" mode="indexpage"/>
  </xsl:variable>
  <xsl:apply-templates select="$indexpage-output" mode="output"/>
  <!-- create the english dictionary index -->
  <xsl:variable name="engdict-output">
    <xsl:apply-templates select="/" mode="engdict"/>
  </xsl:variable>
  <xsl:apply-templates select="$engdict-output" mode="output"/>
 </xsl:template>

</xsl:stylesheet>
