package cn.itcast.travel.service;

public interface FavourageService {
    /**
     * 判断当前用户是否收藏该条旅游线路
     * @param rid
     * @param uid
     * @return
     */
    boolean isfavourate(String rid,int uid);

    void addfavourate(String rid,int uid);

}
