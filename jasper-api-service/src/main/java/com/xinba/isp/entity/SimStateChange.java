package com.xinba.isp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SimStateChange",namespace="http://api.jasperwireless.com/ws/schema" )
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimStateChange {
    @XmlElement(name = "previousState",namespace="http://api.jasperwireless.com/ws/schema")
    private String iccid;
    @XmlElement(name = "previousState",namespace="http://api.jasperwireless.com/ws/schema")
    private String previousState;
    @XmlElement(name = "currentState",namespace="http://api.jasperwireless.com/ws/schema")
    private String currentState;
    @XmlElement(name = "dateChanged",namespace="http://api.jasperwireless.com/ws/schema")
    private String dateChanged;
}
