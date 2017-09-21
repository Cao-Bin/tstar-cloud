package com.tiza.process.protocol.handler;

import cn.com.tiza.tstar.common.process.BaseHandle;
import cn.com.tiza.tstar.common.process.RPTuple;
import cn.com.tiza.tstar.common.utils.DBUtil;
import com.diyiliu.common.cache.ICache;
import com.diyiliu.common.dao.BaseDao;
import com.diyiliu.common.task.ITask;
import com.diyiliu.common.util.CommonUtil;
import com.diyiliu.common.util.SpringUtil;
import com.tiza.process.common.config.MStarConstant;
import com.tiza.process.common.dao.VehicleDao;
import com.tiza.process.common.model.InOutRecord;
import com.tiza.process.protocol.bean.M2Header;
import com.tiza.process.protocol.m2.M2DataProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

        // 将processorConf内容放入上下文中
        rpTuple.getContext().put(MStarConstant.Kafka.TRACK_TOPIC, processorConf.get("trackTopic"));
        rpTuple.getContext().put(MStarConstant.Kafka.WORK_TIME_TOPIC, processorConf.get("workTimeTopic"));

        M2Header header = (M2Header) process.dealHeader(rpTuple.getMsgBody());
        header.settStarData(rpTuple);
        process.parse(header.getContent(), header);

        return rpTuple;
    }

    @Override
    public void init() throws Exception {
        M2DataProcess.setHandle(this);
        SpringUtil.init();

        initDataSource();

        // 加载初始化SQL
        MStarConstant.init("init-sql.xml");

        // 刷新车辆列表
        ITask task = SpringUtil.getBean("refreshVehicleInfoTask");
        task.execute();

        /**
         * 每个流程的init方法是并行的，
         * 所有的初始化方法必须放在第一个流程的init方法里。
         * 除非后面的初始化方法不依赖前面的初始化内容。
         * （1: Spring容器池; 2: Jdbc数据源; 3: 初始化SQL查询语句。）
         */
        initStrategyAlarmModule();
    }


    /**
     * 装载数据源
     * @throws Exception
     */
    public void initDataSource() throws Exception{
        String driver = processorConf.get("business.database.driver");
        String url = processorConf.get("business.database.url");
        String username = processorConf.get("business.database.username");
        String password = processorConf.get("business.database.password");
        DBUtil dbUtil = new DBUtil(driver, url, username, password);

        // 初始化数据源
        BaseDao.initDataSource(dbUtil.getDataSource());
    }

    public void initStrategyAlarmModule(){
        // 刷新车辆仓库信息
        ITask task = SpringUtil.getBean("refreshVehicleStorehouseTask");
        task.execute();

        ICache  vehicleOutInCacheProvider = SpringUtil.getBean("vehicleOutInCacheProvider");
        VehicleDao vehicleDao = SpringUtil.getBean("vehicleDao");

        // 车辆最近一条和仓库之间的位置信息
        List<InOutRecord> list = vehicleDao.selectInOutRecord();
        for (InOutRecord record : list) {
            vehicleOutInCacheProvider.put(record.getVehicleId(), record);
        }
    }
}
