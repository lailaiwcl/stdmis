package com.wucl.stdmis.exception;

import java.text.MessageFormat;

public abstract class CommonException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法.<br>
	 * 
	 * @param message
	 *            异常信息
	 */
	public CommonException(String message) {
		super(message);
	}

	/**
	 * 构造方法.<br>
	 * 
	 * @param message
	 *            异常的格式化信息
	 * @param param
	 *            异常格式化信息的参数
	 */
	public CommonException(String message, Object[] param) {
		super(format(message, param));
	}

	/**
	 * 构造方法.<br>
	 * 
	 * @param cause
	 *            异常原因
	 */
	public CommonException(Throwable cause) {
		super(cause);
	}

	/**
	 * 构造方法.<br>
	 * 
	 * @param message
	 *            异常信息
	 * @param cause
	 *            异常原因
	 */
	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造方法.<br>
	 * 
	 * @param message
	 *            异常的格式化信息
	 * @param param
	 *            异常格式化信息的参数
	 * @param cause
	 *            异常原因
	 */
	public CommonException(String message, Object[] param, Throwable cause) {
		super(format(message, param), cause);
	}

	static String format(String message, Object[] params) {
		if (message.trim().length() > 0) {
			if (params != null && params.length > 0) {
				try {
					return new MessageFormat(message).format(params);
				} catch (Throwable t) {
					return message;
				}
			}
		}
		return message;
	}
}