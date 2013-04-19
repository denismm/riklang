<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.1">

 <xsl:import href="rikbasics.xsl"/>

 <xsl:output method="html" encoding="iso-8859-1"/>
 
 <xsl:template match="/">
  <xsl:apply-templates select="language" mode="indexpage"/>
 </xsl:template>
 
 <!-- indexes -->
 <xsl:template match="language" mode="indexpage">
  <html><head>
  <title>Morpheme Dictionary</title>
  </head><body bgcolor="#FFFFFF">
  <h1>Morpheme Dictionary</h1>
  <ul>
  <li><a href="#name">By Name</a></li>
  <li><a href="#paradigm">By Paradigm</a></li>
  <li><a href="#glyph">By Glyph (rikchik ordering)</a></li>
  <li><a href="quicklist.html">Quick Access By Glyph</a></li>
  </ul>
  <h2><a name="name">Morphemes by name</a></h2>
  <xsl:message>writing name index.</xsl:message>
  <xsl:apply-templates select="//morphemes" mode="indexname"/>
  <h2><a name="paradigm">Morphemes by paradigm</a></h2>
  <xsl:message>writing paradigm index.</xsl:message>
  <xsl:apply-templates select="//morphemes" mode="indexparadigm"/>
  <h2><a name="glyph">Morphemes by glyph</a></h2>
  <xsl:message>writing glyph index.</xsl:message>
  <table cellpadding="0" cellspacing="0" border="0">
   <xsl:apply-templates select="//morpheme" mode="nameindexentry"/>
  </table>
  </body></html>
  <xsl:apply-templates select="//morphemes" mode="morphemequicklist"/>
 </xsl:template>

 <xsl:template match="morphemes" mode="indexname">
  <table cellpadding="0" cellspacing="0" border="0">
   <xsl:variable name="morphemesbyname">
    <xsl:for-each select="morpheme">
     <xsl:sort select="@name"/>
     <xsl:copy-of select="."/>
    </xsl:for-each>
   </xsl:variable>
   <xsl:apply-templates select="$morphemesbyname" mode="nameindexentry">
   </xsl:apply-templates>
  </table>
 </xsl:template>

 <xsl:template match="morphemes" mode="indexparadigm">
  <xsl:variable name="morphemesbyparadigm">
   <xsl:for-each select="morpheme">
    <xsl:sort select="@paradigm"/>
    <xsl:sort select="@name"/>
    <xsl:copy-of select="."/>
   </xsl:for-each>
  </xsl:variable>
   <xsl:apply-templates select="$morphemesbyparadigm" mode="paradigmindexentry">
   </xsl:apply-templates>
 </xsl:template>

 <xsl:template match="morpheme[position() mod 7 = 1]" mode="nameindexentry">
  <tr>
   <xsl:apply-templates select=". | following-sibling::morpheme[position() &lt; 7]" mode="nameindexsubentry"/>
  </tr>
 </xsl:template>

 <xsl:template match="morpheme" mode="nameindexsubentry">
  <td align="center">
   <xsl:apply-templates select="." mode="basiclinkentry"/>
  </td>
 </xsl:template>

 <!--xsl:template match="morpheme" mode="basiclinkentry">
  <xsl:variable name="href" select="concat('dictionary/',@name,'.html')"/> 
   <a href="{$href}"><img src="http://www.suberic.net/~dmm/rikchik/images/classic/3/m{@name}.png" alt="{@name}" border="0" width="42" height="42"/></a><br/>
   <a href="{$href}"><xsl:value-of select="@name"/></a>&#xA0;<br/><br/>
 </xsl:template-->

 <xsl:template match="morpheme" mode="nameindexentry">
 </xsl:template>

 <xsl:template match="morpheme[not(@paradigm=preceding::morpheme/@paradigm)]" mode="paradigmindexentry">
  <xsl:variable name="morphemeparadigm" select="@paradigm"/> 
  <xsl:variable name="morphemesbyname">
   <xsl:for-each select=". | following-sibling::morpheme[@paradigm=$morphemeparadigm]">
    <xsl:copy-of select="."/>
   </xsl:for-each>
  </xsl:variable>
  <h3><xsl:value-of select="$morphemeparadigm"/></h3>
  <table cellpadding="0" cellspacing="0" border="0">
   <xsl:apply-templates select="$morphemesbyname" mode="nameindexentry">
   </xsl:apply-templates>
  </table>
 </xsl:template>

 <xsl:template match="morpheme" mode="paradigmindexentry"/>

 <xsl:template match="morphemes" mode="morphemequicklist">
  <xsl:message>writing quick list</xsl:message>
  <xsl:document href="quicklist.html" method="html" encoding="iso-8859-1">
   <html><head>
    <title>Morpheme Dictionary</title>
   </head><body bgcolor="#FFFFFF" link="#FFFFFF" vlink="#FFFFFF" alink="#003300">
    <h1>Morpheme Quick List</h1>
    <xsl:for-each select="morpheme">
     <a href="dictionary/{@name}.html">
     <img src="http://www.suberic.net/~dmm/rikchik/images/classic/3/m{@name}.png" alt="{@name}" border="5" width="42" height="42"/>
     </a>
    </xsl:for-each>
   </body></html>
  </xsl:document>
 </xsl:template>

 <!-- /indexes -->

</xsl:stylesheet>


