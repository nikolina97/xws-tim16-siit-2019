//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.15 at 09:09:19 PM CEST 
//


package com.xws.application.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TReviewAssignment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TReviewAssignment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{https://github.com/nikolina97/xws-tim16-siit-2019}reviewer"/>
 *         &lt;element name="status" type="{https://github.com/nikolina97/xws-tim16-siit-2019}TReviewAssignementState"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TReviewAssignment", propOrder = {
    "reviewer",
    "status"
})
public class TReviewAssignment {

    @XmlElement(required = true)
    protected TPerson reviewer;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TReviewAssignementState status;

    /**
     * Gets the value of the reviewer property.
     * 
     * @return
     *     possible object is
     *     {@link TPerson }
     *     
     */
    public TPerson getReviewer() {
        return reviewer;
    }

    /**
     * Sets the value of the reviewer property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPerson }
     *     
     */
    public void setReviewer(TPerson value) {
        this.reviewer = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link TReviewAssignementState }
     *     
     */
    public TReviewAssignementState getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link TReviewAssignementState }
     *     
     */
    public void setStatus(TReviewAssignementState value) {
        this.status = value;
    }

}
