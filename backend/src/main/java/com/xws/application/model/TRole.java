//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.15 at 09:09:19 PM CEST 
//


package com.xws.application.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TRole.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TRole">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ROLE_AUTHOR"/>
 *     &lt;enumeration value="ROLE_REVIEWER"/>
 *     &lt;enumeration value="ROLE_EDITOR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TRole")
@XmlEnum
public enum TRole {

    ROLE_AUTHOR,
    ROLE_REVIEWER,
    ROLE_EDITOR;

    public String value() {
        return name();
    }

    public static TRole fromValue(String v) {
        return valueOf(v);
    }

}
