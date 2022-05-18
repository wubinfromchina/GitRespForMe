package com.wb.service.impl;

import com.wb.mapper.TranRemarkMapper;
import com.wb.pojo.TranRemark;
import com.wb.service.TranRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tranRemarkService")
public class TranRemarkServiceImpl implements TranRemarkService {
    @Autowired
    private TranRemarkMapper tranRemarkMapper;
    @Override
    public List<TranRemark> queryTranRemarkByTranId(String tranId) {
        return tranRemarkMapper.selectTranRemarkByTranId(tranId);
    }
}
