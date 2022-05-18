package com.wb.service.impl;

import com.wb.mapper.ActivityRemarkMapper;
import com.wb.pojo.ActivityRemark;
import com.wb.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("activityRemarkService")
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    ActivityRemarkMapper activityRemarkMapper;
    @Override
    public List<ActivityRemark> queryAllActivityRemark(String activityId) {
        return activityRemarkMapper.selectAllActivityRemark(activityId);
    }

    @Override
    public Integer saveOneRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.insertOneRemark(activityRemark);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return activityRemarkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer setRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.updateRemark(activityRemark);
    }
}
