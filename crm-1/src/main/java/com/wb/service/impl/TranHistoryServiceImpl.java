package com.wb.service.impl;

import com.wb.mapper.TranHistoryMapper;
import com.wb.pojo.TranHistory;
import com.wb.service.TranHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tranHistoryService")
public class TranHistoryServiceImpl implements TranHistoryService {
    @Autowired
    private TranHistoryMapper tranHistoryMapper;
    @Override
    public List<TranHistory> queryAllByTranId(String id) {
        return tranHistoryMapper.selectAllByTranId(id);
    }
}
