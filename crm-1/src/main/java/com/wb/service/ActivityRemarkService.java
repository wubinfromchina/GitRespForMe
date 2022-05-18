package com.wb.service;

import com.wb.pojo.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {

    List<ActivityRemark> queryAllActivityRemark(String activityId);

    Integer saveOneRemark(ActivityRemark activityRemark);

    int deleteByPrimaryKey(String id);

    Integer setRemark(ActivityRemark activityRemark);
}
