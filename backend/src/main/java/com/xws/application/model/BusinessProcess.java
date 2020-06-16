//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.16 at 12:44:40 AM CEST 
//


package com.xws.application.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="scientificPaperId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="scientificPaperTitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="state" type="{https://github.com/nikolina97/xws-tim16-siit-2019}TState"/>
 *         &lt;element name="reviewAssignments">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="reviewAssignment" type="{https://github.com/nikolina97/xws-tim16-siit-2019}TReviewAssignment" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
@XmlType(name = "", propOrder = {
    "scientificPaperId",
    "scientificPaperTitle",
    "version",
    "state",
    "reviewAssignments"
})
@XmlRootElement(name = "businessProcess")
public class BusinessProcess {

    @XmlElement(required = true)
    protected String scientificPaperId;
    @XmlElement(required = true)
    protected String scientificPaperTitle;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger version;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TState state;
    @XmlElement(required = true)
    protected BusinessProcess.ReviewAssignments reviewAssignments;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the scientificPaperId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScientificPaperId() {
        return scientificPaperId;
    }

    /**
     * Sets the value of the scientificPaperId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScientificPaperId(String value) {
        this.scientificPaperId = value;
    }

    /**
     * Gets the value of the scientificPaperTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScientificPaperTitle() {
        return scientificPaperTitle;
    }

    /**
     * Sets the value of the scientificPaperTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScientificPaperTitle(String value) {
        this.scientificPaperTitle = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVersion(BigInteger value) {
        this.version = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link TState }
     *     
     */
    public TState getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link TState }
     *     
     */
    public void setState(TState value) {
        this.state = value;
    }

    /**
     * Gets the value of the reviewAssignments property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessProcess.ReviewAssignments }
     *     
     */
    public BusinessProcess.ReviewAssignments getReviewAssignments() {
        return reviewAssignments;
    }

    /**
     * Sets the value of the reviewAssignments property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessProcess.ReviewAssignments }
     *     
     */
    public void setReviewAssignments(BusinessProcess.ReviewAssignments value) {
        this.reviewAssignments = value;
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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="reviewAssignment" type="{https://github.com/nikolina97/xws-tim16-siit-2019}TReviewAssignment" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "reviewAssignment"
    })
    public static class ReviewAssignments {

        protected List<TReviewAssignment> reviewAssignment;

        /**
         * Gets the value of the reviewAssignment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the reviewAssignment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReviewAssignment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TReviewAssignment }
         * 
         * 
         */
        public List<TReviewAssignment> getReviewAssignment() {
            if (reviewAssignment == null) {
                reviewAssignment = new ArrayList<TReviewAssignment>();
            }
            return this.reviewAssignment;
        }

    }

}
