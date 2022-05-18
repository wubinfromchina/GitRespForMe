package com.wb.service.impl;

import com.wb.mapper.ClueRemarkMapper;
import com.wb.pojo.ClueRemark;
import com.wb.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clueRemarkService")
public class ClueRemarkServiceImpl implements ClueRemarkService {
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Override
    public List<ClueRemark> queryRemarkForDetail(String id) {
        return clueRemarkMapper.selectRemarkForDetail(id);
    }

    @Override
    public int saveClueRemark(ClueRemark clueRemark) {
        return clueRemarkMapper.insertClueRemark(clueRemark);
    }
}
