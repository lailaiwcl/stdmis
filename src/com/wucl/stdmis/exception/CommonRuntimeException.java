package com.wucl.stdmis.exception;

public abstract class CommonRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法.<br>
	 * 
	 * @param message
	 *            异常信息
	 */
	public CommonRuntimeException(String message) {
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
	public CommonRuntimeException(String message, Object[] param) {
		super(CommonException.format(message, param));
	}

	/**
	 * 构造方法.<br>
	 * 
	 * @param cause
	 *            异常原因
	 */
	public CommonRuntimeException(Throwable cause) {
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
	public CommonRuntimeException(String message, Throwable cause) {
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
	public CommonRuntimeException(String message, Object[] param,
			Throwable cause) {
		super(CommonException.format(message, param), cause);
	}
}