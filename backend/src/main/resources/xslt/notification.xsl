<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:sp="https://github.com/nikolina97/xws-tim16-siit-2019"
    version="2.0">
    <xsl:import href="templates.xsl"/>
    <xsl:template match="/">
    	 <html style="text-align: justify; margin: auto 50px auto 50px;">
            <head>
            	<title>Notification</title>
            </head>
            <body>
            	<h1 align="center">Notification</h1>
            	<div align="left">
            		<b>From</b>: <xsl:value-of select = "sp:notification/sp:from/sp:fromEmail"/><br/> 
            		<b>To</b>: <xsl:value-of select = "sp:notification/sp:to/sp:toEmail"/><br/>
            		<b>Date</b>: <xsl:value-of select = "sp:notification/@date"/><br/><br/> 
            		<xsl:value-of select = "sp:notification/sp:text"/> 
            	</div>
            </body>
          </html>
    </xsl:template>
</xsl:stylesheet>