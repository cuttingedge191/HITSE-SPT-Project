package com.hit.spt.service;

public interface OrderItemService {
    boolean checkIfExits(Integer i_od);

    Integer genOrderId();
}
