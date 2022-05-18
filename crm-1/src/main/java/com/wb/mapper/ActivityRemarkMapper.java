package com.wb.mapper;

import com.wb.pojo.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri May 13 21:11:02 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri May 13 21:11:02 CST 2022
     */
    int insert(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri May 13 21:11:02 CST 2022
     */
    int insertSelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri May 13 21:11:02 CST 2022
     */
    ActivityRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri May 13 21:11:02 CST 2022
     */
    int updateByPrimaryKeySelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri May 13 21:11:02 CST 2022
     */
    int updateByPrimaryKey(ActivityRemark record);

    /**
     * 根据市场活动的id查看该市场活动的备注明细
     * @param activityId
     * @return
     */
    List<ActivityRemark> selectAllActivityRemark(String activityId);

    /**
     * 根据用户输入的记录添加一条市场活动备注
     * @param activityRemark
     * @return
     */
    Integer insertOneRemark(ActivityRemark activityRemark);

    /**
     * 根据用户输入的备注内容，向数据库更新该条数据
     * @param activityRemark
     * @return
     */
    Integer updateRemark(ActivityRemark activityRemark);
}