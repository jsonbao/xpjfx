/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/tlkzzz/jeesite">JeeSite</a> All rights reserved.
 */
package com.tlkzzz.jeesite.modules.test.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlkzzz.jeesite.common.config.Global;
import com.tlkzzz.jeesite.common.utils.FreeMarkers;
import com.tlkzzz.jeesite.common.utils.IdGen;
import com.tlkzzz.jeesite.common.utils.StringUtils;
import com.tlkzzz.jeesite.common.web.BaseController;
import com.tlkzzz.jeesite.modules.sys.entity.User;
import com.tlkzzz.jeesite.modules.sys.utils.DictUtils;
import com.tlkzzz.jeesite.modules.sys.utils.UserUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tlkzzz.jeesite.common.persistence.Page;
import com.tlkzzz.jeesite.modules.test.entity.Test;
import com.tlkzzz.jeesite.modules.test.service.TestService;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试Controller
 * @author tlkzzz
 * @version 2013-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/test/test")
public class TestController extends BaseController {

	@Autowired
	private TestService testService;
	
	@ModelAttribute
	public Test get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return testService.get(id);
		}else{
			return new Test();
		}
	}
	
	/**
	 * 显示列表页
	 * @param test
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("test:test:view")
	@RequestMapping(value = {"list", ""})
	public String list(Test test, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/test/testList";
	}
	
	/**
	 * 获取硕正列表数据（JSON）
	 * @param test
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("test:test:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Test> listData(Test test, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			test.setCreateBy(user);
		}
        Page<Test> page = testService.findPage(new Page<Test>(request, response), test); 
        return page;
	}
	
	/**
	 * 新建或修改表单
	 * @param test
	 * @param model
	 * @return
	 */
	@RequiresPermissions("test:test:view")
	@RequestMapping(value = "form")
	public String form(Test test, Model model) {
		model.addAttribute("test", test);
		return "modules/test/testForm";
	}

	/**
	 * 表单保存方法
	 * @param test
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "save")
	public String save(Test test, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, test)){
			return form(test, model);
		}
//		testService.save(test);
		addMessage(redirectAttributes, "保存测试'" + test.getName() + "'成功");
		return "redirect:" + adminPath + "/test/test/?repage";
	}

	/**
	 * app,ios下载链接页面
	 * @return
	 */
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "download")
	public String download() {
		return "modules/test/Download";
	}

	/**
	 * 删除数据方法
	 * @param test
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Test test, RedirectAttributes redirectAttributes) {
//		testService.delete(test);
//		addMessage(redirectAttributes, "删除测试成功");
//		return "redirect:" + adminPath + "/test/test/?repage";
		return "true";
	}

	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "freemarkTest")
	public void freemarkTest(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap();
		model.put("userName", "XRC");


		String content = FreeMarkers.renderFile("/WEB-INF/views/modules/test","index.ftl", request, model);//利用模版生成html页面

		String contentTow = FreeMarkers.renderFile("/WEB-INF/views/static","index.html", request, model);//利用html文件生成页面

		renderString(response,contentTow,"text/html; charset=UTF-8");
	}
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "lsit")
	public void lsit(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap();
		String contentTow = FreeMarkers.renderFile("/WEB-INF/views/static","cwgl.html", request, model);//利用html文件生成页面
		renderString(response,contentTow,"text/html; charset=UTF-8");
	}
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "lsitq")
	public void lsitq(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap();
		String contentTow = FreeMarkers.renderFile("/WEB-INF/views/static","cxyw.html", request, model);//利用html文件生成页面
		renderString(response,contentTow,"text/html; charset=UTF-8");
	}
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "lsitqw")
	public void lsitqw(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap();
		String contentTow = FreeMarkers.renderFile("/WEB-INF/views/static","dbsy.html", request, model);//利用html文件生成页面
		renderString(response,contentTow,"text/html; charset=UTF-8");
	}
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "lsitqwe")
	public void lsitqwe(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap();
		String contentTow = FreeMarkers.renderFile("/WEB-INF/views/static","jybg.html", request, model);//利用html文件生成页面
		renderString(response,contentTow,"text/html; charset=UTF-8");
	}
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "lsitqwer")
	public void lsitqwer(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap();
		String contentTow = FreeMarkers.renderFile("/WEB-INF/views/static","kcgl.html", request, model);//利用html文件生成页面
		renderString(response,contentTow,"text/html; charset=UTF-8");
	}
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "lsitqwert")
	public void lsitqwert(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap();
		String contentTow = FreeMarkers.renderFile("/WEB-INF/views/static","ysyw.html", request, model);//利用html文件生成页面
		renderString(response,contentTow,"text/html; charset=UTF-8");
	}
	@RequestMapping(value = "shopTest")
	public void shopTest(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap();
		model.put("ctx", request.getContextPath()+Global.getAdminPath());
		model.put("ctxStatic", request.getContextPath()+"/static");
		model.put("userName", "XRC");
		String contentTow = FreeMarkers.renderFile("/WEB-INF/views/static/one","login.html",request, model);//利用html文件生成页面
		renderString(response,contentTow,"text/html;charset=UTF-8");
	}

}
