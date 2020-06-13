//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.13 at 04:08:13 PM CEST 
//


package com.xws.application.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TReviewAssignementState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TReviewAssignementState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="accepted"/>
 *     &lt;enumeration value="rejected"/>
 *     &lt;enumeration value="reviewed"/>
 *     &lt;enumeration value="assigned"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TReviewAssignementState")
@XmlEnum
public enum TReviewAssignementState {

    @XmlEnumValue("accepted")
    ACCEPTED("accepted"),
    @XmlEnumValue("rejected")
    REJECTED("rejected"),
    @XmlEnumValue("reviewed")
    REVIEWED("reviewed"),
    @XmlEnumValue("assigned")
    ASSIGNED("assigned");
    private final String value;

    TReviewAssignementState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TReviewAssignementState fromValue(String v) {
        for (TReviewAssignementState c: TReviewAssignementState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}