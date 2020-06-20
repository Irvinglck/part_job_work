package com.lck.util;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class PageInfo<T> {
    private int pageCurrent;//当前页
    private int pageSize;//每页数量
    private int totalCount;//总记录数
    private int totalPage;//一共多少页
    private List<T> data;
    private List<QuickPage> quickMap;//快速跳转
    public PageInfo(int pageCurrent, int pageSize, List<T> data) {
        this.pageCurrent = pageCurrent;
        this.pageSize = pageSize;
        this.data = data;

    }
    public PageInfo(){super();}
    public PageInfo(int pageCurrent, int pageSize, int totalCount, List<T> data) {
        this.pageCurrent = pageCurrent;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        this.data = data;

    }
}
