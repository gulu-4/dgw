package com.chards.committee.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.chards.committee.domain.CoreAdmin;
import lombok.Data;

import java.util.*;

/**
 * @author θΏ chards_
 * @FileName:CoreAdminDTO
 * @date: 2020-08-24 21:19
 */
@Data
public class CoreAdminDTO extends CoreAdmin {

	@ExcelProperty("ζι")
	private String role;

	@ExcelIgnore
	private List<UserDataScope> userDataScopeList;

}
