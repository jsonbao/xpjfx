/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/tlkzzz/jeesite">JeeSite</a> All rights reserved.
 */
package com.tlkzzz.jeesite.modules.cw.dao;

import com.tlkzzz.jeesite.common.persistence.CrudDao;
import com.tlkzzz.jeesite.common.persistence.annotation.MyBatisDao;
import com.tlkzzz.jeesite.modules.cw.entity.FPropaidExpenses;

/**
 * 待摊费用DAO接口
 * @author xrc
 * @version 2017-04-05
 */
@MyBatisDao
public interface FPropaidExpensesDao extends CrudDao<FPropaidExpenses> {
	
}