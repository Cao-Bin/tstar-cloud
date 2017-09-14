package com.tiza.webservice.common.model;

import cn.com.tiza.tstar.datainterface.client.entity.ClientCmdSendResult;

import java.util.Date;

/**
 * Description: SendMSG
 * Author: DIYILIU
 * Update: 2016-03-21 13:59
 */
public class SendMSG {

    private int id;
    private String terminalId;
    private String terminalType;
    private int serial;
    private int cmd;
    private Date sendTime;
    private int waitCount;

    private byte[] content;

    private ClientCmdSendResult sendResult;

    public SendMSG() {

    }

    public SendMSG(String terminalId, int cmd, byte[] content) {
        this.terminalId = terminalId;
        this.cmd = cmd;
        this.content = content;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClientCmdSendResult getSendResult() {
        return sendResult;
    }

    public void setSendResult(ClientCmdSendResult sendResult) {
        this.sendResult = sendResult;
    }

    public int getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(int waitCount) {
        this.waitCount = waitCount;
    }
}
