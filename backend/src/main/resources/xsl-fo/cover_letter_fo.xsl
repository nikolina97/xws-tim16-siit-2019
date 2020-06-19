<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:sp="https://github.com/nikolina97/xws-tim16-siit-2019"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    <xsl:import href="templates_fo.xsl"/>
   	<xsl:template match="/">
   		<fo:root>
	      <fo:layout-master-set>
	          <fo:simple-page-master 
	          	master-name="sp-page"
	          	page-height="29.7cm"
	              page-width="21cm"
	              margin-top="1cm"
	              margin-bottom="2cm"
	              margin-left="1.5cm"
	              margin-right="1.5cm">
	           	<fo:region-body margin="0.6in"/>
	        	</fo:simple-page-master>
	     	</fo:layout-master-set>
	      	<fo:page-sequence master-reference="sp-page">
		        <fo:flow flow-name="xsl-region-body">
		        	<fo:block text-align="center" font-size="16px" space-after="16px">
	                	Cover letter
	          		</fo:block>
	          		<fo:block text-align="left" font-size="10px" space-after="16px" space-before="16px"> 
			          	<fo:table width="100%">
			          		<fo:table-body>
			                 	<fo:table-row>
				                 	<fo:table-cell>
				                 	<xsl:call-template name="AuthorTemplate">
				            							<xsl:with-param name="author" select = "sp:cover_letter/sp:author" />
				            		</xsl:call-template>
				            		</fo:table-cell>
		                		</fo:table-row>
			               </fo:table-body>
			            </fo:table>
		        	</fo:block>
		        	<fo:block text-align="left" font-size="10px" space-after="16px" space-before="16px"> 
			          	<fo:table width="100%">
			          		<fo:table-body>
			                 	<fo:table-row>
				                 	<fo:table-cell>
				                 	<xsl:call-template name="EditorTemplate">
				            							<xsl:with-param name="editor" select = "sp:cover_letter/sp:editor" />
				            		</xsl:call-template>
				            		</fo:table-cell>
		                		</fo:table-row>
			               </fo:table-body>
			            </fo:table>
		        	</fo:block>
					<fo:block text-align="left" font-size="10px" space-after="16px" space-before="32px"> 
		        		<xsl:apply-templates select="sp:cover_letter/sp:date"></xsl:apply-templates>
		        	</fo:block>
		        	<fo:block text-align="left" font-size="10px" space-after="16px" space-before="16px"> 
                 		<xsl:apply-templates select="sp:cover_letter/sp:content"></xsl:apply-templates>
		        	</fo:block>
		        	<fo:block text-align="left" font-size="10px" space-after="16px" space-before="16px">
			        	<fo:block font-style="italic"><xsl:value-of select = "sp:cover_letter/sp:signature/sp:name"/>,</fo:block>
	                 	<fo:block><xsl:value-of select = "sp:cover_letter/sp:signature/sp:degree"/>,</fo:block>
	                 	<fo:block><xsl:value-of select = "sp:cover_letter/sp:signature/sp:department"/>,</fo:block>
	                 	<fo:block><xsl:value-of select = "sp:cover_letter/sp:signature/sp:university_name"/></fo:block> 
		        	</fo:block>
	       		</fo:flow>
	    	</fo:page-sequence>
    	</fo:root>
	</xsl:template>
</xsl:stylesheet>