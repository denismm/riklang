<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
		xmlns:r="http://www.suberic.net/~dmm/rikchik"
                version="2.0">

  <xsl:output method="text"/>

  <xsl:template match="*">
    <xsl:text>&#xa;{</xsl:text>
    <xsl:text>&quot;type&quot;: &quot;</xsl:text>
    <xsl:value-of select="node-name(.)"/>
    <xsl:text>&quot;,</xsl:text>
    <xsl:apply-templates select="@name"/>
    <xsl:apply-templates/>
    <xsl:text>}</xsl:text>
    <xsl:if test="following-sibling::*">
      <xsl:text>,</xsl:text>
    </xsl:if>
  </xsl:template>

  <xsl:template match="*[@name]">
    <xsl:text>&#xa;&quot;</xsl:text>
    <xsl:value-of select="@name"/>
    <xsl:text>&quot; : </xsl:text>
    <xsl:text>{</xsl:text>
    <xsl:text>&quot;type&quot;: &quot;</xsl:text>
    <xsl:value-of select="node-name(.)"/>
    <xsl:text>&quot;,</xsl:text>
    <xsl:apply-templates/>
    <xsl:text>}</xsl:text>
    <xsl:if test="following-sibling::*">
      <xsl:text>,</xsl:text>
    </xsl:if>
  </xsl:template>

  <!--
      converts simple arithmetic to JavaScript.
      / = x/y
      r = x * Math.sqrt(y)
      s = x * (y - Math.sqrt(2))/2
      -->
  <xsl:function name="r:parseNum">
    <xsl:param name="text"/>
    <xsl:choose>
      <xsl:when test="contains($text, '/')">
	<xsl:text>(</xsl:text>
	<xsl:value-of select="r:parseNum(substring-before($text, '/'))"/>
	<xsl:text>) / (</xsl:text>
	<xsl:value-of select="r:parseNum(substring-after($text, '/'))"/>
	<xsl:text>)</xsl:text>
      </xsl:when>
      <xsl:when test="contains($text, 'r')">
	<xsl:text>(</xsl:text>
	<xsl:value-of select="r:parseNum(substring-before($text, 'r'))"/>
	<xsl:text>) * Math.sqrt(</xsl:text>
	<xsl:value-of select="r:parseNum(substring-after($text, 'r'))"/>
	<xsl:text>)</xsl:text>
      </xsl:when>
      <xsl:when test="contains($text, 's')">
	<xsl:text>(</xsl:text>
	<xsl:value-of select="r:parseNum(substring-before($text, 's'))"/>
	<xsl:text>) * ((</xsl:text>
	<xsl:value-of select="r:parseNum(substring-after($text, 's'))"/>
	<xsl:text>) - Math.sqrt(2)) / 2</xsl:text>
      </xsl:when>
      <xsl:otherwise>
	<xsl:value-of select="$text"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:function>
  

  <xsl:template match="@*[matches(.,'^-?[0-9sr/\.]*$')]">
    <xsl:text>&quot;</xsl:text>
    <xsl:value-of select="node-name(.)"/>
    <xsl:text>&quot;: </xsl:text>
    <xsl:value-of select="r:parseNum(string(.))"/>
    <xsl:text>,</xsl:text>
  </xsl:template>

  <xsl:template match="@*">
    <xsl:text>&quot;</xsl:text>
    <xsl:value-of select="node-name(.)"/>
    <xsl:text>&quot;: &quot;</xsl:text>
    <xsl:value-of select="."/>
    <xsl:text>&quot;,</xsl:text>
  </xsl:template>

  <xsl:template match="*[ends-with(string(node-name(.)), 's')]">
    <xsl:apply-templates select="." mode="list"/>
  </xsl:template>

  <xsl:template match="*" mode="list">
    <xsl:if test="parent::*">
      <xsl:text>&#xa;</xsl:text>
      <xsl:text>&quot;</xsl:text>
      <xsl:value-of select="node-name(.)"/>
      <xsl:text>&quot;: </xsl:text>
    </xsl:if>
    <xsl:text>{</xsl:text>
    <xsl:apply-templates select="*"/>
    <xsl:text>}</xsl:text>
    <xsl:if test="following-sibling::*">
      <xsl:text>,</xsl:text>
    </xsl:if>
  </xsl:template>

  <xsl:template match="glyph">
    <xsl:if test="parent::*">
      <xsl:text>&#xa;</xsl:text>
      <xsl:text>&quot;</xsl:text>
      <xsl:value-of select="node-name(.)"/>
      <xsl:text>&quot;: </xsl:text>
    </xsl:if>
    <xsl:text>[</xsl:text>
    <xsl:apply-templates select="*"/>
    <xsl:text>]</xsl:text>
    <xsl:if test="following-sibling::*">
      <xsl:text>,</xsl:text>
    </xsl:if>
  </xsl:template>

  <xsl:template match="line|arc|ellarc|squiggle|circle|wicket|hook|halfhook|lbend|fishbend|zigzag|lobe|greatarc">
    <xsl:text>&#xa;{&quot;type&quot;: &quot;</xsl:text>
    <xsl:value-of select="node-name(.)"/>
    <xsl:text>&quot;, </xsl:text>
    <xsl:apply-templates select="@*"/>
    <xsl:text>"h":""</xsl:text>
    <xsl:text>}</xsl:text>
    <xsl:if test="following-sibling::*">
      <xsl:text>,</xsl:text>
    </xsl:if>
  </xsl:template>

</xsl:stylesheet>


