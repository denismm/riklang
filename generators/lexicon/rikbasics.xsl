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

 <xsl:template match="compound" mode="basiclinkentry">
   <xsl:apply-templates select="." mode="linkentry"/>
 </xsl:template>

 <xsl:template match="compound" mode="innerlinkentry">
   <xsl:apply-templates select="." mode="linkentry">
     <xsl:with-param name="inner" select="'true'"/>
   </xsl:apply-templates>
 </xsl:template>

 <xsl:template match="compound" mode="linkentry">
  <xsl:param name="inner"/>
  <xsl:variable name="compoundcollector" select="count(addition/utterance/word) - sum(addition/utterance/word/@collector)"/>
  <xsl:variable name="utterance">
    <utterance>
      <xsl:copy-of select="addition/utterance/word"/>
      <xsl:if test="not($inner)">
	<word morpheme="{../../@name}" aspect="I" relation="End" collector="{$compoundcollector}"/>
      </xsl:if>
    </utterance>
  </xsl:variable>
  <xsl:variable name="asciiform"
   ><xsl:apply-templates select="$utterance" mode="asciiform"
  /></xsl:variable>
  <xsl:variable name="href">
    <xsl:apply-templates select="." mode="url">
      <xsl:with-param name="inner" select="$inner"/>
    </xsl:apply-templates>
  </xsl:variable>
  <p>
  <a href="{$href}">
    <xsl:apply-templates select="$utterance" mode="morphemepage">
      <xsl:with-param name="lineheight">1</xsl:with-param>
    </xsl:apply-templates><br/>
    <xsl:value-of select="translate($asciiform,'_',' ')"/><xsl:if test="gloss"> (<xsl:apply-templates select="gloss"/>)</xsl:if></a></p>
 </xsl:template>

 <xsl:template match="compound/readings/reading" mode="basiclinkentry">
   <xsl:apply-templates select="." mode="linkentry"/>
 </xsl:template>

 <xsl:template match="compound/readings/reading" mode="linkentry">
  <xsl:param name="inner"/>
  <xsl:variable name="morpheme" select="../../../../@name"/>
  <xsl:variable name="compoundcollector" select="count(../../addition/utterance/word) - sum(../../addition/utterance/word/@collector)"/>
  <xsl:variable name="utterance">
    <utterance>
      <xsl:copy-of select="../../addition/utterance/word"/>
      <word morpheme="{$morpheme}" aspect="{@aspect}" relation="End" collector="{$compoundcollector}"/>
    </utterance>
  </xsl:variable>
  <xsl:variable name="asciiform"
   ><xsl:apply-templates select="$utterance" mode="asciiform"
  /></xsl:variable>
  <xsl:variable name="href">
    <xsl:apply-templates select="." mode="url">
      <xsl:with-param name="inner" select="$inner"/>
    </xsl:apply-templates>
  </xsl:variable>
  <p>
  <a href="{$href}"><xsl:apply-templates select="$utterance" mode="morphemepage">
      <xsl:with-param name="lineheight">1</xsl:with-param>
  </xsl:apply-templates><br/><xsl:value-of select="translate($asciiform,'_',' ')"/><xsl:if test="gloss"> (<xsl:apply-templates select="gloss"/>)</xsl:if></a></p>
 </xsl:template>

 <xsl:template match="morpheme" mode="basiclinkentry">
   <xsl:variable name="href">
     <xsl:apply-templates select="." mode="url"/>
   </xsl:variable>
   <a href="{$href}"><img src="http://www.suberic.net/~dmm/rikchik/images/{$entrystyle}/3/m{@name}.png" alt="{@name}" border="0" width="42" height="42"/></a><br/>
   <a href="{$href}"><xsl:value-of select="@name"/></a>&#xA0;<br/><br/>
 </xsl:template>

 <xsl:template match="morpheme/readings/reading" mode="basiclinkentry">
   <xsl:variable name="href">
     <xsl:apply-templates select="." mode="url"/>
   </xsl:variable>
   <xsl:variable name="morpheme" select="../../@name"/>
   <a href="{$href}"><img src="http://www.suberic.net/~dmm/cgi-bin/rikchik.cgi?height=1&amp;size=2&amp;{$morpheme}-{@aspect}-End-0" alt="{$morpheme}-{@aspect}-End-0" border="0" width="49" height="49"/></a><br/>
   <a href="{$href}"><xsl:value-of select="../../@name"/>-<xsl:value-of select="@aspect"/>-End-0</a>&#xA0;<br/><br/>
 </xsl:template>

 <xsl:template match="idiom" mode="basiclinkentry">
  <xsl:variable name="asciiform">
    <xsl:apply-templates select="utterance" mode="asciiform"/>
  </xsl:variable>
  <a href="dictionary/{ancestor::morpheme/@name}.html#{$asciiform}">
    <xsl:apply-templates select="utterance" mode="morphemepage"/><br/>
    <xsl:value-of select="translate($asciiform,'_',' ')"/>
  </a>
 </xsl:template>

</xsl:stylesheet>
