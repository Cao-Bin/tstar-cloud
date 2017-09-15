package com.tiza.webservice.sender.m2;

import com.diyiliu.common.model.ISender;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Description: IM2Sender
 * Author: DIYILIU
 * Update: 2016-03-29 15:53
 */

@WebService
public interface IM2Sender extends ISender {

    /**
     * 指令下发
     * @param id
     * @param cmd
     * @param terminalId
     * @param content 下发内容(Json格式)
     */
    @WebMethod
    void send(int id, int cmd, String terminalId, String content);

    /**
     * 原始指令下发
     * @param id
     * @param cmd
     * @param terminalId
     * @param content 指令内容(字节数组)
     */
    @WebMethod
    void sendOriginal(int id, int cmd, String terminalId, byte[] content);
}
