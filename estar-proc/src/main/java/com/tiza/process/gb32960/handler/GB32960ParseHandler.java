package com.tiza.process.gb32960.handler;

import cn.com.tiza.tstar.common.datasource.BusinessDBManager;
import cn.com.tiza.tstar.common.process.BaseHandle;
import cn.com.tiza.tstar.common.process.RPTuple;
import cn.com.tiza.tstar.common.utils.DBUtil;
import com.tiza.process.common.support.cache.ICache;
import com.tiza.process.common.support.config.Constant;
import com.tiza.process.common.support.dao.VehicleDao;
import com.tiza.process.common.support.dao.base.BaseDao;
import com.tiza.process.common.support.task.ITask;
import com.tiza.process.common.util.CommonUtil;
import com.tiza.process.common.util.SpringUtil;
import com.tiza.process.gb32960.bean.GB32960Header;
import com.tiza.process.gb32960.protocol.GB32960DataProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Description: GB32960ParseHandler
 * Author: Wangw
 * Update: 2017-09-06 16:26
 */
public class GB32960ParseHandler extends BaseHandle {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public RPTuple handle(RPTuple rpTuple) throws Exception {
        logger.info("terminal[{}], cmd[{}]...", rpTuple.getTerminalID(), CommonUtil.toHex(rpTuple.getCmdID()));

        ICache cmdCacheProvider = SpringUtil.getBean("cmdCacheProvider");
        GB32960DataProcess process = (GB32960DataProcess) cmdCacheProvider.get(rpTuple.getCmdID());
        if (process == null) {
            logger.error("can't find [{}] cmd parser!", CommonUtil.toHex(rpTuple.getCmdID()));
            return null;
        }

        GB32960Header header = (GB32960Header) process.dealHeader(rpTuple.getMsgBody());
        header.settStarData(rpTuple);
        process.parse(header.getContent(), header);

        return rpTuple;
    }

    @Override
    public void init() throws Exception {
        GB32960DataProcess.setHandler(this);
        SpringUtil.init();

        BusinessDBManager dbManager = BusinessDBManager.getInstance(this.processorConf);
        Field field = dbManager.getClass().getDeclaredField("dbUtil");
        field.setAccessible(true);
        DBUtil dbUtil = (DBUtil) field.get(dbManager);
        field.setAccessible(false);

        // 初始化数据源
        BaseDao.initDataSource(dbUtil.getDataSource());

        // 刷新车辆列表
        ITask task = SpringUtil.getBean("refreshVehicleInfoTask");
        task.execute();
    }
}
