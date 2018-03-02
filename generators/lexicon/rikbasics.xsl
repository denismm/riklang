<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.1">
 <xsl:variable name="lang" select="'en'"/>
 
 <xsl:variable name="entrystyle" select="'blunt'"/>
 <xsl:variable name="entryheadstyle" select="'blunt'"/>
 <xsl:variable name="textstyle" select="'classic'"/>


 <xsl:template match="morpheme" mode="basiclinkentry">
  <xsl:variable name="href" select="concat('dictionary/',@name,'.html')"/> 
   <a href="{$href}"><img src="http://www.suberic.net/~dmm/rikchik/images/{$entrystyle}/3/m{@name}.png" alt="{@name}" border="0" width="42" height="42"/></a><br/>
   <a href="{$href}"><xsl:value-of select="@name"/></a>&#xA0;<br/><br/>
 </xsl:template>

</xsl:stylesheet>
