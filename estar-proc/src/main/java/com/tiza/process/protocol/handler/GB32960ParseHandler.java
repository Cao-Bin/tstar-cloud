package com.tiza.process.protocol.handler;

import cn.com.tiza.tstar.common.datasource.BusinessDBManager;
import cn.com.tiza.tstar.common.process.BaseHandle;
import cn.com.tiza.tstar.common.process.RPTuple;
import cn.com.tiza.tstar.common.utils.DBUtil;
import com.diyiliu.common.cache.ICache;
import com.diyiliu.common.dao.BaseDao;
import com.diyiliu.common.task.ITask;
import com.diyiliu.common.util.CommonUtil;
import com.diyiliu.common.util.SpringUtil;
import com.tiza.process.common.config.EStarConstant;
import com.tiza.process.protocol.bean.GB32960Header;
import com.tiza.process.protocol.gb32960.GB32960DataProcess;
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

        // 加载初始化SQL
        EStarConstant.init("init-sql.xml");

        // 刷新车辆列表
        ITask task = SpringUtil.getBean("refreshVehicleInfoTask");
        task.execute();
    }
}
