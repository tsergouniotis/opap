package ts.opap.joker.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ts.opap.joker.dto.CustomDeserializer;

@Entity
@Table(name = "draws")
@JsonDeserialize(using = CustomDeserializer.class)
@NamedQueries({ @NamedQuery(name = "Draw.lastDrawNumber", query = "select max(d.drawNo) from Draw d") })

public class Draw implements Serializable {

	@Column(name = "draw_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss", timezone = "UTC")
	private Date drawTime;

	@Id
	@Column(name = "draw_number")
	private Integer drawNo;

	@Column(name = "number_1")
	private int number1;

	@Column(name = "number_2")
	private int number2;

	@Column(name = "number_3")
	private int number3;

	@Column(name = "number_4")
	private int number4;

	@Column(name = "number_5")
	private int number5;

	@Column(name = "joker")
	private int joker;

	public Date getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}

	public Integer getDrawNo() {
		return drawNo;
	}

	public void setDrawNo(Integer drawNo) {
		this.drawNo = drawNo;
	}

	public int getNumber1() {
		return number1;
	}

	public void setNumber1(int number1) {
		this.number1 = number1;
	}

	public int getNumber2() {
		return number2;
	}

	public void setNumber2(int number2) {
		this.number2 = number2;
	}

	public int getNumber3() {
		return number3;
	}

	public void setNumber3(int number3) {
		this.number3 = number3;
	}

	public int getNumber4() {
		return number4;
	}

	public void setNumber4(int number4) {
		this.number4 = number4;
	}

	public int getNumber5() {
		return number5;
	}

	public void setNumber5(int number5) {
		this.number5 = number5;
	}

	public int getJoker() {
		return joker;
	}

	public void setJoker(int joker) {
		this.joker = joker;
	}

}
