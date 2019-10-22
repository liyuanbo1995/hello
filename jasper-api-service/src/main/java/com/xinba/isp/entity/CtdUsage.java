package com.xinba.isp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CtdUsage",namespace="http://api.jasperwireless.com/ws/schema" )
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CtdUsage {
    @XmlElement(name = "iccid",namespace="http://api.jasperwireless.com/ws/schema")
    private String iccid;
    @XmlElement(name = "dataUsage",namespace="http://api.jasperwireless.com/ws/schema")
    private String dataUsage;
}
