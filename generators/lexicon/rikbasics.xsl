<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.1">
 <xsl:variable name="lang" select="'en'"/>
 
 <xsl:variable name="entrystyle" select="'blunt'"/>
 <xsl:variable name="entryheadstyle" select="'blunt'"/>
 <xsl:variable name="textstyle" select="'classic'"/>

 <xsl:template match="morpheme" mode="url">
   <xsl:param name="inner"/>
   <xsl:if test="not($inner)">
     <xsl:text>dictionary/</xsl:text>
   </xsl:if>
   <xsl:value-of select="@name"/>
   <xsl:text>.html</xsl:text>
 </xsl:template>

 <xsl:template match="compound" mode="url">
   <xsl:param name="inner"/>
  <xsl:variable name="asciiform"
   ><xsl:apply-templates select="addition/utterance" mode="asciiform"
  /></xsl:variable>
  <xsl:variable name="compoundcollector" select="count(addition/utterance/word) - sum(addition/utterance/word/@collector)"/>
   <xsl:if test="not($inner)">
     <xsl:text>dictionary/</xsl:text>
   </xsl:if>
   <xsl:value-of select="$asciiform"/>
   <xsl:text>_</xsl:text>
   <xsl:value-of select="../../@name"/>
   <xsl:text>-I-End-</xsl:text>
   <xsl:value-of select="$compoundcollector"/>
   <xsl:text>.html</xsl:text>
 </xsl:template>

 <xsl:template match="reading" mode="url">
   <xsl:param name="inner"/>
   <xsl:apply-templates select="../.." mode="url"/>
   <xsl:text>#reading-</xsl:text>
   <xsl:value-of select="@aspect"/>
 </xsl:template>

 <xsl:template match="compound" mode="innerlinkentry">
  <xsl:variable name="asciiform"
   ><xsl:apply-templates select="addition/utterance" mode="asciiform"
  /></xsl:variable>
  <xsl:variable name="compoundcollector" select="count(addition/utterance/word) - sum(addition/utterance/word/@collector)"/>
  <xsl:variable name="href">
    <xsl:apply-templates select="." mode="url">
      <xsl:with-param name="inner" select="'true'"/>
    </xsl:apply-templates>
  </xsl:variable>
  <a href="{$href}"><xsl:apply-templates select="addition/utterance" mode="morphemepage"/><xsl:value-of select="translate($asciiform,'_',' ')"/><xsl:if test="gloss"> (<xsl:apply-templates select="gloss"/>)</xsl:if></a>
 </xsl:template>

 <xsl:template match="morpheme" mode="basiclinkentry">
   <xsl:variable name="href">
     <xsl:apply-templates select="." mode="url"/>
   </xsl:variable>
   <a href="{$href}"><img src="http://www.suberic.net/~dmm/rikchik/images/{$entrystyle}/3/m{@name}.png" alt="{@name}" border="0" width="42" height="42"/></a><br/>
   <a href="{$href}"><xsl:value-of select="@name"/></a>&#xA0;<br/><br/>
 </xsl:template>

</xsl:stylesheet>
