package com.diyiliu.common.listener;

import com.diyiliu.common.model.IDataProcess;
import com.diyiliu.common.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Description: CMDInitializer
 * Author: DIYILIU
 * Update: 2017-08-04 14:10
 */

public class CMDInitializer implements ApplicationListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<Class> protocols;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        logger.info("协议解析初始化...");

        for (Class protocol : protocols) {

            Map parses = SpringUtil.getBeansOfType(protocol);

            for (Iterator iterator = parses.keySet().iterator(); iterator.hasNext(); ) {
                String key = (String) iterator.next();
                IDataProcess process = (IDataProcess) parses.get(key);
                process.init();
            }
        }
    }

    public void setProtocols(List<Class> protocols) {
        this.protocols = protocols;
    }
}
