<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:sp="https://github.com/nikolina97/xws-tim16-siit-2019"
    version="2.0">
     <xsl:import href="templates.xsl"/>
    <xsl:template match="/">
         <html style="text-align: justify; margin: auto 50px auto 50px;">
            <head>
                <style type="text/css">
                </style>
            	<title>Scientific paper</title>
            </head>
            <body>
                 <h1 align="center">
                	 <xsl:value-of select="sp:scientific_paper/sp:title"/>
                 </h1>
                 
                  <b>Abstract</b><br></br>
                    <div>
                        <xsl:for-each select="sp:scientific_paper/sp:abstract/sp:paragraph">
                           <xsl:apply-templates></xsl:apply-templates>
                       </xsl:for-each>
                    </div><br/>
               
               <b>Keywords</b>:
               <i>
                    <xsl:for-each select="sp:scientific_paper/sp:abstract/sp:keywords/sp:keyword">
                        <xsl:value-of select="."/><xsl:if test="position()!=last()">, </xsl:if>
                    </xsl:for-each>	
                </i>
                
                	<xsl:for-each select="sp:scientific_paper/sp:chapters/sp:chapter">
                        <xsl:call-template name="ChapterTemplate">
                        <xsl:with-param name="chapter" select = "." />
                   	 	</xsl:call-template>
                    </xsl:for-each>
                    <h3>References</h3>
                    <xsl:for-each select="sp:scientific_paper/sp:references/sp:reference">
                        <xsl:call-template name="ReferenceTemplate">
                        <xsl:with-param name="reference" select = "." />
                   	 	</xsl:call-template>
                    </xsl:for-each>
                    <br/>
                    <br/>
             </body>
        </html>
    </xsl:template>
</xsl:stylesheet>