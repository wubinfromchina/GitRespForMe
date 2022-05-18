package com.wb.mapper;

import com.wb.pojo.TranHistory;

import java.util.List;

public interface TranHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Tue May 17 12:12:54 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Tue May 17 12:12:54 CST 2022
     */
    int insert(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Tue May 17 12:12:54 CST 2022
     */
    int insertSelective(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Tue May 17 12:12:54 CST 2022
     */
    TranHistory selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Tue May 17 12:12:54 CST 2022
     */
    int updateByPrimaryKeySelective(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Tue May 17 12:12:54 CST 2022
     */
    int updateByPrimaryKey(TranHistory record);

    /**
     * 保存创建交易的历史
     * @param tranHistory
     * @return
     */
    int insertTranHistory(TranHistory tranHistory);

    /**
     * 根据tranId查询所有交易历史
     * @param tranId
     * @return
     */
    List<TranHistory> selectAllByTranId(String tranId);
}