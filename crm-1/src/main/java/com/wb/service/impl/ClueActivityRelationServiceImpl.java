package com.wb.service.impl;

import com.wb.mapper.ClueActivityRelationMapper;
import com.wb.pojo.ClueActivityRelation;
import com.wb.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Override
    public Integer saveClueActRelationByList(List<ClueActivityRelation> list) {
        return clueActivityRelationMapper.insertClueActRelationByList(list);
    }

    @Override
    public Integer dropClueActRelationById(ClueActivityRelation relation) {
        return clueActivityRelationMapper.deleteClueActRelationById(relation);
    }
}
