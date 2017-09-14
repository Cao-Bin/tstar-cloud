package com.tiza.process.m2.handler;

import cn.com.tiza.tstar.common.datasource.BusinessDBManager;
import cn.com.tiza.tstar.common.process.BaseHandle;
import cn.com.tiza.tstar.common.process.RPTuple;
import cn.com.tiza.tstar.common.utils.DBUtil;
import com.diyiliu.common.cache.ICache;
import com.diyiliu.common.dao.BaseDao;
import com.diyiliu.common.task.ITask;
import com.diyiliu.common.util.CommonUtil;
import com.diyiliu.common.util.SpringUtil;
import com.tiza.process.common.config.Constant;
import com.tiza.process.m2.bean.M2Header;
import com.tiza.process.m2.protocol.M2DataProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Description: M2ParseHandler
 * Author: DIYILIU
 * Update: 2017-09-14 14:24
 */
public class M2ParseHandler extends BaseHandle{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public RPTuple handle(RPTuple rpTuple) throws Exception {
        logger.info("收到终端[{}], 指令[{}]...", rpTuple.getTerminalID(), CommonUtil.toHex(rpTuple.getCmdID()));

        ICache cmdCacheProvider = SpringUtil.getBean("cmdCacheProvider");
        M2DataProcess process = (M2DataProcess) cmdCacheProvider.get(rpTuple.getCmdID());
        if (process == null) {
            logger.error("无法找到[{}]指令解析器!", CommonUtil.toHex(rpTuple.getCmdID()));
            return null;
        }

        rpTuple.getContext().put(Constant.Kafka.TRACK_TOPIC, processorConf.get("trackTopic"));
        M2Header header = (M2Header) process.dealHeader(rpTuple.getMsgBody());
        header.settStarData(rpTuple);
        process.parse(header.getContent(), header);

        return rpTuple;
    }

    @Override
    public void init() throws Exception {
        M2DataProcess.setHandle(this);
        SpringUtil.init();

        BusinessDBManager dbManager = BusinessDBManager.getInstance(this.processorConf);
        Field field = dbManager.getClass().getDeclaredField("dbUtil");
        field.setAccessible(true);
        DBUtil dbUtil = (DBUtil) field.get(dbManager);
        field.setAccessible(false);

        // 初始化数据源
        BaseDao.initDataSource(dbUtil.getDataSource());

        // 加载初始化SQL
        Constant.init("init-sql.xml");

        // 刷新车辆列表
        ITask task = SpringUtil.getBean("refreshVehicleInfoTask");
        task.execute();
    }
}
