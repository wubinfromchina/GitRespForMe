package com.wb.service;

import com.wb.pojo.FunnelVO;
import com.wb.pojo.Tran;

import java.util.List;
import java.util.Map;

public interface TranService {

    List<Tran> queryAllTranByCon(Map<String,Object> map);

    int queryCountByCon(Map<String,Object> map);

    void saveTran(Map<String,Object> map);

    void editTran(Map<String,Object> map);

    Tran queryTranById(String id);

    Tran queryCreateBy(String id);

    List<FunnelVO> queryCountTranGroupByStage();

}
