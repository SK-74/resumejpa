package com.resumejpa.entity;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "resumes")
public class Resume {

	/** ID */    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** 種別（学歴・職歴） */
	private Integer typ;
	
	/** 年月(DB登録用) */
	private LocalDate ym;

	/** 年月(リクエスト用) */
	@Transient//SQLで使用しない
	@NotNull(message="{err.msg.required}")
	private YearMonth requestYm;

	/** 経歴 */
	@NotEmpty(message="{err.msg.required}")
	private String content;

	/** member.id*/
	@ManyToOne
    @JoinColumn(name="member_id", referencedColumnName="id")//外部参照キー
	private Member member;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTyp() {
		return typ;
	}

	public void setTyp(Integer typ) {
		this.typ = typ;
	}

	public LocalDate getYm() {
		return ym;
	}

	public void setYm(LocalDate ym) {
		this.ym = ym;
	}

	public YearMonth getRequestYm() {
		return requestYm;
	}

	public void setRequestYm(YearMonth requestYm) {
		this.requestYm = requestYm;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public String getTypStr() {
		String ret = "";
		if(this.typ == 1) {
			ret = "学歴";
		}else if(this.typ == 2){
			ret = "職歴";
		}
		return ret;
	}

	public String getDtYM() {
		//resumeList.htmlのtableのカラムに年月だけ表示するために形式を整える
		DateTimeFormatter dtm = DateTimeFormatter.ofPattern("yyyy-MM");
		return ym.format(dtm);
	}
}
