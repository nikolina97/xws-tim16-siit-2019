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
	              margin-left="2.5cm"
	              margin-right="2.5cm">
	              <fo:region-body margin="0.6in"/>
	          </fo:simple-page-master>
	      </fo:layout-master-set>
	      <fo:page-sequence master-reference="sp-page">
	          <fo:flow flow-name="xsl-region-body">
	          <fo:block text-align="center" font-size="24px">
	                  <xsl:value-of select="sp:scientific_paper/sp:title"/>
	          </fo:block>
	          <fo:block text-align="center" font-size="11px" space-after="16px" space-before="16px"> 
	          	<fo:table width="100%">
	          		<fo:table-body>
	                 	<xsl:for-each select="sp:scientific_paper/sp:authors/sp:author">
		                 	<fo:table-row>
		                 	<fo:table-cell>
		                 	<xsl:call-template name="AuthorTemplate">
		            							<xsl:with-param name="author" select = "." />
		            		</xsl:call-template>
		            		</fo:table-cell>
	                	</fo:table-row>
	                	</xsl:for-each>
                 	</fo:table-body>
                 </fo:table>
                </fo:block>
                <fo:block space-after="14px" space-before="16px" font-size="11px">
                        <fo:block font-weight="bold">
                            Abstract
                        </fo:block>
                        <xsl:for-each select="sp:scientific_paper/sp:abstract/sp:paragraph">
                            <xsl:apply-templates/>
                        </xsl:for-each>
                    </fo:block>
                    <fo:block space-after="16px" font-size="12px">
                        <fo:inline font-weight="bold">
                            Keywords:
                        </fo:inline>
                        <fo:inline font-style="italic"><xsl:for-each select="sp:scientific_paper/sp:abstract/sp:keywords/sp:keyword">
                            <xsl:value-of select="."/><xsl:if test="position()!=last()">, </xsl:if>
                        </xsl:for-each></fo:inline>
                    </fo:block>
                    <fo:block space-after="16px" space-before="16px" font-size="11px">
                    <xsl:for-each select="sp:scientific_paper/sp:chapters/sp:chapter">
                        <xsl:call-template name="ChapterTemplate">
                        <xsl:with-param name="chapter" select = "." />
                   	 	</xsl:call-template>
                    </xsl:for-each>
                    </fo:block>
                   	<fo:block font-size="18px">References</fo:block>
                   	<fo:block font-size="12px">
                    <xsl:for-each select="sp:scientific_paper/sp:references/sp:reference">
                        <xsl:call-template name="ReferenceTemplate">
                        <xsl:with-param name="reference" select = "." />
                   	 	</xsl:call-template>
                    </xsl:for-each>
                    </fo:block>
	          </fo:flow>
	       </fo:page-sequence>
    	</fo:root>
    </xsl:template>
</xsl:stylesheet>