package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * 旅游路线详情的卖家信息
 */
public interface SellerDao {
    Seller findbysid(int sid);

}
