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

    @WebMethod
    void send(int id, int cmd, String terminalId, String content);
}
