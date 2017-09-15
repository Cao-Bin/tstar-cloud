package com.tiza.webservice.sender.m2.impl;

import com.diyiliu.common.util.JacksonUtil;
import com.tiza.webservice.protocol.m2.M2DataProcess;
import com.tiza.webservice.sender.m2.IM2Sender;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Description: M2Sender
 * Author: DIYILIU
 * Update: 2016-03-29 15:54
 */

@WebService
public class M2Sender implements IM2Sender {

    private String address;

    @Resource
    private M2DataProcess m2DataProcess;

    @Override
    public void send(int id, int cmd, String terminalId, String content) {

        Object param = content;
        if (StringUtils.isNotBlank(content)){
            try {
                if (content.startsWith("{")){

                    param = JacksonUtil.toObject(content, HashMap.class);
                }else if (content.startsWith("[")){

                    param = JacksonUtil.toObject(content, List.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        m2DataProcess.send(id, cmd, terminalId, param);
    }

    @Override
    public void sendOriginal(int id, int cmd, String terminalId, byte[] content) {

        m2DataProcess.send(id, cmd, terminalId, content);
    }

    @Override
    public void init() {

        Endpoint.publish(address, this);
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
