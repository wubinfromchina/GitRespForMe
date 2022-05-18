package com.wb.service.impl;

import com.wb.mapper.CustomerMapper;
import com.wb.mapper.TranHistoryMapper;
import com.wb.mapper.TranMapper;
import com.wb.pojo.*;
import com.wb.pojo.utils.Contents;
import com.wb.pojo.utils.DateUtil;
import com.wb.pojo.utils.UUIDUtils;
import com.wb.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("tranService")
public class TranServiceImpl implements TranService {
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Override
    public List<Tran> queryAllTranByCon(Map<String, Object> map) {

        return tranMapper.selectAllTranByCon(map);
    }

    @Override
    public int queryCountByCon(Map<String, Object> map) {
        return tranMapper.selectCountByCon(map);
    }

    @Override
    public void saveTran(Map<String, Object> map) {
        String customerName = (String) map.get("customerName");
        Customer customer = customerMapper.selectCustomerByName(customerName);
        User user = (User) map.get(Contents.LOGIN_SESSION_USER);
        if (customer==null){//判断客户是否存在，否则新建客户
            customer = new Customer();
            customer.setId(UUIDUtils.getUUID());
            customer.setOwner(user.getId());
            customer.setName(customerName);
            customer.setCreateTime(DateUtil.formatDateTime(new Date()));
            customer.setCreateBy(user.getId());
            customerMapper.insertCustomer(customer);
        }
        Tran tran = new Tran();
        tran.setStage((String)map.get("stage"));
        tran.setOwner((String)map.get("owner"));
        tran.setNextContactTime((String)map.get("nextContactTime"));
        tran.setName((String)map.get("name"));
        tran.setMoney((String)map.get("money"));
        tran.setId(UUIDUtils.getUUID());
        tran.setExpectedDate((String)map.get("expectedDate"));
        tran.setCustomerId(customer.getId());
        tran.setCreateTime(DateUtil.formatDateTime(new Date()));
        tran.setCreateBy(user.getId());
        tran.setContactSummary((String)map.get("contactSummary"));
        tran.setContactsId((String)map.get("contactsId"));
        tran.setActivityId((String)map.get("activityId"));
        tran.setDescription((String)map.get("description"));
        tran.setSource((String)map.get("source"));
        tran.setType((String)map.get("type"));
        tranMapper.insertTran(tran);
        //保存交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(DateUtil.formatDateTime(new Date()));
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setId(UUIDUtils.getUUID());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());
        tranHistoryMapper.insertTranHistory(tranHistory);
    }

    @Override
    public void editTran(Map<String, Object> map) {
        String customerName = (String) map.get("customerName");
        Customer customer = customerMapper.selectCustomerByName(customerName);
        User user = (User) map.get(Contents.LOGIN_SESSION_USER);
        if (customer==null){//判断客户是否存在，否则新建客户
            customer = new Customer();
            customer.setId(UUIDUtils.getUUID());
            customer.setOwner(user.getId());
            customer.setName(customerName);
            customer.setCreateTime(DateUtil.formatDateTime(new Date()));
            customer.setCreateBy(user.getId());
            customerMapper.insertCustomer(customer);
        }
        Tran tran = new Tran();
        tran.setStage((String)map.get("stage"));
        tran.setOwner((String)map.get("owner"));
        tran.setNextContactTime((String)map.get("nextContactTime"));
        tran.setName((String)map.get("name"));
        tran.setMoney((String)map.get("money"));
        tran.setId((String)map.get("id"));
        tran.setExpectedDate((String)map.get("expectedDate"));
        tran.setCustomerId(customer.getId());
        tran.setCreateTime((String)map.get("createTime"));
        tran.setCreateBy((String)map.get("createBy"));
        tran.setContactSummary((String)map.get("contactSummary"));
        tran.setContactsId((String)map.get("contactsId"));
        tran.setActivityId((String)map.get("activityId"));
        tran.setDescription((String)map.get("description"));
        tran.setSource((String)map.get("source"));
        tran.setType((String)map.get("type"));
        tran.setEditBy(user.getId());
        tran.setEditTime(DateUtil.formatDateTime(new Date()));
        tranMapper.updateByPrimaryKey(tran);
        //保存交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(DateUtil.formatDateTime(new Date()));
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setId(UUIDUtils.getUUID());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());
        tranHistoryMapper.insertTranHistory(tranHistory);
    }

    @Override
    public Tran queryTranById(String id) {
        return tranMapper.selectTranById(id);
    }

    @Override
    public Tran queryCreateBy(String id) {
        return tranMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<FunnelVO> queryCountTranGroupByStage() {
        return tranMapper.selectCountAllTranGroupByStage();
    }
}
