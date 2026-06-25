<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:xs="http://www.w3.org/2001/XMLSchema"
		xmlns:rikchik="https://suberic.net/~dmm/rikchik/intro.html"
                version="1.1">

  <xsl:param name="riklang-src">../../data/riklang.xml</xsl:param>

  <xsl:variable name="riklang" select="doc($riklang-src)"/>

  <xsl:template match="*">
    <xsl:copy>
      <xsl:copy-of select="@*"/>
      <xsl:apply-templates select="element()|comment()"/>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="comment()">
    <xsl:comment><xsl:value-of select="."/></xsl:comment>
  </xsl:template>

  <xsl:template match="compound">
    <xsl:variable name="morpheme-name" select="ancestor::morpheme/@name"/>
    <xsl:variable name="morpheme" select="$riklang//morpheme[@name=$morpheme-name]"/>
    <xsl:variable name="morpheme-utterances" select="$morpheme/compounds/compound/addition/utterance"/>
    <xsl:variable name="this-first-word" select="addition/utterance/word[1]"/>
    <xsl:variable name="matching-utterance" select="$morpheme-utterances[rikchik:words-match(./word[1], $this-first-word)]"/>
    <xsl:choose>
      <xsl:when test="not($matching-utterance)">
	<xsl:copy>
	  <xsl:copy-of select="@*"/>
	  <xsl:apply-templates select="element()|comment()"/>
	</xsl:copy>
      </xsl:when>
      <xsl:otherwise>
	<xsl:message>Skipped existing compound in <xsl:value-of select="$morpheme-name"/>: <xsl:value-of select="normalize-space($matching-utterance/ancestor::compound//gloss)"/></xsl:message>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
  <xsl:function name="rikchik:words-match" as="xs:boolean">
    <xsl:param name="a"/>
    <xsl:param name="b"/>
    <xsl:variable name="this-matches"
		  select="$a/@morpheme = $b/@morpheme and
			  $a/@aspect = $b/@aspect and
			  $a/@relation = $b/@relation and
			  $a/@collector = $b/@collector"/>
    <xsl:choose>
      <xsl:when test="not($this-matches)">
	<xsl:sequence select="false()"/>
      </xsl:when>
      <xsl:when test="not($a/following-sibling::word or $b/following-sibling::word)">
	<xsl:sequence select="true()"/>
      </xsl:when>
      <xsl:when test="not(count($a/following-sibling::word) = count($b/following-sibling::word))">
	<xsl:sequence select="false()"/>
      </xsl:when>
      <xsl:otherwise>
	<xsl:sequence select="rikchik:words-match(
			      $a/following-sibling::word[1],
			      $b/following-sibling::word[1])"/>
      </xsl:otherwise>
    </xsl:choose>

  </xsl:function>

  
</xsl:stylesheet>
