<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.1">
 <xsl:import href="rikbasics.xsl"/>
 <xsl:variable name="lang" select="'en'"/>

 <xsl:output method="html" encoding="iso-8859-1"/>
 
 <xsl:template match="/">
  <xsl:apply-templates select="//morphemes" mode="morphemepages"/>
  <xsl:apply-templates select="//paradigms" mode="paradigmpages"/>
 </xsl:template>

 <!-- morphemepages -->
 <xsl:template match="morphemes" mode="morphemepages">
  <xsl:apply-templates select="morpheme" mode="morphemepage"/>
 </xsl:template>

 <xsl:template match="morpheme" mode="morphemepage">
   <xsl:variable name="output">
     <page-body href="dictionary/{@name}.html" title="{@name}">
       <img src="http://www.suberic.net/~dmm/rikchik/images/blunt/5/m{@name}.png" alt="{@name}" width="70" height="70"/>
       <xsl:apply-templates select="glyph/note" mode="noteref"/>
       <h1><xsl:value-of select="@name"/></h1>
       <b>Paradigm:</b>&#xA0;<xsl:value-of select="@paradigm"/><br/>
       <xsl:apply-templates select="readings | roles | idioms | compounds" mode="morphemepage"/> 
       <hr/>
       <xsl:apply-templates select=".//note" mode="morphemepage" />
     </page-body>
   </xsl:variable>
   <xsl:apply-templates select="$output" mode="output"/>
 </xsl:template>
 
 <xsl:template match="readings" mode="morphemepage">
  <h2>Readings</h2>
  <xsl:variable name="readings" select="."/>
  <xsl:variable name="paradigm"
   ><xsl:choose
    ><xsl:when test="../@paradigm"
     ><xsl:value-of select="../@paradigm"
    /></xsl:when
    ><xsl:when test="../../../@paradigm"
     ><xsl:value-of select="../../../@paradigm"
    /></xsl:when
   ></xsl:choose
  ></xsl:variable>
  <table cellpadding="0" cellspacing="0" border="0">
   <xsl:for-each select="/language/aspects/aspect">
    <xsl:variable name="aspect" select="@name"/>
    <xsl:choose>
     <xsl:when test="$readings/reading[@aspect=$aspect]">
      <xsl:apply-templates 
        select="$readings/reading[@aspect=$aspect]"
	mode="morphemepage"/>
     </xsl:when>
     <xsl:when test="//paradigm[@name=$paradigm]/readings/reading[@aspect=$aspect]">
      <xsl:apply-templates 
        select="//paradigm[@name=$paradigm]/readings/reading[@aspect=$aspect]"
	mode="morphemepage"/>
     </xsl:when>
    </xsl:choose>
   </xsl:for-each>
  </table>
 </xsl:template>

 <xsl:template match="reading" mode="morphemepage">
  <tr><td valign="top" align="center">
   <img src="http://www.suberic.net/~dmm/rikchik/images/classic/5/a{@aspect}.png" alt="{@aspect}" title="{@aspect}" id="reading-{@aspect}" width="30" height="30"/><br/><strong><xsl:value-of select="@aspect"/></strong>
  </td><td valign="top" align="left">&#xA0;<br/>
   <xsl:apply-templates select="translation" mode="morphemepage"/><xsl:apply-templates select="gloss"/><br/>
   <xsl:apply-templates select="examples" mode="morphemepage"/>
   <xsl:apply-templates select="note" mode="noteref"/>
  </td></tr>
  <tr><td></td><td><hr/></td></tr>
 </xsl:template>

 <xsl:template match="paradigm/readings/reading" mode="morphemepage">
  <tr><td valign="top" align="center">
   <img src="http://www.suberic.net/~dmm/rikchik/images/classic/5/colora{@aspect}.png" alt="{@aspect}" title="{@aspect}" id="reading-{@aspect}" width="30" height="30"/><br/><strong><xsl:value-of select="@aspect"/></strong>
  </td><td valign="top" align="left">&#xA0;<br/>
   (From paradigm) <xsl:apply-templates select="translation"/><br/>
   <xsl:apply-templates select="examples" mode="morphemepage"/>
  </td></tr>
  <tr><td></td><td><hr/></td></tr>
 </xsl:template>

 <xsl:template match="roles" mode="morphemepage">
  <h2>Roles</h2>
  <xsl:variable name="roles" select="."/>
  <xsl:variable name="paradigm"
   ><xsl:choose
    ><xsl:when test="../@paradigm"
     ><xsl:value-of select="../@paradigm"
    /></xsl:when
    ><xsl:when test="../../../@paradigm"
     ><xsl:value-of select="../../../@paradigm"
    /></xsl:when
   ></xsl:choose
  ></xsl:variable>
  <table cellpadding="0" cellspacing="0" border="0">
   <xsl:for-each select="/language/relations/relation">
    <xsl:variable name="relation" select="@name"/>
    <xsl:choose>
     <xsl:when test="$roles/role[@relation=$relation]">
      <xsl:apply-templates 
        select="$roles/role[@relation=$relation]"
	mode="morphemepage"/>
     </xsl:when>
     <xsl:when test="//paradigm[@name=$paradigm]/roles/role[@relation=$relation]">
      <xsl:apply-templates 
        select="//paradigm[@name=$paradigm]/roles/role[@relation=$relation]"
	mode="morphemepage"/>
     </xsl:when>
    </xsl:choose>
   </xsl:for-each>
  </table>
 </xsl:template>

 <xsl:template match="role" mode="morphemepage">
  <tr><td valign="top" align="center">
   <img src="http://www.suberic.net/~dmm/rikchik/images/classic/5/r{@relation}.png" alt="{@relation}" width="80" height="20"/><br/><strong><xsl:value-of select="@relation"/></strong>
  </td><td valign="top" align="left">
   <xsl:apply-templates select="translation"/><br/>
   <xsl:apply-templates select="examples" mode="morphemepage"/>
   <xsl:apply-templates select="note" mode="noteref"/>
  </td></tr>
  <tr><td></td><td><hr/></td></tr>
 </xsl:template>

 <xsl:template match="paradigm/roles/role" mode="morphemepage">
  <tr><td valign="top" align="center">
   <img src="http://www.suberic.net/~dmm/rikchik/images/classic/5/colorr{@relation}.png" alt="{@relation}" width="80" height="20"/><br/><strong><xsl:value-of select="@relation"/></strong>
  </td><td valign="top" align="left">
   (From paradigm) <xsl:apply-templates select="translation"/><br/>
   <xsl:apply-templates select="examples" mode="morphemepage"/>
  </td></tr>
  <tr><td></td><td><hr/></td></tr>
 </xsl:template>

 <xsl:template match="compounds" mode="morphemepage">
  <h3>Compounds</h3>
  <xsl:apply-templates select="compound" mode="morphemepage"/>
   <xsl:variable name="current-morpheme" select="ancestor::morpheme"/>
   <xsl:apply-templates select="//compound[addition/utterance/word/@morpheme = $current-morpheme/@name]" mode="innerlinkentry"/>
 </xsl:template>

 <xsl:template match="idioms" mode="morphemepage">
  <h3>Idioms</h3>
  <xsl:apply-templates select="idiom" mode="morphemepage"/>
 </xsl:template>

 <xsl:template match="examples" mode="morphemepage">
  <xsl:apply-templates select="example" mode="morphemepage"/>
 </xsl:template>

 <xsl:template match="compound" mode="morphemepage">
   <xsl:apply-templates select="." mode="innerlinkentry"/>
   <xsl:apply-templates select="." mode="compoundpage"/>
 </xsl:template>

 <xsl:template match="compound" mode="compoundpage">
  <xsl:variable name="asciiform"
   ><xsl:apply-templates select="addition/utterance" mode="asciiform"
  /></xsl:variable>
  <xsl:variable name="morpheme" select="../../@name"/>
  <xsl:variable name="compoundcollector" select="count(addition/utterance/word) - sum(addition/utterance/word/@collector)"/>
  <xsl:variable name="fullasciiform" select="concat($asciiform,'_',$morpheme,'-I-End-',$compoundcollector)"/>
  <page-body href="dictionary/{$fullasciiform}.html" title="{translate($fullasciiform,'_',' ')}" >
    <!-- xsl:apply-templates select="addition/utterance" mode="morphemepage"/ -->
    <img src="http://www.suberic.net/~dmm/cgi-bin/rikchik.cgi?size=3&amp;message={$fullasciiform}" alt="{$fullasciiform}"/>
    <h1><xsl:value-of select="translate($fullasciiform,'_',' ')"/><xsl:apply-templates select="gloss"/></h1>
   <b>Paradigm:</b>&#xA0;<xsl:value-of select="../../@paradigm"/><br/>
   <xsl:apply-templates select="readings | roles | idioms | compounds" mode="morphemepage"/> 
   <xsl:apply-templates select=".//note" mode="morphemepage" />
  </page-body>
 </xsl:template>

 <xsl:template match="idiom" mode="morphemepage">
  <xsl:variable name="asciiform">
    <xsl:apply-templates select="utterance" mode="asciiform"/>
  </xsl:variable>
  <a name="{$asciiform}">
  <xsl:apply-templates select="utterance" mode="morphemepage"/><br/>
  <xsl:apply-templates select="translation" mode="morphemepage"/><br/>
  </a>
 </xsl:template>

 <xsl:template match="example" mode="morphemepage">
  <xsl:apply-templates select="utterance" mode="morphemepage"/><br/>
  <xsl:apply-templates select="translation" mode="morphemepage"/><br/>
 </xsl:template>
 
 <xsl:template match="note" mode="morphemepage">
  <xsl:variable name="notenumber">
   <xsl:number level="any" from="morpheme|compound"/> 
  </xsl:variable>
  <h4><a name="note{$notenumber}">Note <xsl:value-of select="$notenumber"/></a></h4>(<xsl:value-of select="@type"/>)<br/>
  <xsl:apply-templates mode="morphemepage"/>
 </xsl:template>

 <xsl:template match="note" mode="noteref">
  <xsl:variable name="notenumber">
   <xsl:number level="any" from="morpheme|compound"/> 
  </xsl:variable>
  (<a href="#note{$notenumber}"><xsl:value-of select="$notenumber"/></a>)
 </xsl:template>

 <xsl:template match="translation" mode="morphemepage">
  <xsl:value-of select="text"/>
 </xsl:template>

 <xsl:template match="utterance" mode="morphemepage">
  <xsl:param name="lineheight">4</xsl:param>
  <xsl:variable name="asciiform">
    <xsl:apply-templates select="word[1]" mode="asciiform"/>
    <xsl:for-each select="word[1]/following-sibling::word">
      <xsl:text>_</xsl:text>
      <xsl:apply-templates select="." mode="asciiform"/>
    </xsl:for-each>
  </xsl:variable>
  <img src="http://www.suberic.net/~dmm/cgi-bin/rikchik.cgi?lineheight={$lineheight}&amp;size=2&amp;{$asciiform}"/>
 </xsl:template>

 <xsl:template match="utterance" mode="asciiform"
  ><xsl:for-each select="word"
   ><xsl:apply-templates select="." mode="asciiform" /><xsl:if test="position()!=last()">_</xsl:if 
  ></xsl:for-each
 ></xsl:template>

 <xsl:template match="word" mode="asciiform"
  ><xsl:value-of select="@morpheme"
  />-<xsl:value-of select="@aspect"
  />-<xsl:value-of select="@relation"
  />-<xsl:value-of select="@collector"
  /><xsl:choose>
   <xsl:when test="@pronomial='none'"></xsl:when>
   <xsl:when test="@pronomial='pronomial'">P</xsl:when>
   <xsl:when test="@pronomial='option'">O</xsl:when>
  </xsl:choose
 ></xsl:template>

 <!-- /morphemepages -->

 <!-- paradigmpages -->

 <xsl:template match="paradigms" mode="paradigmpages">
  <xsl:apply-templates select="paradigm" mode="paradigmpage"/>
 </xsl:template>
 
 <xsl:template match="paradigm" mode="paradigmpage">
   <xsl:variable name="titlecasename"><xsl:value-of select="translate(substring(@name,1,1),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/><xsl:value-of select="substring(@name,2)"/></xsl:variable>
   <xsl:variable name="output">
   <page-body title="{$titlecasename} Paradigm" href="paradigms/{@name}.html">
    <h1><xsl:value-of select="$titlecasename"/> Paradigm</h1>
    <xsl:apply-templates select="readings | roles | idioms | compounds" mode="morphemepage"/> 

    <!-- report -->
    <xsl:apply-templates select="." mode="paradigmreport"/>
   </page-body>
   </xsl:variable>
   <xsl:apply-templates select="$output" mode="output"/>
 </xsl:template>
 <xsl:template mode="paradigmreport" match="paradigm">
  <xsl:variable name="paradigm" select="."/>
  <xsl:variable name="readings" select="$paradigm/readings"/>
  <xsl:variable name="roles" select="$paradigm/roles"/>
  <h2>Example Report</h2>
  <table style="border:2px solid #888; border-collapse:collapse">
  <!-- header section -->
  <tr><td/>
  <xsl:for-each select="$readings/reading">
   <td style="border:2px solid #888" align="center" valign="middle">
   <img src="http://www.suberic.net/~dmm/rikchik/images/classic/5/colora{@aspect}.png" alt="{@aspect}" width="30" height="30"/>
   (<xsl:value-of select="@aspect"/>)
   </td>
  </xsl:for-each>
  <xsl:for-each select="$roles/role">
   <td style="border:2px solid #888" align="center">
   <img src="http://www.suberic.net/~dmm/rikchik/images/classic/5/colorr{@relation}.png" alt="{@relation}" width="80" height="20"/><br/><xsl:value-of select="@relation"/>
   </td>
  </xsl:for-each>
  </tr>
  <xsl:for-each select="//morpheme[@paradigm=$paradigm/@name]">
  <xsl:variable name="morpheme"  select="."/>
   <tr>
   <td style="border:2px solid #888" align="center">
   <a href="http://www.suberic.net/~dmm/rikchik/language/dictionary/{@name}.html">
   <img border="0" src="http://www.suberic.net/~dmm/rikchik/images/classic/2/m{@name}.png" alt="{@name}" width="28" height="28"/><br/><xsl:value-of select="@name"/>
   </a>
   </td>
      <xsl:for-each select="$readings/reading">
       <xsl:variable name="aspect" select="@aspect"/>
       <td style="border:1px solid #888"><xsl:value-of select="$morpheme/readings/reading[@aspect=$aspect]/translation/text"/></td>
      </xsl:for-each>
      <xsl:for-each select="$roles/role">
       <xsl:variable name="relation" select="@relation"/>
       <td style="border:1px solid #888"><xsl:value-of select="$morpheme/roles/role[@relation=$relation]/translation/text"/></td>
      </xsl:for-each>
   </tr>
   </xsl:for-each>
  </table>
 </xsl:template>


 <!-- /paradigmpages -->

</xsl:stylesheet>


