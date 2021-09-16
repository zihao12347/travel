package cn.itcast.travel.domain;

import java.util.List;

/**
 * 分页对象
 * @param <T> 将PageBean定义为泛形，可以存贮任意类型的数据
 */
public class PageBean<T> {
    private int totalpage;  //总页面———————这个需要通过数据库查询
    private int currentpage;    //当前页面
    private int totalcount;     //总记录数
    private int rows;   //每页显示的条数
    private List<T> list;  //每页的数据集合——————————每页的数据集合也需要通过查询

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalpage=" + totalpage +
                ", currentpage=" + currentpage +
                ", totalcount=" + totalcount +
                ", rows=" + rows +
                ", list=" + list +
                '}';
    }
}