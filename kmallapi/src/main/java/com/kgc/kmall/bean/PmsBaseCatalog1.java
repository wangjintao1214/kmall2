package com.kgc.kmall.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("一级分类表")
public class PmsBaseCatalog1 implements Serializable{
    @ApiModelProperty("编号")
    private Integer id;
    @ApiModelProperty("分类名称")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "PmsBaseCatalog1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}