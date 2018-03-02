<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.1">

    <xsl:import href="rikbasics.xsl"/>

    <xsl:output method="html" encoding="iso-8859-1"/>

    <xsl:variable name="aspectlist" select="/language/aspects"/> 
    <xsl:variable name="relationlist" select="/language/relations"/> 


    <xsl:template match="/">
	<xsl:apply-templates select="language" mode="todopage"/>
    </xsl:template>

    <xsl:template match="language" mode="todopage">
	<html>
	<body>
	    Todo page!
	    <xsl:apply-templates select="//morphemes" mode="todopage"/>
	</body>
	</html>
    </xsl:template>

    <xsl:template match="morphemes" mode="todopage">
	<table>
	<xsl:variable name="morphemelist">
	    <xsl:for-each select="morpheme">
		<xsl:copy-of select="."/>
	    </xsl:for-each>
	</xsl:variable>
	<xsl:apply-templates select="$morphemelist" mode="todopageentry"/>
	</table>
    </xsl:template>
    
    <xsl:template match="morpheme" mode="todopageentry">
	<tr><td align="center">
	    <xsl:apply-templates select="." mode="basiclinkentry"/>
	    <!--<xsl:value-of select="@name"/>-->
	</td>
	<xsl:apply-templates select="readings" mode="todopage"/>
	<xsl:apply-templates select="roles" mode="todopage"/>
	</tr>
    </xsl:template>

    <xsl:template match="readings" mode="todopage">
	<xsl:variable name="readings" select="."/>
	<xsl:variable name="paradigm">
	    <xsl:choose>
		<xsl:when test="../@paradigm">
		    <xsl:value-of select="../@paradigm"/>
		</xsl:when>
		<xsl:when test="../../../@paradigm">
		    <xsl:value-of select="../../../@paradigm"/>
		</xsl:when>
	    </xsl:choose>
	</xsl:variable>
	<xsl:for-each select="$aspectlist/aspect">
	    <xsl:variable name="aspect" select="@name"/>
	    <xsl:choose>
	     <xsl:when test="$readings/reading[@aspect=$aspect]">
		<td><img src="http://www.suberic.net/~dmm/rikchik/images/classic/5/a{$aspect}.png" alt="{$aspect}" width="30" height="30"/></td>
	     </xsl:when>
	     <xsl:when test="//paradigm[@name=$paradigm]/readings/reading[@aspect=$aspect]">
		<td><img src="http://www.suberic.net/~dmm/rikchik/images/classic/5/colora{$aspect}.png" alt="{$aspect}" width="30" height="30"/></td>
	     </xsl:when>
	     <xsl:otherwise>
		<td></td>
	     </xsl:otherwise>
	    </xsl:choose>

	</xsl:for-each>
    </xsl:template>

    <xsl:template match="roles" mode="todopage">
	<xsl:variable name="roles" select="."/>
	<xsl:variable name="paradigm">
	    <xsl:choose>
		<xsl:when test="../@paradigm">
		    <xsl:value-of select="../@paradigm"/>
		</xsl:when>
		<xsl:when test="../../../@paradigm">
		    <xsl:value-of select="../../../@paradigm"/>
		</xsl:when>
	    </xsl:choose>
	</xsl:variable>
	<xsl:for-each select="$relationlist/relation">
	    <xsl:variable name="relation" select="@name"/>
	    <xsl:choose>
	     <xsl:when test="$roles/role[@relation=$relation]">
		<td><img src="http://www.suberic.net/~dmm/rikchik/images/classic/5/r{$relation}.png" alt="{$relation}" width="80" height="20"/></td>
	     </xsl:when>
	     <xsl:when test="//paradigm[@name=$paradigm]/roles/role[@relation=$relation]">
		<td><img src="http://www.suberic.net/~dmm/rikchik/images/classic/5/colorr{$relation}.png" alt="{$relation}" width="80" height="20"/></td>
	     </xsl:when>
	     <xsl:otherwise>
		<td></td>
	     </xsl:otherwise>
	    </xsl:choose>

	</xsl:for-each>
    </xsl:template>



</xsl:stylesheet>

