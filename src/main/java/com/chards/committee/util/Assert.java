package com.chards.committee.util;

import com.chards.committee.config.BusinessException;
import com.chards.committee.vo.Code;

/**
 * @author 远 chards_
 * @FileName:Assert
 * @date: 2020-07-24 10:03
 */
public class Assert {
	public static void notNull(Object object, String message) {
		if (object == null) BusinessException.error(message);
	}
	// 这里不是判断了吗
	public static void notNull(Object object, Code code) {
		if (object == null) BusinessException.error(code);
	}
}
