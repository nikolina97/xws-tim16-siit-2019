<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:sp="https://github.com/nikolina97/xws-tim16-siit-2019"
    version="2.0">
    <xsl:import href="templates.xsl"/>
    <xsl:template match="/">
         <html style="text-align: justify; margin: auto 50px auto 50px;">
         <head>
            	<title>Review</title>
            </head>
            <body>
            	<h1 align="center"><xsl:value-of select = "sp:review/sp:title"/></h1>
            	<div>
            		<h3>Questions:</h3>
            		<ol>
            			 <xsl:for-each select="sp:review/sp:questions/sp:question">
            			 <li> <bold><xsl:value-of select="./sp:question"/></bold> <br/><xsl:apply-templates select="./sp:answer"/></li>
            			 </xsl:for-each>
            		</ol>
            		<h3>Comments:</h3>
            		<ol>
            			 <xsl:for-each select="sp:review/sp:comments/sp:comment">
            			 <li>
            			 	<xsl:apply-templates></xsl:apply-templates>
            			  </li>
            			 </xsl:for-each>
            		</ol>
            		<h3>Grades:</h3>
            		<ul>
            			<li><i>Introduction: </i> <xsl:value-of select="sp:review/sp:grades/sp:introduction"/> </li>
            			<li><i>Conclusion: </i> <xsl:value-of select="sp:review/sp:grades/sp:consclusion"/> </li>
            			<li><i>Experiment results: </i> <xsl:value-of select="sp:review/sp:grades/sp:experimentResults"/> </li>
            			<li><b><i>Final grade: </i> <xsl:value-of select="sp:review/sp:grades/sp:finalGrade"/></b></li>
            		</ul>
            	</div>
       		</body>
        </html>
    </xsl:template>
</xsl:stylesheet>