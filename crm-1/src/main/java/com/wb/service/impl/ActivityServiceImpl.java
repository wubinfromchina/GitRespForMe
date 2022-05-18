package com.wb.service.impl;

import com.wb.mapper.ActivityMapper;
import com.wb.pojo.Activity;
import com.wb.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Override
    public int saveActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    @Override
    public List<Activity> queryByPageNoAndPageSize(Map<String,Object> map) {

        return activityMapper.selectByPageNoAndPageSize(map);
    }

    @Override
    public Integer queryByAllCount(Map<String,Object> map) {

        return activityMapper.selectByAllCount(map);
    }

    @Override
    public Integer deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    @Override
    public Activity selectByPrimaryKey(String id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateActivityByAllCon(Activity activity) {

        return activityMapper.updateActivityByAllCon(activity);
    }

    @Override
    public List<Activity> getAllAct() {
        return activityMapper.selectAllAct();
    }

    @Override
    public List<Activity> queryActByCon(String[] ids) {
        return activityMapper.selectActByCon(ids);
    }

    @Override
    public Integer saveByFile(List<Activity> activityList) {
        return activityMapper.insertByFile(activityList);
    }

    @Override
    public Activity queryDetailById(String id) {
        return activityMapper.selectDetailById(id);
    }

    @Override
    public List<Activity> queryActByClueId(String clueId) {
        return activityMapper.selectActByClueId(clueId);
    }

    @Override
    public List<Activity> queryAcctByName(Map<String, Object> map) {
        return activityMapper.selectAcctByName(map);
    }

    @Override
    public List<Activity> queryActForDetailByIds(String[] ids) {
        return activityMapper.selectActForDetailByIds(ids);
    }

    @Override
    public List<Activity> queryActByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActByNameClueId(map);
    }

    @Override
    public List<Activity> queryActByName(String activityName) {
        return activityMapper.selectActByName(activityName);
    }
}
