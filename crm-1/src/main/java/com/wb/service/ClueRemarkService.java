package com.wb.service;

import com.wb.pojo.ClueRemark;

import java.util.List;

public interface ClueRemarkService {

    List<ClueRemark> queryRemarkForDetail(String id);

    int saveClueRemark(ClueRemark clueRemark);
}
