package com.chards.committee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * (PsychologicalCounsellingCase)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("psychological_counselling_case")
public class PsychologicalCounsellingCase extends Model<PsychologicalCounsellingCase> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	@TableField("stu_num")
	private String stuNum;

	@TableField("counselling_time")
	private Date counsellingTime;  //咨询记录  前端传递

	@TableField("basic_judgment")
	private String basicJudgment;

	@TableField("type")
	private String type;

	@TableField("referral")
	private String referral;

	@TableField("has_diagnosis")
	private Boolean hasDiagnosis;

	@TableField("diagnosis_and_advice")
	private String diagnosisAndAdvice;

	@TableField("counselor")
	private String counselor;   // 咨询师

	@TableField("attention_level")
	private String attentionLevel;

	@TableField("is_first_time")
	private Boolean isFirstTime;

	@TableField("is_finished")
	private Boolean isFinished;

	@TableField("result")
	private String result;

	@TableField("recorder")
	private String recorder;  // 填写者，工号

	@TableField("recorded_time")
	private LocalDateTime recordedTime;

	/**
	 * 获取主键值
	 *
	 * @return 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}