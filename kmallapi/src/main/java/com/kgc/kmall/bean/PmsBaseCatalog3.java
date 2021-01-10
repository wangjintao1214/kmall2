package com.kgc.kmall.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("三级分类表")
public class PmsBaseCatalog3 implements Serializable{
    @ApiModelProperty("编号")
    private Long id;
    @ApiModelProperty("三级分类名称")
    private String name;
    @ApiModelProperty("二级分类编号")
    private Long catalog2Id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getCatalog2Id() {
        return catalog2Id;
    }

    public void setCatalog2Id(Long catalog2Id) {
        this.catalog2Id = catalog2Id;
    }

    @Override
    public String toString() {
        return "PmsBaseCatalog3{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", catalog2Id=" + catalog2Id +
                '}';
    }
}