package com.baomidou.common;

public class Pagination {
    private int page;       // 当前页码
    private int size;       // 每页大小
    private long total;     // 总记录数
    private int total_pages; // 总页数

    // 构造方法
    public Pagination(int page, int size, long total, int total_pages) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.total_pages = total_pages;
    }

    // Getters 和 Setters
}
