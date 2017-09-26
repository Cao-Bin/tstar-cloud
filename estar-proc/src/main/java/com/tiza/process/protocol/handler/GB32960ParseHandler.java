package com.tiza.process.protocol.handler;

import cn.com.tiza.tstar.common.process.BaseHandle;
import cn.com.tiza.tstar.common.process.RPTuple;
import com.diyiliu.common.cache.ICache;
import com.diyiliu.common.util.CommonUtil;
import com.diyiliu.common.util.SpringUtil;
import com.tiza.process.common.config.EStarConstant;
import com.tiza.process.protocol.bean.GB32960Header;
import com.tiza.process.protocol.gb32960.GB32960DataProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        // 将conf配置信息放入上下文中
        rpTuple.getContext().put(EStarConstant.Kafka.TRACK_TOPIC, processorConf.get("trackTopic"));

        GB32960Header header = (GB32960Header) process.dealHeader(rpTuple.getMsgBody());
        header.settStarData(rpTuple);
        process.parse(header.getContent(), header);

        return rpTuple;
    }

    @Override
    public void init() throws Exception {
        // 加载配置信息
        EStarConstant.init("init-sql.xml", processorConf);

        // 装载Spring容器
        SpringUtil.init();
        //
        GB32960DataProcess.setHandler(this);
    }
}
