package com.hit.spt.mapper;


import com.hit.spt.pojo.GoodsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsInfoMapper {
    Integer insertGoodsInfo(GoodsInfo goodsInfo);

    Integer updateGoodsInfo(GoodsInfo goodsInfo);

    GoodsInfo queryGoodsInfoByName(String name);

    GoodsInfo queryGoodsInfoByGid(Integer g_id);

    Integer deleteGoodsInfoByGid(Integer g_id);

    List<GoodsInfo> queryGoodsInfoList();
}
