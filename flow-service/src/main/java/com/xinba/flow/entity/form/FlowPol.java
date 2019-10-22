package com.xinba.flow.entity.form;

import lombok.Data;

@Data
public class FlowPol {
    //流量池参数
    private Integer totalFlowYl;
    private Integer totalFlowJc;
    private Integer usedFlow;
    private Integer remainFlow;
}
