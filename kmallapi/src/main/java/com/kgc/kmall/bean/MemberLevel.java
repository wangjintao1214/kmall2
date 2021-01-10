package com.kgc.kmall.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel("会员等级表实体类")
public class MemberLevel implements Serializable{
    @ApiModelProperty("会员等级id")
    private Long id;
    @ApiModelProperty("会员等级名称")
    private String name;

    private Integer growthPoint;
    @ApiModelProperty("是否为默认等级：0->不是；1->是")
    private Integer defaultStatus;
    @ApiModelProperty("免运费标准")
    private BigDecimal freeFreightPoint;
    @ApiModelProperty("每次评价获取的成长值")
    private Integer commentGrowthPoint;
    @ApiModelProperty("是否有免邮特权")
    private Integer priviledgeFreeFreight;
    @ApiModelProperty("是否有签到特权")
    private Integer priviledgeSignIn;
    @ApiModelProperty("是否有评论获奖励特权")
    private Integer priviledgeComment;
    @ApiModelProperty("是否有专享活动特权")
    private Integer priviledgePromotion;
    @ApiModelProperty("是否有会员价格特权")
    private Integer priviledgeMemberPrice;
    @ApiModelProperty("是否有生日特权")
    private Integer priviledgeBirthday;

    private String note;

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

    public Integer getGrowthPoint() {
        return growthPoint;
    }

    public void setGrowthPoint(Integer growthPoint) {
        this.growthPoint = growthPoint;
    }

    public Integer getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public BigDecimal getFreeFreightPoint() {
        return freeFreightPoint;
    }

    public void setFreeFreightPoint(BigDecimal freeFreightPoint) {
        this.freeFreightPoint = freeFreightPoint;
    }

    public Integer getCommentGrowthPoint() {
        return commentGrowthPoint;
    }

    public void setCommentGrowthPoint(Integer commentGrowthPoint) {
        this.commentGrowthPoint = commentGrowthPoint;
    }

    public Integer getPriviledgeFreeFreight() {
        return priviledgeFreeFreight;
    }

    public void setPriviledgeFreeFreight(Integer priviledgeFreeFreight) {
        this.priviledgeFreeFreight = priviledgeFreeFreight;
    }

    public Integer getPriviledgeSignIn() {
        return priviledgeSignIn;
    }

    public void setPriviledgeSignIn(Integer priviledgeSignIn) {
        this.priviledgeSignIn = priviledgeSignIn;
    }

    public Integer getPriviledgeComment() {
        return priviledgeComment;
    }

    public void setPriviledgeComment(Integer priviledgeComment) {
        this.priviledgeComment = priviledgeComment;
    }

    public Integer getPriviledgePromotion() {
        return priviledgePromotion;
    }

    public void setPriviledgePromotion(Integer priviledgePromotion) {
        this.priviledgePromotion = priviledgePromotion;
    }

    public Integer getPriviledgeMemberPrice() {
        return priviledgeMemberPrice;
    }

    public void setPriviledgeMemberPrice(Integer priviledgeMemberPrice) {
        this.priviledgeMemberPrice = priviledgeMemberPrice;
    }

    public Integer getPriviledgeBirthday() {
        return priviledgeBirthday;
    }

    public void setPriviledgeBirthday(Integer priviledgeBirthday) {
        this.priviledgeBirthday = priviledgeBirthday;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}