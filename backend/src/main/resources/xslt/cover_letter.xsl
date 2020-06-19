<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:sp="https://github.com/nikolina97/xws-tim16-siit-2019"
    version="2.0">
    <xsl:import href="templates.xsl"/>
    <xsl:template match="/">
         <html style="text-align: justify; margin: auto 50px auto 50px;">
            <head>
            	<title>Cover letter</title>
            </head>
            <body>
            	<h1 align="center">Cover letter</h1>
            	<div align="left">
            		 <table width = "100%">
		                 <tr>
		                 	<td>
		                 	<xsl:call-template name="AuthorTemplate">
		            							<xsl:with-param name="author" select = "sp:cover_letter/sp:author" />
		            		</xsl:call-template>
		            		</td>
		                 </tr>
		              </table>
            	</div>
            	<br/>
            	<div align="left">
            		 <table width = "100%">
		                 <tr>
		                 	<td>
		                 	<xsl:call-template name="EditorTemplate">
		            							<xsl:with-param name="editor" select = "sp:cover_letter/sp:editor" />
		            		</xsl:call-template>
		            		</td>
		                 </tr>
		              </table>
            	</div>
            	<br/>
            	<div align="left">
            	<xsl:value-of select = "sp:cover_letter/sp:date"></xsl:value-of>
            	</div>
            	<br/>
            	<div>
                 	<xsl:apply-templates select="sp:cover_letter/sp:content"></xsl:apply-templates>
                 </div>
                 <br></br>
                 <div align="left">
                 	<i><xsl:value-of select = "sp:cover_letter/sp:signature/sp:name"/></i>,<br/>
                 	<xsl:value-of select = "sp:cover_letter/sp:signature/sp:degree"/>,<br/>
                 	<xsl:value-of select = "sp:cover_letter/sp:signature/sp:department"/><br></br>
                 	<xsl:value-of select = "sp:cover_letter/sp:signature/sp:university_name"/><br/> 	
                 </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>