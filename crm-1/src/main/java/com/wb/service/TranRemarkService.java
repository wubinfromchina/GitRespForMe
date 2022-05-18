package com.wb.service;

import com.wb.pojo.TranRemark;

import java.util.List;

public interface TranRemarkService {

    List<TranRemark> queryTranRemarkByTranId(String tranId);
}
