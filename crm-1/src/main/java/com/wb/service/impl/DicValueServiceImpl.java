package com.wb.service.impl;

import com.wb.mapper.DicValueMapper;
import com.wb.pojo.DicValue;
import com.wb.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dicValueService")
public class DicValueServiceImpl implements DicValueService {
    @Autowired
    private DicValueMapper dicValueMapper;

    @Override
    public List<DicValue> queryDicByTypeCode(String typeCode) {
        return dicValueMapper.selectDicByTypeCode(typeCode);
    }
}
