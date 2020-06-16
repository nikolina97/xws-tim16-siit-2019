//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.17 at 01:32:49 AM CEST 
//


package com.xws.application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TDecorator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TDecorator">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="bold" type="{https://github.com/nikolina97/xws-tim16-siit-2019}TDecorator" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="italic" type="{https://github.com/nikolina97/xws-tim16-siit-2019}TDecorator" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="underline" type="{https://github.com/nikolina97/xws-tim16-siit-2019}TDecorator" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TDecorator", propOrder = {
    "content"
})
public class TDecorator {

    @XmlElementRefs({
        @XmlElementRef(name = "italic", namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "underline", namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "bold", namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    protected List<Serializable> content;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TDecorator }{@code >}
     * {@link JAXBElement }{@code <}{@link TDecorator }{@code >}
     * {@link String }
     * {@link JAXBElement }{@code <}{@link TDecorator }{@code >}
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }

}
