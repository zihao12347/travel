package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDaoImpl categoryDao=new CategoryDaoImpl();
    @Override
    public List<Category> findall() {
        //为对分类数据进行优化缓存，这里采用redis，从redis中查询
        //1.获取Jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //2.1查询数据：这里期望数据库中的数据存储顺序为展示数据的顺序
            //因此我们可以用sortedset:有序集合类型查询
       // Set<String> sortedset = jedis.zrange("category", 0, -1 );
        //查询sortedset集合中的分数(cid)和值(cname)
        Set<Tuple> sortedset = jedis.zrangeWithScores("category", 0, -1);


        //声明一个list数组
        List<Category> categoryList=null;
        //3.判断category集合是否为空
        if (sortedset==null||sortedset.size()==0){
            //4.如果为空：第一次查询，则从mysql数据库中查询
            categoryList = categoryDao.findall();
                //并将从mysql数据库查询的数据遍历存入redis中
            for (int i = 0; i <categoryList.size() ; i++) {
                jedis.zadd("category",categoryList.get(i).getCid(),categoryList.get(i).getCname());
            }
        }else {
            //5.如果不为空的话。直接redis中的集合:由于redis数据中的集合为sortedset，但是返回的数据为List，这里我们要遍历将set数据保存成list数据
            //实例化list数组
            categoryList=new ArrayList<Category>();
            //创建Category对象
            for (Tuple tuple : sortedset) {
                Category category = new Category();
                //设置cname,tuple.getElement()就是获取数组中封装的值cname
                category.setCname(tuple.getElement());
                //设置cid，tuple。getCid()就是获取数组中的分数cid
                category.setCid((int) tuple.getScore());
                categoryList.add(category);

            }
        }
        return categoryList;
    }
}
