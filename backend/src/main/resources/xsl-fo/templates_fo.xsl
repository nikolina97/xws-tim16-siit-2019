<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:sp="https://github.com/nikolina97/xws-tim16-siit-2019"
    version="2.0">
    
        <xsl:template name="AuthorTemplate">
        <xsl:param name="author"/>
        <fo:block>
            <fo:block><xsl:value-of select="$author/sp:first_name"/> &#160; <xsl:value-of select="$author/sp:last_name"/></fo:block>
            <fo:block><xsl:value-of select="$author/sp:university/sp:name"/>,
             <xsl:value-of select="$author/sp:university/sp:city"/>,
             <xsl:value-of select="$author/sp:university/sp:country"/>
             </fo:block>
            <fo:block><xsl:value-of select="$author/sp:email"/></fo:block>
        </fo:block>
    </xsl:template>
    <xsl:template name="EditorTemplate">
	        <xsl:param name="editor"/>
	        <fo:block><xsl:value-of select="$editor/sp:name"/></fo:block>,
	        <fo:block><xsl:value-of select="$editor/sp:title"/></fo:block>
	        <fo:block><xsl:value-of select="$editor/sp:journal_name"/></fo:block>
	</xsl:template>
    
    <xsl:template match="sp:paragraph">
	<fo:block font-size="11px" space-after="16px" space-before="16px">
        <xsl:apply-templates select="."></xsl:apply-templates>
    </fo:block>
    </xsl:template>
    
    
    <xsl:template match="sp:bold">
        <fo:inline font-weight="bold">
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:inline>
    </xsl:template>
    
    <xsl:template match="sp:italic">
        <fo:inline font-style="italic">
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:inline>
    </xsl:template>
    
    <xsl:template match="sp:underline">
        <fo:inline border-after-width="1pt" border-after-style="solid">
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:inline>
    </xsl:template>
    
    <xsl:template match="sp:ref">
    <fo:inline>
            (<fo:basic-link>
            <xsl:attribute name="internal-destination">[<xsl:value-of select="."/>]</xsl:attribute>
            <xsl:value-of select="."/>
            </fo:basic-link>)
    </fo:inline>
    </xsl:template>
    
   	<xsl:template match="sp:formula">
        <fo:inline font-family="Arial" space-after="16px" space-before="16px">
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:inline>
    </xsl:template>
    
    <xsl:template match="sp:quote">
    <fo:block space-after="16px" space-before="16px"  start-indent="20px">
        <fo:inline font-style="italic" >
           <xsl:apply-templates select="./sp:content"></xsl:apply-templates>&#160; - &#160;
        </fo:inline>
        <fo:inline>
          <xsl:apply-templates select="./sp:from"/>
        </fo:inline>
    </fo:block>
    </xsl:template>
    
    <xsl:template match="sp:list">
       <fo:list-block space-after="16px" space-before="16px">
	       <xsl:for-each select="./*">
	           <fo:list-item>
	               <fo:list-item-label start-indent="20px">
	                   <fo:block>-</fo:block>
	               </fo:list-item-label>
	               <fo:list-item-body start-indent="30px">
	                   <fo:block>
	                       <xsl:apply-templates select="."/>
	                   </fo:block>
	               </fo:list-item-body>
	           </fo:list-item>
	       </xsl:for-each>
       </fo:list-block>
   	</xsl:template>
   	
   	<xsl:template match="sp:table">
   	 <fo:block text-align="center" space-after="16px" space-before="16px">
            <fo:table border="1px solid black">
            <xsl:if test="./sp:th">
              <fo:table-header>
                 <fo:table-row border="1px solid black">
                 <xsl:for-each select="./sp:th">
				    <fo:table-cell border="1px solid black">
				      <fo:block font-weight="bold">
				         <xsl:apply-templates select="."></xsl:apply-templates>
				       </fo:block>
				    </fo:table-cell>
				   </xsl:for-each>
				 </fo:table-row>
                </fo:table-header>
               </xsl:if>
            <fo:table-body>
                <xsl:for-each select="./sp:tr">
                    	<fo:table-row border="1px solid black">
                        <xsl:for-each select="./sp:td">
                            <fo:table-cell border="1px solid black">
                                <fo:block>
                                       <xsl:apply-templates select="."></xsl:apply-templates>
                                </fo:block>
                            </fo:table-cell>
                        </xsl:for-each>
                        </fo:table-row>
                 </xsl:for-each>
            </fo:table-body>
            </fo:table>
      </fo:block>
    </xsl:template>
    
     <xsl:template match="sp:figure">
		 <fo:block text-align = "center" space-after="16px" space-before="16px">
		 	<fo:block>
		 		<fo:external-graphic width="100%" content-width="scale-down-to-fit" height="100%" content-height="scale-down-to-fit">
				  	<xsl:attribute name="src">
				  		<xsl:value-of select="./sp:image"/>
				  	</xsl:attribute>
				 </fo:external-graphic>
			 	<fo:inline font-size="10px"><xsl:value-of select="./sp:description"/></fo:inline>
			</fo:block>
		</fo:block>
	</xsl:template>
    
    
    <xsl:template name="ChapterTemplate">
     	<xsl:param name="chapter"/>
	    <fo:block text-align="left" font-size="18px" space-after="16px" space-before="16px"><xsl:value-of select="$chapter/sp:subtitle"/></fo:block> 
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
	       		<fo:block text-align="left" font-size="16px" space-after="16px" space-before="16px"><xsl:value-of select="$subchapter/sp:subtitle"/></fo:block>
	     	</xsl:when>
	     	<xsl:when test="name(..)='sp:subchapter'">
	       		<fo:block text-align="left" font-size="14px" space-after="16px" space-before="16px"><xsl:value-of select="$subchapter/sp:subtitle"/></fo:block>
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
	   	<fo:block text-align="left">
	   	<xsl:attribute name="id"><xsl:value-of select="$reference/sp:ref_number"/></xsl:attribute>
	   	<xsl:value-of select="$reference/sp:ref_number"></xsl:value-of>&#160;
	   	<xsl:value-of select="$reference/sp:ref_author"></xsl:value-of>,&#160;
   		<fo:basic-link>
   		<xsl:attribute name="external-destination">http://localhost:8081/api/paper/getPDFById/<xsl:value-of select="substring-after($reference/@href, '.rs/paper/')"/></xsl:attribute>
   		<xsl:value-of select="$reference/sp:article_name"></xsl:value-of>,&#160;
   		</fo:basic-link>
   		<xsl:value-of select="$reference/sp:year"></xsl:value-of>&#160;
	   	</fo:block> 
	</xsl:template>
    
 </xsl:stylesheet>