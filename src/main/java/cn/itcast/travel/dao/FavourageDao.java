package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavourageDao {
    /**
     * 通过tab_route表的rid和tab_user表的uid查询：tab_favourate对象，
     * 判断当前用户是否收藏当前旅游线路
     * @return
     */
    Favorite findbyridanduid(int rid, int uid);

    /**
     * 根据route的rid查询旅游线路收藏的次数
     * @param rid
     * @return
     */
    int findfavourateaccount(int rid);


    void addfavourate(int rid,int uid);
}
