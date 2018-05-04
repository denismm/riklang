<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="http://www.suberic.net/~dmm/rikchik"
                version="2.0">

    <xsl:import href="rikbasics.xsl"/>
    <xsl:strip-space elements="*"/>

    <xsl:output method="html" encoding="iso-8859-1"/>

    <xsl:function name="r:sort-form">
      <xsl:param name="text"/>
      <xsl:sequence select="replace(lower-case(normalize-space($text)), '^a |^an |^the |^to ', '')"/>
    </xsl:function>

    <xsl:template match="/" mode="engdict">
      <xsl:variable name="doc" select="."/>
      <html><head>
        <title>English-Rikchik Dictionary</title>
      </head><body>
      <h1>English-Rikchik Dictionary</h1>
      <p>An index by English gloss to readings, compounds, and idioms.</p>
      <xsl:for-each select="tokenize('a b c d e f g h i j k l m n o p q r s t u v w x y z',' ')">
        <a href="#{.}"><xsl:value-of select="upper-case(.)"/></a><xsl:text> </xsl:text>
      </xsl:for-each>
      <xsl:for-each select="tokenize('a b c d e f g h i j k l m n o p q r s t u v w x y z',' ')">
        <dl>
          <xsl:variable name="letter" select="."/>
          <h2 id="{$letter}"><xsl:value-of select="upper-case($letter)"/></h2>
          <xsl:apply-templates select="$doc//compound/gloss[starts-with(r:sort-form(text), $letter)]|$doc//morpheme[starts-with(r:sort-form(@name), $letter)]" mode="engdict">
            <xsl:sort select="r:sort-form(./text|./@name)"/>
          </xsl:apply-templates>
        </dl>
      </xsl:for-each>
      </body></html>
    </xsl:template>

    <!-- skips -->
    <xsl:template match="example/translation" mode="engdict" priority="2"/>
    <xsl:template match="note/translation" mode="engdict" priority="2"/>
    <xsl:template match="role/translation" mode="engdict" priority="2"/>
    <xsl:template match="translation[not(ancestor::morpheme)]" mode="engdict" priority="4"/>
    <!--xsl:template match="translation[not(text//text())]" mode="engdict" priority="3"/-->
    <xsl:template match="gloss[not(text//text())]" mode="engdict" priority="3"/>
    <xsl:template match="translation[../gloss]" mode="engdict" priority="2"/>
    <xsl:template match="reading/translation[../../../gloss and count(../../reading) = 1]" mode="engdict" priority="2"/>


    <xsl:template match="gloss" mode="engdict">
      <xsl:message><xsl:value-of select="normalize-space(text)"/></xsl:message>
      <dt><xsl:value-of select="normalize-space(text)"/></dt>
      <dd>
	<xsl:apply-templates select=".." mode="basiclinkentry"/>
	<xsl:apply-templates select="../translation" mode="text"/>
	<xsl:apply-templates select="../readings/reading[translation]" mode="text"/>
      </dd>
    </xsl:template>

    <xsl:template match="morpheme" mode="engdict">
      <xsl:message>Morpheme: <xsl:value-of select="normalize-space(@name)"/></xsl:message>
      <dt><xsl:value-of select="normalize-space(@name)"/></dt>
      <dd>
	<xsl:apply-templates select="." mode="basiclinkentry"/>
	<xsl:apply-templates select="readings/reading[translation|gloss]" mode="text"/>
      </dd>
    </xsl:template>

    <xsl:template match="reading[translation|gloss]" mode="text" priority="2">
      <xsl:value-of select="@aspect"/>
      <xsl:text>: </xsl:text>
      <xsl:apply-templates select="translation|gloss" mode="text"/>
      <br/>
    </xsl:template>

    <xsl:template match="translation" mode="engdict">
      <xsl:message>Need gloss in <xsl:value-of select="node-name(..)"/>
	<xsl:text> in </xsl:text>
	<xsl:value-of select="ancestor::morpheme/@name"/>
	<xsl:text> &quot;</xsl:text>
	<xsl:value-of select="normalize-space(text)"/>
	<xsl:text>&quot;.</xsl:text>
      </xsl:message>
      
      <dt><xsl:value-of select="normalize-space(text)"/></dt>
      <dd>
	<xsl:apply-templates select=".." mode="basiclinkentry"/>
      </dd>
    </xsl:template>

</xsl:stylesheet>

