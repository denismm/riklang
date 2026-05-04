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
      <word morpheme="{../../@name}" aspect="I" relation="End" collector="{$compoundcollector}"/>
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
    <xsl:value-of select="translate($asciiform,'_',' ')"/><xsl:apply-templates select="gloss"/></a></p>
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
  </xsl:apply-templates><br/><xsl:value-of select="translate($asciiform,'_',' ')"/><xsl:apply-templates select="gloss"/></a></p>
 </xsl:template>

 <xsl:template match="morpheme" mode="basiclinkentry">
   <xsl:variable name="href">
     <xsl:apply-templates select="." mode="url"/>
   </xsl:variable>
   <a href="{$href}"><img src="http://www.suberic.net/~dmm/rikchik/images/{$entrystyle}/3/m{@name}.png" alt="{@name}" border="0" width="42" height="42"/></a><br/>
   <a href="{$href}"><xsl:value-of select="@name"/></a>&#xA0;<br/><br/>
 </xsl:template>

 <xsl:template match="morpheme" mode="sublinkentry">
   <xsl:variable name="href">
     <xsl:apply-templates select="." mode="url"/>
   </xsl:variable>
   <a href="{$href}"><img src="http://www.suberic.net/~dmm/rikchik/images/classic/2/m{@name}.png" alt="{@name}" border="0" width="28" height="28"/></a><br/>
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

 <xsl:template match="gloss">
   <xsl:text> (</xsl:text>
   <xsl:choose>
     <!-- if we specify a lang and there are multiple glosses -->
     <xsl:when test="$lang and text[2]">
       <xsl:value-of select="normalize-space(text[lang($lang)])"/>
     </xsl:when>
     <xsl:otherwise>
       <xsl:value-of select="normalize-space(text[1])"/>
     </xsl:otherwise>
   </xsl:choose>
   <xsl:text>)</xsl:text>
 </xsl:template>

 <xsl:template match="page-body" mode="output">
   <xsl:message>Writing file for <xsl:value-of select="@title"/></xsl:message>
   <xsl:result-document href="{@href}" method="html" encoding="iso-8859-1">
     <html>
       <head>
	 <title><xsl:text>Rikchik Language Reference Dictionary: </xsl:text><xsl:value-of select="@title"/></title>
	 <style><!-- link="#FFFFFF" vlink="#FFFFFF" alink="#003300" -->
	   a.quick:link, a.quick:visited {
	     color: #FFFFFF;
	   }

	   a.quick:hover, a.quick:active {
	     color: #003300;
	   }
	 </style>
       </head>
       <body bgcolor="#FFFFFF">
	 <xsl:apply-templates mode="output"/>
	 <xsl:apply-templates select="." mode="footer"/>
       </body>
     </html>
   </xsl:result-document>
 </xsl:template>

 <xsl:template match="*" mode="output">
   <xsl:copy>
     <xsl:copy-of select="@*"/>
     <xsl:apply-templates mode="output"/>
   </xsl:copy>
 </xsl:template>
 
 <xsl:template match="page-body" mode="footer">
   <xsl:variable name="backdir">
     <xsl:if test="count(tokenize(@href, '/')) &gt; 1">
       <xsl:text>../</xsl:text>
     </xsl:if> 
   </xsl:variable>
   <hr />
   <address>
     Part of the <a href="{$backdir}dictionary.html">Rikchik Language Reference Dictionary</a>.
     <a href="/~dmm/rikchik/intro.html">Rikchik culture and language</a> 
     by Denis Moskowitz. 
     Word assembly program utilizes 
     <a href="http://www.boutell.com/gd/">gd</a>, a graphics library, and
     <a href="http://www-genome.wi.mit.edu/ftp/pub/software/WWW/GD.html">GD.pm</a>,
     a perl interface to gd. gd is &#xA9; 1994, 1995, Quest Protein Database 
     Center, Cold Spring Harbor Labs.  GD.pm is &#xA9; 1995, Lincoln D. Stein.
     Both are used with permission.
   </address>
 </xsl:template>
 
</xsl:stylesheet>
