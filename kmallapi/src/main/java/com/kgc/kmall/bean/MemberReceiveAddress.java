package com.kgc.kmall.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("会员收货地址表实体类")
public class MemberReceiveAddress implements Serializable{
    @ApiModelProperty("会员收获地址id")
    private Long id;
    @ApiModelProperty("会员id")
    private Long memberId;
    @ApiModelProperty("收货人名称")
    private String name;
    @ApiModelProperty("电话")
    private String phoneNumber;
    @ApiModelProperty("是否为默认")
    private Integer defaultStatus;
    @ApiModelProperty("邮政编码")
    private String postCode;
    @ApiModelProperty("省份/直辖市")
    private String province;
    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("区")
    private String region;
    @ApiModelProperty("详细地址(街道)")
    private String detailAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public Integer getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }
}