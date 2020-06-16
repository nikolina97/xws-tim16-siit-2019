//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.16 at 12:44:40 AM CEST 
//


package com.xws.application.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TChapter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TChapter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="subtitle">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="paragraph" type="{https://github.com/nikolina97/xws-tim16-siit-2019}TParagraph" maxOccurs="unbounded"/>
 *           &lt;element name="subchapter" type="{https://github.com/nikolina97/xws-tim16-siit-2019}TChapter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TChapter", propOrder = {
    "subtitle",
    "paragraphOrSubchapter"
})
public class TChapter {

    @XmlElement(required = true)
    protected String subtitle;
    @XmlElements({
        @XmlElement(name = "paragraph", type = TParagraph.class),
        @XmlElement(name = "subchapter", type = TChapter.class)
    })
    protected List<Object> paragraphOrSubchapter;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the subtitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Sets the value of the subtitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubtitle(String value) {
        this.subtitle = value;
    }

    /**
     * Gets the value of the paragraphOrSubchapter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paragraphOrSubchapter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParagraphOrSubchapter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TParagraph }
     * {@link TChapter }
     * 
     * 
     */
    public List<Object> getParagraphOrSubchapter() {
        if (paragraphOrSubchapter == null) {
            paragraphOrSubchapter = new ArrayList<Object>();
        }
        return this.paragraphOrSubchapter;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
