package com.wb.service;

import com.wb.pojo.DicValue;

import java.util.List;

public interface DicValueService {

    List<DicValue> queryDicByTypeCode(String typeCode);
}
