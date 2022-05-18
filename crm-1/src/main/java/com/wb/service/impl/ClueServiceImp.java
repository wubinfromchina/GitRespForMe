package com.wb.service.impl;

import com.wb.mapper.*;
import com.wb.pojo.*;
import com.wb.pojo.utils.Contents;
import com.wb.pojo.utils.DateUtil;
import com.wb.pojo.utils.UUIDUtils;
import com.wb.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("clueService")
public class ClueServiceImp implements ClueService {
    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public int saveClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public List<Clue> queryAllClueByCon(Map<String, Object> map) {
        return clueMapper.selectAllClueByCon(map);
    }

    @Override
    public Integer queryCountByCon(Map<String, Object> map) {
        return clueMapper.selectCountByCon(map);
    }

    @Override
    public Clue queryById(String id) {
        return clueMapper.selectByPrimaryKey(id);
    }

    @Override
    public void dropByIds(String[] ids) {
        for (String id : ids) {
            //删除线索下备注
            clueRemarkMapper.deleteClueRemarkByClueId(id);
            //删除线索与市场活动的关联关系
            clueActivityRelationMapper.deleteClueActRelationByClueId(id);
            //删除线索
            clueMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Integer setClueByCon(Clue clue) {
        return clueMapper.updateClueByCon(clue);
    }

    @Override
    public Clue queryForDetailById(String id) {
        return clueMapper.selectForDetailById(id);
    }

    //线索转换
    @Override
    public void saveConvertClue(Map<String, Object> map) {
        String clueId = (String)map.get("clueId");
        User user = (User) map.get(Contents.LOGIN_SESSION_USER);
        String isCreateTran = (String)map.get("isCreateTran");
        //根据id查询线索
        Clue clue = clueMapper.selectForById(clueId);
        //将关于公司的信息封装，转换到客户表中
        Customer c = new Customer();
        c.setAddress(clue.getAddress());
        c.setContactSummary(clue.getContactSummary());
        c.setCreateBy(user.getId());
        c.setDescription(clue.getDescription());
        c.setCreateTime(DateUtil.formatDateTime(new Date()));
        c.setName(clue.getCompany());
        c.setId(UUIDUtils.getUUID());
        c.setNextContactTime(clue.getNextContactTime());
        c.setOwner(user.getId());
        c.setPhone(clue.getPhone());
        c.setWebsite(clue.getWebsite());
        customerMapper.insertCustomer(c);
        //将个人信息转换搭配联系人表中
        Contacts co = new Contacts();
        co.setAddress(clue.getAddress());
        co.setAppellation(clue.getAppellation());
        co.setContactSummary(clue.getContactSummary());
        co.setCreateBy(user.getId());
        co.setCreateTime(DateUtil.formatDateTime(new Date()));
        co.setCustomerId(c.getId());
        co.setDescription(clue.getDescription());
        co.setEmail(clue.getEmail());
        co.setFullname(clue.getFullname());
        co.setId(UUIDUtils.getUUID());
        co.setJob(clue.getJob());
        co.setMphone(clue.getMphone());
        co.setNextContactTime(clue.getNextContactTime());
        co.setOwner(user.getId());
        co.setSource(clue.getSource());
        contactsMapper.insertContacts(co);

        //根据clueId查询线索下所有备注
        List<ClueRemark> clueRemarkList = clueRemarkMapper.selectClueRemarkByClueId(clueId);
        //转换所有备注到客户备注表中以及联系人备注表中
        if (clueRemarkList!=null&&clueRemarkList.size()>0){
            CustomerRemark cur = null;
            ContactsRemark ctr = null;
            List<CustomerRemark> curList = new ArrayList<>();
            List<ContactsRemark> ctrList = new ArrayList<>();
            for (ClueRemark clueRemark : clueRemarkList) {
                cur = new CustomerRemark();
                cur.setId(UUIDUtils.getUUID());
                cur.setCreateBy(clueRemark.getCreateBy());
                cur.setCreateTime(clueRemark.getCreateTime());
                cur.setEditBy(clueRemark.getEditBy());
                cur.setCustomerId(c.getId());
                cur.setEditTime(clueRemark.getEditTime());
                cur.setEditFlag(clueRemark.getEditFlag());
                cur.setNoteContent(clueRemark.getNoteContent());
                curList.add(cur);

                ctr = new ContactsRemark();
                ctr.setContactsId(co.getId());
                ctr.setCreateBy(clueRemark.getCreateBy());
                ctr.setCreateTime(clueRemark.getCreateTime());
                ctr.setEditBy(clueRemark.getEditBy());
                ctr.setEditTime(clueRemark.getEditTime());
                ctr.setEditFlag(clueRemark.getEditFlag());
                ctr.setNoteContent(clueRemark.getNoteContent());
                ctr.setId(UUIDUtils.getUUID());
                ctrList.add(ctr);
            }
            customerRemarkMapper.insertCustomerRemarkByList(curList);
            contactsRemarkMapper.insertContactsRemarkByList(ctrList);
        }

        //根据clueId查询线索与市场活动的关联关系
        List<ClueActivityRelation> carList = clueActivityRelationMapper.selectClueActRelationByClueId(clueId);
        if (carList!=null&&carList.size()>0){
            List<ContactsActivityRelation> conArList = new ArrayList<>();
            ContactsActivityRelation  conAr = null;
            for (ClueActivityRelation car : carList) {
                conAr = new ContactsActivityRelation();
                conAr.setContactsId(co.getId());
                conAr.setId(UUIDUtils.getUUID());
                conAr.setActivityId(car.getActivityId());
                conArList.add(conAr);
            }
            contactsActivityRelationMapper.insertByList(conArList);
        }

        //如果有交易,则交易表和交易备注表添加记录
        if ("true".equals(isCreateTran)){
            //交易表添加记录
            Tran tran = new Tran();
            tran.setContactsId(co.getId());
            tran.setActivityId((String)map.get("activityId"));
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtil.formatDateTime(new Date()));
            tran.setExpectedDate((String)map.get("expectedDate"));
            tran.setStage((String)map.get("stage"));
            tran.setCustomerId(c.getId());
            tran.setName((String)map.get("name"));
            tran.setId(UUIDUtils.getUUID());
            tran.setMoney((String)map.get("money"));
            tran.setOwner(user.getId());
            tranMapper.insertTran(tran);

            //注入交易备注表
            if (clueRemarkList!=null&&clueRemarkList.size()>0){
                TranRemark tranRemark = new TranRemark();
                List<TranRemark> trList = new ArrayList<>();
                for (ClueRemark clueRemark : clueRemarkList) {
                    tranRemark.setCreateBy(user.getId());
                    tranRemark.setCreateTime(clueRemark.getCreateTime());
                    tranRemark.setTranId(tran.getId());
                    tranRemark.setEditBy(clueRemark.getEditBy());
                    tranRemark.setEditTime(clueRemark.getEditTime());
                    tranRemark.setEditFlag(clueRemark.getEditFlag());
                    tranRemark.setNoteContent(clueRemark.getNoteContent());
                    tranRemark.setId(UUIDUtils.getUUID());
                    trList.add(tranRemark);
                }
                tranRemarkMapper.insertTranRemarkByList(trList);
            }
        }
        //删除线索下备注
        clueRemarkMapper.deleteClueRemarkByClueId(clueId);
        //删除线索与市场活动的关联关系
        clueActivityRelationMapper.deleteClueActRelationByClueId(clueId);
        //删除线索
        clueMapper.deleteByPrimaryKey(clueId);


    }
}
