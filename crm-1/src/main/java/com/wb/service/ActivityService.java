package com.wb.service;


import com.wb.pojo.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    int saveActivity(Activity activity);

    List<Activity> queryByPageNoAndPageSize(Map<String,Object> map);

    Integer queryByAllCount(Map<String,Object> map);

    Integer deleteActivityByIds(String[] ids);

    Activity selectByPrimaryKey(String id);

    Integer updateActivityByAllCon(Activity activity);

    List<Activity> getAllAct();

    List<Activity> queryActByCon(String[] ids);

    Integer saveByFile(List<Activity> activityList);

    Activity queryDetailById(String id);

    List<Activity> queryActByClueId(String clueId);

    List<Activity> queryAcctByName(Map<String,Object> map);

    List<Activity> queryActForDetailByIds(String[] ids);

    List<Activity> queryActByNameClueId(Map<String,Object> map);

    List<Activity> queryActByName(String activityName);
}
