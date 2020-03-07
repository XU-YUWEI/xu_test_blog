package com.xu.kinggame.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xu.kinggame.entity.User;
import com.xu.kinggame.service.AdminService;
import com.xu.kinggame.util.MD5Util;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Resource
	private AdminService adminService;
	
	@GetMapping("/login")
	public String login() {
		
		return "admin/login";
	}
	
	@GetMapping({"","/","/index","/index.html"})
	public String index(HttpServletRequest request) {
		request.setAttribute("path", "index");
		return "admin/index";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("userName") String username,@RequestParam("password") String password,@RequestParam("verifyCode") String verifyCode,HttpSession session) {
		if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)) {
			session.setAttribute("errorMsg", "用户名或密码不能为空");
			return "admin/login";
		}
		if(StringUtils.isEmpty(verifyCode)) {
			session.setAttribute("errorMsg", "验证码不能为空");
			return "admin/login";
		}
		String kaptchaCode = session.getAttribute("verifyCode") + "";
		if(StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
			session.setAttribute("errorMsg", "验证码错误");
			return "admin/login";
		}
		String passwordmd5 = MD5Util.MD5Encode(password, "UTF-8");
		User user = adminService.selectUser(username, passwordmd5);
		if(user==null) {
			session.setAttribute("errorMsg", "找不到用户，请注册");
			return "admin/login";
		}else {
		/*System.out.println(user);*/
		session.setAttribute("loginUserId", user.getAdminId());
		session.setAttribute("loginUser", user.getAdminnickname());
		return "redirect:/admin/index";
		}
	}
	
	@GetMapping("/register")
	public String register() {
		return "admin/register";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam("userNickName") String usernickname,@RequestParam("userName") String username,@RequestParam("password") String password,@RequestParam("repassword") String repassword,HttpSession session) {
		if(StringUtils.isEmpty(usernickname)) {
			session.setAttribute("errorMsg1", "昵称不能为空");
			return "admin/register";
		}
		if(StringUtils.isEmpty(username)) {
			session.setAttribute("errorMsg1", "账号不能为空");
			return "admin/register";
		}
		if(StringUtils.isEmpty(password)&&StringUtils.isEmpty(repassword)) {
			session.setAttribute("errorMsg1", "密码不能为空");
			return "admin/register";
		}
		if(!password.equals(repassword)) {
			session.setAttribute("errorMsg1", "两次密码不一样");
			return "admin/register";
		}
		/*System.out.println(usernickname+" "+username+" "+password+" "+repassword);*/
		
		User user = new User();
		user.setAdminnickname(usernickname);
		user.setLoginname(username);
		user.setLoginpassword(password);
		if(adminService.addUser(user)) {
			return "admin/login";
		}
		session.setAttribute("errorMsg1", "注册失败");
		return "admin/register";
	}
	
	@GetMapping("/profile")
	public String profie(HttpServletRequest request) {
		Long loginId = (long)request.getSession().getAttribute("loginUserId");
		User user = adminService.selectUserById(loginId);
		if(user==null) {
			return "/admin/login";
		}
		
		request.setAttribute("path", "profile");
		request.setAttribute("loginUserName", user.getLoginname());
		request.setAttribute("nickName", user.getAdminnickname());
		return "/admin/profile";
			
	}
	
	@PostMapping("/profile/password")
	@ResponseBody
	public String password(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
            @RequestParam("newPassword") String newPassword) {
		if(StringUtils.isEmpty(originalPassword)||StringUtils.isEmpty(newPassword)) {
			return "请输入密码";
		}
		Long loginId = (long)request.getSession().getAttribute("loginUserId");
		if(adminService.updatePassword(loginId,originalPassword,newPassword)) {
			request.getSession().removeAttribute("errorMsg");
			request.getSession().removeAttribute("loginUserId");
			request.getSession().removeAttribute("loginUser");
			return "success";
		}else {
			return "修改失败";
		}
	}
	
	@PostMapping("/profile/name")
	@ResponseBody
	public String name(HttpServletRequest request, @RequestParam("loginUserName") String loginUserName,
            @RequestParam("nickName") String nickName) {
		if(StringUtils.isEmpty(loginUserName)||StringUtils.isEmpty(nickName)) {
			return "请输入昵称";
		}
		Long loginId = (long)request.getSession().getAttribute("loginUserId");
		if(adminService.updateName(loginId,loginUserName,nickName)) {
			return "success";
		}else {
			return "修改失败";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute("errorMsg");
		request.getSession().removeAttribute("loginUserId");
		request.getSession().removeAttribute("loginUser");
		return "/admin/login";
	}
	
}
