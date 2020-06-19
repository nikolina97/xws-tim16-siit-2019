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
		        	<fo:block text-align="center" font-size="16px" space-after="32px">
	                	Review
	          		</fo:block>
	          		<fo:block text-align="left" space-after="16px" space-before="16px">
	          		<fo:block font-size ="12px" font-weight="bold" space-after="3px">Questions:</fo:block>
	          		<fo:list-block font-size ="10px">
            			 <xsl:for-each select="sp:review/sp:questions/sp:question">
            			 <fo:list-item space-after="2px">
            			 	<fo:list-item-label start-indent="20px">
			                   <fo:block>
			                   	 <xsl:number value="position()"/>.
			                   </fo:block>
			               </fo:list-item-label>
			               <fo:list-item-body start-indent="30px">
			               		<fo:block font-weight="bold">
			                         <xsl:value-of select="./sp:question"/>
			               		</fo:block>
			               		<fo:block>
			                         <xsl:apply-templates select="./sp:answer"/>
			               		</fo:block>
			               	</fo:list-item-body>
            			 </fo:list-item>>
            			 </xsl:for-each>
            		</fo:list-block>
	          		</fo:block>
	          		<fo:block text-align="left" space-after="16px" space-before="16px">
	          		<fo:block font-size ="12px" font-weight="bold" space-after="3px">Comments:</fo:block>
	          		<fo:list-block font-size ="10px">
            			 <xsl:for-each select="sp:review/sp:comments/sp:comment">
            			 <fo:list-item space-after="2px">
            			 	<fo:list-item-label start-indent="20px">
			                   <fo:block>
			                   	 <xsl:number value="position()"/>.
			                   </fo:block>
			               </fo:list-item-label>
			               <fo:list-item-body start-indent="30px">
			               		<fo:block>
			                         <xsl:apply-templates></xsl:apply-templates>
			               		</fo:block>
			               	</fo:list-item-body>
            			 </fo:list-item>
            			 </xsl:for-each>
            		</fo:list-block>
	          		</fo:block>
	          		<fo:block text-align="left" space-after="16px" space-before="16px">
	          		<fo:block font-size ="12px" font-weight="bold" space-after="2px">Grades:</fo:block>
	          		<fo:list-block font-size ="10px">
            			 <fo:list-item space-after="2px">
            			 	<fo:list-item-label start-indent="20px">
			                   <fo:block>-</fo:block>
			               </fo:list-item-label>
			               <fo:list-item-body start-indent="30px">
			               		<fo:block>
			                         <fo:inline font-style="italic">
			                         	Introduction: 
			                         </fo:inline>
			                         <fo:inline>
			                         	<xsl:value-of select="sp:review/sp:grades/sp:introduction"/>
			                         </fo:inline>
			               		</fo:block>
			               	</fo:list-item-body>
            			 </fo:list-item>
            			  <fo:list-item space-after="2px">
            			 	<fo:list-item-label start-indent="20px">
			                   <fo:block>-</fo:block>
			               </fo:list-item-label>
			               <fo:list-item-body start-indent="30px">
			               		<fo:block>
			                         <fo:inline font-style="italic">
			                         	Conclusion: 
			                         </fo:inline>
			                         <fo:inline>
			                         	<xsl:value-of select="sp:review/sp:grades/sp:conclusion"/>
			                         </fo:inline>
			               		</fo:block>
			               	</fo:list-item-body>
            			 </fo:list-item>
            			  <fo:list-item space-after="2px">
            			 	<fo:list-item-label start-indent="20px">
			                   <fo:block>-</fo:block>
			               </fo:list-item-label>
			               <fo:list-item-body start-indent="30px">
			               		<fo:block>
			                         <fo:inline font-style="italic">
			                         	Experiment results:
			                         </fo:inline>
			                         <fo:inline>
			                         	<xsl:value-of select="sp:review/sp:grades/sp:experimentResults"/>
			                         </fo:inline>
			               		</fo:block>
			               	</fo:list-item-body>
            			 </fo:list-item>
            			  <fo:list-item font-weight="bold" font-size="11px">
            			 	<fo:list-item-label start-indent="20px">
			                   <fo:block>-</fo:block>
			               </fo:list-item-label>
			               <fo:list-item-body start-indent="30px">
			               		<fo:block>
			                         <fo:inline font-style="italic">
			                         	Final grade: 
			                         </fo:inline>
			                         <fo:inline>
			                         	<xsl:value-of select="sp:review/sp:grades/sp:finalGrade"/>
			                         </fo:inline>
			               		</fo:block>
			               	</fo:list-item-body>
            			 </fo:list-item>         			 
            		</fo:list-block>
	          		</fo:block>
	       		</fo:flow>
	    	</fo:page-sequence>
    	</fo:root>
	</xsl:template>
</xsl:stylesheet>