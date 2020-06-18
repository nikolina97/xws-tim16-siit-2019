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
    
    <xsl:template match="sp:formula">
        <code>
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </code>
    </xsl:template>
    
    <xsl:template match="sp:quote">
        <blockquote text-align="center">   
             <i><xsl:apply-templates select="./sp:content"></xsl:apply-templates> </i>&#160; - &#160;
             <xsl:apply-templates select="./sp:from"/>
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
            <table style="border: 1px solid black; text-align:center">
            	<xsl:for-each select="./sp:th">
                    <th style="border: 1px solid black">
                        <xsl:apply-templates select="."></xsl:apply-templates>
                    </th>
                </xsl:for-each>
                <xsl:for-each select="./sp:tr">
                    <tr style="border: 1px solid black">
                        <xsl:for-each select="./sp:td">
                            <td style="border: 1px solid black">
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
			  <img style= 'max-width: 400px; max-height:400px '>
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
        <xsl:attribute name="href">#[<xsl:value-of select="."/>]</xsl:attribute>
		<xsl:value-of select="."/>
		</a>)
    </xsl:template>
    
    <xsl:template name="ChapterTemplate">
     	<xsl:param name="chapter"/>
	       	<h2 align="left"><xsl:value-of select="$chapter/sp:subtitle"/></h2>
	       	<xsl:for-each select="$chapter/sp:paragraph">
	     	<xsl:apply-templates></xsl:apply-templates>
	     </xsl:for-each>
	     <xsl:for-each select="$chapter/sp:subchapter">
	     		
               <xsl:call-template name="SubchapterTemplate">
                       <xsl:with-param name="subchapter" select = "." />
                </xsl:call-template>
         </xsl:for-each>
	</xsl:template>
	
	<xsl:template name="SubchapterTemplate">
     	<xsl:param name="subchapter"/>
	   	<xsl:choose>
	       	<xsl:when test="name(..)='sp:chapter'">
	       		<h3 align="left"><xsl:value-of select="$subchapter/sp:subtitle"/></h3>
	     	</xsl:when>
	     	<xsl:when test="name(..)='sp:subchapter'">
	       		<h4 align="left"><xsl:value-of select="$subchapter/sp:subtitle"/></h4>
	     	</xsl:when>
	     </xsl:choose>
	     <xsl:for-each select="$subchapter/sp:paragraph">
	     	<xsl:apply-templates></xsl:apply-templates>
	     </xsl:for-each>
	     <xsl:for-each select="$subchapter/sp:subchapter">
               <xsl:call-template name="SubchapterTemplate">
                       <xsl:with-param name="subchapter" select = "." />
                </xsl:call-template>
         </xsl:for-each>
	</xsl:template>
	
	<xsl:template name="ReferenceTemplate">
     	<xsl:param name="reference"/>
	   	<div align="left">
	   	<xsl:attribute name="id"><xsl:value-of select="$reference/sp:ref_number"/>
		</xsl:attribute>
	   	<xsl:value-of select="$reference/sp:ref_number"></xsl:value-of>&#160;
	   	<xsl:value-of select="$reference/sp:ref_author"></xsl:value-of>,&#160;
	   	<a>
	   	<xsl:attribute name="href">http://localhost:8081/api/paper/getPDFById/<xsl:value-of select="substring-after($reference/@href, '.rs/paper/')"/>
		</xsl:attribute>
	   	<xsl:value-of select="$reference/sp:article_name"></xsl:value-of>,&#160;</a>
	   	<xsl:value-of select="$reference/sp:year"></xsl:value-of>&#160;
		
	   	</div>
	</xsl:template>
    
    
   	<xsl:template name="EditorTemplate">
	        <xsl:param name="editor"/>
	            <xsl:value-of select="$editor/sp:name"/><br/>
	            <xsl:value-of select="$editor/sp:title"/><br/>
	            <xsl:value-of select="$editor/sp:journal_name"/>
	</xsl:template>
    
</xsl:stylesheet>
