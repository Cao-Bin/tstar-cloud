package com.tiza.process.common.support.model;

/**
 * Description: IDataProcess
 * Author: Wangw
 * Update: 2017-09-06 16:59
 */
public interface IDataProcess {

    Header dealHeader(byte[] bytes);

    void parse(byte[] content, Header header);

    void init();
}
