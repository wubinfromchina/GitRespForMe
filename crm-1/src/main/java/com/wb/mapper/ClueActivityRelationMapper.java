package com.wb.mapper;

import com.wb.pojo.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Sun May 15 16:26:33 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Sun May 15 16:26:33 CST 2022
     */
    int insert(ClueActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Sun May 15 16:26:33 CST 2022
     */
    int insertSelective(ClueActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Sun May 15 16:26:33 CST 2022
     */
    ClueActivityRelation selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Sun May 15 16:26:33 CST 2022
     */
    int updateByPrimaryKeySelective(ClueActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Sun May 15 16:26:33 CST 2022
     */
    int updateByPrimaryKey(ClueActivityRelation record);

    /**
     * 批量保存创建的关联关系
     * @param list
     * @return
     */
    Integer insertClueActRelationByList(List<ClueActivityRelation> list);

    /**
     * 根据主键删除线索关系
     * @param relation
     * @return
     */
    Integer deleteClueActRelationById(ClueActivityRelation relation);

    /**
     * 根据clueId查询关联关系
     * @param clueId
     * @return
     */
    List<ClueActivityRelation> selectClueActRelationByClueId(String clueId);

    /**
     * 根据线索id删除线索与市场活动的关联关系
     * @param clueId
     * @return
     */
    Integer deleteClueActRelationByClueId(String clueId);
}