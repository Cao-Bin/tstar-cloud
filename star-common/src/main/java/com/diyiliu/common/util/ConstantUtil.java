package com.diyiliu.common.util;

import com.diyiliu.common.dao.BaseDao;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: ConstantUtil
 * Author: DIYILIU
 * Update: 2017-09-20 14:44
 */
public class ConstantUtil {
    private static Logger logger = LoggerFactory.getLogger(ConstantUtil.class);

    public final static String JSON_CHARSET = "UTF-8";

    public static void init(String file){
        logger.info("init configuration...");
        initSqlCache(file);
    }

    public static void init(String file, Map<String, String> conf) throws Exception{
        logger.info("init configuration...");
        initSqlCache(file);

        initDataSource(conf);
    }

    /**
     * 装载数据源
     * @throws Exception
     */
    public static void initDataSource(Map<String, String> conf) throws Exception{
        String driver = conf.get("business.database.driver");
        String url = conf.get("business.database.url");
        String username = conf.get("business.database.username");
        String password = conf.get("business.database.password");

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        // 初始化数据源
        BaseDao.initDataSource(dataSource);
    }


    private static Map<String, String> sqlCache = new HashMap<>();
    public static void initSqlCache(String sqlFile) {
        sqlCache.clear();

        InputStream is = null;
        try {
            is = new ClassPathResource(sqlFile).getInputStream();
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(is);

            List<Node> sqlList = document.selectNodes("root/sql");
            for (Node sqlNode : sqlList) {
                String id = sqlNode.valueOf("@id");
                String content = sqlNode.getText().trim();
                sqlCache.put(id, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getSQL(String sqlId) {
        return sqlCache.get(sqlId);
    }
}
