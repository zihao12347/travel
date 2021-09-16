package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavourageDao;
import cn.itcast.travel.dao.impl.FavourageDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavourageService;

public class FavourageServiceImpl implements FavourageService {
    private FavourageDao favourageDao=new FavourageDaoImpl();
    /**
     * 判断当前用户是否收藏当前旅游路线
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isfavourate(String rid, int uid) {
        Favorite findbyridanduid = favourageDao.findbyridanduid(Integer.parseInt(rid), uid);
        if (findbyridanduid!=null) {
            return true;
        }else {
            return false;
        }

    }

    /**
     * 根据路线的rid和用户的uid完成点击添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void addfavourate(String rid, int uid) {
        favourageDao.addfavourate(Integer.parseInt(rid),uid);
    }
}
