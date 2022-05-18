package com.wb.service;

import com.wb.pojo.TranHistory;

import java.util.List;

public interface TranHistoryService {

    List<TranHistory> queryAllByTranId(String id);
}
