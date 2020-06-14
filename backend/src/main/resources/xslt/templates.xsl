<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:sp="https://github.com/nikolina97/xws-tim16-siit-2019"
    version="2.0">
	<xsl:template name="AuthorTemplate">
	        <xsl:param name="author"/>
	            <xsl:value-of select="$author/sp:first_name"/>&#160;
	            <xsl:value-of select="$author/sp:last_name"/><br/>
	            <xsl:value-of select="$author/sp:university/sp:name"/>,
	            <xsl:value-of select="$author/sp:university/sp:city"/>,
	            <xsl:value-of select="$author/sp:university/sp:country"/><br></br>
	            <xsl:value-of select="$author/sp:email"/>
	</xsl:template>
	
	<xsl:template match="sp:paragraph">
	<p>
        <xsl:apply-templates select="."></xsl:apply-templates>
    </p>
    </xsl:template>
    
    <xsl:template match="sp:bold">
        <b>
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </b>
    </xsl:template>
    
    <xsl:template match="sp:italic">
        <i>
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </i>
    </xsl:template>
    
    <xsl:template match="sp:underline">
        <u>
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </u>
    </xsl:template>
    
    <xsl:template match="sp:quote">
        <blockquote>
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </blockquote>
   	</xsl:template>
   	
 	<xsl:template match="sp:list">
        <ul>
           <xsl:for-each select="./*">
               <li>  
                   <xsl:apply-templates select="."></xsl:apply-templates>
               </li>
           </xsl:for-each>
       </ul>
   	</xsl:template>
   	
   	
   	 <xsl:template match="sp:table">
   	 <div align="center">
            <table border="1" style="width: 100;align:center">
                <xsl:for-each select="./sp:tr">
                    <tr>
                    	<xsl:for-each select="./sp:th">
                            <th>
                                <xsl:apply-templates select="."></xsl:apply-templates>
                            </th>
                        </xsl:for-each>
                        <xsl:for-each select="./sp:td">
                            <td>
                                <xsl:apply-templates select="."></xsl:apply-templates>
                            </td>
                        </xsl:for-each>
                    </tr>
                </xsl:for-each>
                <br/>
            </table>
      </div>
    </xsl:template>
    
    <xsl:template match="sp:figure">
		 <div style="text-align:center">
		 <figure>
			  <img>
			  	<xsl:attribute name="src">
			  		<xsl:value-of select="./sp:image"/>
			  	</xsl:attribute>
			 </img>
			  <figcaption><xsl:value-of select="./sp:description"/></figcaption>
		</figure>
		</div>
		<br/>
	</xsl:template>
	
	<xsl:template match="sp:ref">
		(<a>
        <xsl:attribute name="href">
			  		"#"<xsl:value-of select="."/>
		</xsl:attribute>
		see
		</a>)
    </xsl:template>
    
    <xsl:template name="ChapterTemplate">
     	<xsl:param name="chapter"/>
	   	<xsl:choose>
	       	<xsl:when test="@indentation_level=1">
	       		<h2 align="center"><xsl:value-of select="$chapter/sp:subtitle"/></h2>
	     	</xsl:when>
	     	<xsl:when test="@indentation_level=2">
	       		<h3 align="center"><xsl:value-of select="$chapter/sp:subtitle"/></h3>
	     	</xsl:when>
	     	<xsl:when test="@indentation_level=3">
	       		<h4 align="center"><xsl:value-of select="$chapter/sp:subtitle"/></h4>
	     	</xsl:when>
	     </xsl:choose>
	     <xsl:for-each select="$chapter/sp:subchapter">
               <xsl:call-template name="SubchapterTemplate">
                       <xsl:with-param name="subchapter" select = "." />
                </xsl:call-template>
         </xsl:for-each>
	</xsl:template>
	
	<xsl:template name="SubchapterTemplate">
     	<xsl:param name="subchapter"/>
	   	<xsl:choose>
	       	<xsl:when test="@indentation_level=1">
	       		<h2 align="center"><xsl:value-of select="$subchapter/sp:subtitle"/></h2>
	     	</xsl:when>
	     	<xsl:when test="@indentation_level=2">
	       		<h3 align="center"><xsl:value-of select="$subchapter/sp:subtitle"/></h3>
	     	</xsl:when>
	     	<xsl:when test="@indentation_level=3">
	       		<h4 align="center"><xsl:value-of select="$subchapter/sp:subtitle"/></h4>
	     	</xsl:when>
	     </xsl:choose>
	     	     <xsl:for-each select="$subchapter/sp:paragraph">
	     	<xsl:apply-templates></xsl:apply-templates>
	     </xsl:for-each>
	</xsl:template>
    
    
   	<xsl:template name="EditorTemplate">
	        <xsl:param name="editor"/>
	            <xsl:value-of select="$editor/sp:name"/><br/>
	            <xsl:value-of select="$editor/sp:title"/><br/>
	            <xsl:value-of select="$editor/sp:journal_name"/>
	</xsl:template>
    
</xsl:stylesheet>
