//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.18 at 03:35:55 PM CEST 
//


package com.xws.application.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="accepted"/>
 *     &lt;enumeration value="rejected"/>
 *     &lt;enumeration value="revised"/>
 *     &lt;enumeration value="revoked"/>
 *     &lt;enumeration value="onReview"/>
 *     &lt;enumeration value="published"/>
 *     &lt;enumeration value="onRevise"/>
 *     &lt;enumeration value="submitted"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TState")
@XmlEnum
public enum TState {

    @XmlEnumValue("accepted")
    ACCEPTED("accepted"),
    @XmlEnumValue("rejected")
    REJECTED("rejected"),
    @XmlEnumValue("revised")
    REVISED("revised"),
    @XmlEnumValue("revoked")
    REVOKED("revoked"),
    @XmlEnumValue("onReview")
    ON_REVIEW("onReview"),
    @XmlEnumValue("published")
    PUBLISHED("published"),
    @XmlEnumValue("onRevise")
    ON_REVISE("onRevise"),
    @XmlEnumValue("submitted")
    SUBMITTED("submitted");
    private final String value;

    TState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TState fromValue(String v) {
        for (TState c: TState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
