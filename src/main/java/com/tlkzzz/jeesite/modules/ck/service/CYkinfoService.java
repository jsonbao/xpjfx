/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/tlkzzz/jeesite">JeeSite</a> All rights reserved.
 */
package com.tlkzzz.jeesite.modules.ck.service;

import java.util.List;

import com.tlkzzz.jeesite.modules.ck.dao.CGoodsDao;
import com.tlkzzz.jeesite.modules.ck.entity.CGoods;
import com.tlkzzz.jeesite.modules.ck.entity.CHgoods;
import com.tlkzzz.jeesite.modules.ck.entity.CHouse;
import com.tlkzzz.jeesite.modules.sys.utils.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tlkzzz.jeesite.common.persistence.Page;
import com.tlkzzz.jeesite.common.service.CrudService;
import com.tlkzzz.jeesite.modules.ck.entity.CYkinfo;
import com.tlkzzz.jeesite.modules.ck.dao.CYkinfoDao;

/**
 * 移库记录Service
 * @author xrc
 * @version 2017-03-15
 */
@Service
@Transactional(readOnly = true)
public class CYkinfoService extends CrudService<CYkinfoDao, CYkinfo> {

	@Autowired
	private CGoodsDao cGoodsDao;

	public CYkinfo get(String id) {
		return super.get(id);
	}
	
	public List<CYkinfo> findList(CYkinfo cYkinfo) {
		List<CYkinfo> list = super.findList(cYkinfo);
		for(CYkinfo cc: list){
			if(cc!=null) {
				String[] unit = {cc.getGoods().getBig().getName(), cc.getGoods().getZong().getName(), cc.getGoods().getSmall().getName()};
				cc.setSpecNub(ToolsUtils.unitTools(cc.getGoods().getSpec().getName(), unit, Integer.parseInt(cc.getNub())));
			}
		}
		return list;
	}

	public List<CYkinfo> fyfindList(CYkinfo cYkinfo){return dao.fyfindList(cYkinfo);}

	public List<CYkinfo> findOrderCodeList(CYkinfo cYkinfo){return dao.findOrderCodeList(cYkinfo);}

	public List<CYkinfo> findReportList(CYkinfo cYkinfo) {
		List<CYkinfo> list = dao.findReportList(cYkinfo);
		for(CYkinfo cc: list){
			if(cc!=null) {
				String[] unit = {cc.getGoods().getBig().getName(), cc.getGoods().getZong().getName(), cc.getGoods().getSmall().getName()};
				cc.setSpecNub(ToolsUtils.unitTools(cc.getGoods().getSpec().getName(), unit, Integer.parseInt(cc.getNub())));
			}
		}
		return list;
	}
	
	public Page<CYkinfo> findPage(Page<CYkinfo> page, CYkinfo cYkinfo) {
		page = super.findPage(page, cYkinfo);
		for(CYkinfo cc:page.getList()){
			if(cc!=null) {
				String[] unit = {cc.getGoods().getBig().getName(), cc.getGoods().getZong().getName(), cc.getGoods().getSmall().getName()};
				cc.setNub(ToolsUtils.unitTools(cc.getGoods().getSpec().getName(), unit, Integer.parseInt(cc.getNub())));
			}
		}
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(CYkinfo cYkinfo) {
		super.save(cYkinfo);
	}

	@Transactional(readOnly = false)
	public void saveInfo(CHgoods cHgoods,String outHouseId,String orderCode) {
		CGoods goods = cGoodsDao.get(cHgoods.getGoods().getId());
		CYkinfo cYkinfo = new CYkinfo();
		cYkinfo.setOrderCode(orderCode);
		cYkinfo.setStartHouse(new CHouse(outHouseId));
		cYkinfo.setEndHouse(cHgoods.getHouse());
		cYkinfo.setGoods(cHgoods.getGoods());
		cYkinfo.setNub(cHgoods.getNub());
		if(goods!=null) {
			cYkinfo.setCbj(goods.getCbj());
			cYkinfo.setXsj(goods.getSj());
		}
		super.save(cYkinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CYkinfo cYkinfo) {
		super.delete(cYkinfo);
	}
	
}