package com.hit.spt.service;

import java.util.List;

public interface OverViewService {
    List<String> getInventoryOverView(Integer il_id);

    List<String> getSalesOverView(String g_id, Integer days);

    List<String> getOperationOverView();
}
