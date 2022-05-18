package com.wb.service;

import com.wb.pojo.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {

    int saveClue(Clue clue);

    List<Clue> queryAllClueByCon(Map<String,Object> map);

    Integer queryCountByCon(Map<String,Object> map);

    Clue queryById(String id);

    /**
     * 根据用户选中的记录的主键删除记录以及相关联的备注，市场活动关联关系
     */
    void dropByIds(String[] ids);

    /**
     * 根据用户填入的信息更新记录
     * @param clue
     * @return
     */
    Integer setClueByCon(Clue clue);

    Clue queryForDetailById(String id);

    //线索转换
    void saveConvertClue(Map<String,Object> map);
}
