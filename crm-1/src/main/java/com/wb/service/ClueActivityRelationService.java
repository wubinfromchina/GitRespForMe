package com.wb.service;

import com.wb.pojo.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {

    Integer saveClueActRelationByList(List<ClueActivityRelation> list);

    Integer dropClueActRelationById(ClueActivityRelation relation);
}
