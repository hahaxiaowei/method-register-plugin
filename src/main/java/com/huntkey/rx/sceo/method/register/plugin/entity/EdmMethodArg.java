package com.huntkey.rx.sceo.method.register.plugin.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class EdmMethodArg {

	private String id;	// 主键id

	private String edmaEdmmId;	// 方法id

	@NotNull
	private Byte edmaType;	// 输入/返回

	@NotNull(message = "参数类型不能为空")
	private String edmaDataType;// 参数数据类型

	@NotNull(message = "参数名称不能为空")
	private String edmaName;// 参数名称

	private String edmaDesc;// 参数描述

	private Integer edmaSeq;// 排序

	private Byte isDel;// 是否删除状态位

	private Date addtime;// 创建时间

	private String adduser;// 创建人

	private Date modtime;// 修改时间

	private String moduser;// 修改人

	private Byte acctid;// 账号标示

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getEdmaEdmmId() {
		return edmaEdmmId;
	}

	public void setEdmaEdmmId(String edmaEdmmId) {
		this.edmaEdmmId = edmaEdmmId == null ? null : edmaEdmmId.trim();
	}

	public Byte getEdmaType() {
		return edmaType;
	}

	public void setEdmaType(Byte edmaType) {
		this.edmaType = edmaType;
	}

	public String getEdmaDataType() {
		return edmaDataType;
	}

	public void setEdmaDataType(String edmaDataType) {
		this.edmaDataType = edmaDataType == null ? null : edmaDataType.trim();
	}

	public String getEdmaName() {
		return edmaName;
	}

	public void setEdmaName(String edmaName) {
		this.edmaName = edmaName == null ? null : edmaName.trim();
	}

	public String getEdmaDesc() {
		return edmaDesc;
	}

	public void setEdmaDesc(String edmaDesc) {
		this.edmaDesc = edmaDesc == null ? null : edmaDesc.trim();
	}

	public Integer getEdmaSeq() {
		return edmaSeq;
	}

	public void setEdmaSeq(Integer edmaSeq) {
		this.edmaSeq = edmaSeq;
	}

	public Byte getIsDel() {
		return isDel;
	}

	public void setIsDel(Byte isDel) {
		this.isDel = isDel;
	}

	public Date getAddtime() {
		if (null != this.addtime) {
			return (Date)this.addtime.clone();
		} else {
			return null;
		}
	}

	public void setAddtime(Date addtime) {
		if (null != addtime) {
			this.addtime = (Date) addtime.clone();
		} else {
			this.addtime = null;
		}
	}

	public String getAdduser() {
		return adduser;
	}

	public void setAdduser(String adduser) {
		this.adduser = adduser == null ? null : adduser.trim();
	}

	public Date getModtime() {
		if (null != this.modtime) {
			return (Date) this.modtime.clone();
		} else {
			return null;
		}
	}

	public void setModtime(Date modtime) {
		if (null != modtime) {
			this.modtime = (Date) modtime.clone();
		} else {
			this.modtime = null;
		}
	}

	public String getModuser() {
		return moduser;
	}

	public void setModuser(String moduser) {
		this.moduser = moduser == null ? null : moduser.trim();
	}

	public Byte getAcctid() {
		return acctid;
	}

	public void setAcctid(Byte acctid) {
		this.acctid = acctid;
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\", \"edmaEdmmId\":\"" + edmaEdmmId + "\", \"edmaType\":\"" + edmaType
				+ "\", \"edmaDataType\":\"" + edmaDataType + "\", \"edmaName\":\"" + edmaName + "\", \"edmaDesc\":\""
				+ edmaDesc + "\", \"edmaSeq\":\"" + edmaSeq + "\", \"isDel\":\"" + isDel + "\", \"addtime\":\""
				+ addtime + "\", \"adduser\":\"" + adduser + "\", \"modtime\":\"" + modtime + "\", \"moduser\":\""
				+ moduser + "\", \"acctid\":\"" + acctid + "\"} ";
	}

}