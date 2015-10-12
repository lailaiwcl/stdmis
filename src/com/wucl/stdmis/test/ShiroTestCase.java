package com.wucl.stdmis.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

import com.wucl.stdmis.util.Crypto;

public class ShiroTestCase {
	public static void main(String[] args) {

		IniSecurityManagerFactory factory = new IniSecurityManagerFactory(
				"classpath:shiro.ini");
		// 创建SecurityManager (根据配置创建SecurityManager实例)
		SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);

		//ShiroDbRealm realm = new ShiroDbRealm();
		//DefaultSecurityManager manager = new DefaultSecurityManager();

		UsernamePasswordToken token = new UsernamePasswordToken("lailaiwcl",Crypto.generatePassword("111111"));
		//token.setRememberMe(true);
		manager.authenticate(token);
		Subject currentUser = SecurityUtils.getSubject();
		boolean b = currentUser.hasRole("1");
		System.out.println(b);

		try {
			currentUser.login(token);
		} catch (UnknownAccountException uae) {
			System.out.println("1" + uae.getMessage());
		} catch (IncorrectCredentialsException ice) {
			System.out.println("2" + ice.getMessage());
		} catch (LockedAccountException lae) {
			System.out.println("3" + lae.getMessage());
		} catch (ExcessiveAttemptsException eae) {
			System.out.println("4" + eae.getMessage());
		} catch (AuthenticationException ae) {
			System.out.println("5" + ae.getMessage());
		}

	}

}
