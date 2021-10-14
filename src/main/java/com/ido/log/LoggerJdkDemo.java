package com.ido.log;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.*;

/**
 * Logger 为jdk自带的类
 */
public class LoggerJdkDemo {
    public static void main(String[] args) throws IOException {

//        userLogger();
//        testLogLevel();
      //  testLogConfig();
       // testLogParent();
       // testProp();
        diyTestProp();
    }

    /**
     * 简单使用
     */
    public static void userLogger(){
        Logger logger = Logger.getLogger("com.ido.log.LoggerJdkDemo");
        //两种日志输出方法
        logger.info("hello jul");
        logger.log(Level.INFO,"method jul");
        //占位符输出变量
        logger.log(Level.WARNING,"用户信息：{0},{1}",new Object[]{"ygw",152324});
    }

    /**
     * 七个级别日志消息:
     *      SEVERE :程序出现严重问题导致程序终止
     *      WARNING:程序出现的警告
     *      INFO:普通信息 (默认)
     *      CONFIG:读取的配置文件信息
     *      FINE  FINER  FINEST :颗粒度逐渐减小
     *      ======================================
     *      ALL: 输出类型日志
     *      OFF: 关闭日志输出
     */
    public static void testLogLevel(){
        Logger logger = Logger.getLogger("com.ido.log.LoggerJdkDemo");
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info"); //只输出info以上
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    /**
     * 配置控制台输出和文件输出
     * @throws IOException
     */
    public static void testLogConfig() throws IOException {
        Logger logger = Logger.getLogger("com.ido.log.LoggerJdkDemo");
        //关闭系统默认配置
        logger.setUseParentHandlers(false);

        //格式转换对象
        SimpleFormatter sdf = new SimpleFormatter();

        //控制台输出对象
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(sdf);//关联
        logger.addHandler(consoleHandler);//关联

        //文件输出对象
        FileHandler fileHandler = new FileHandler("src/main/resources/log/jul.log");
        fileHandler.setFormatter(sdf);//关联
        logger.addHandler(fileHandler);//关联

        //配置日志级别
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info"); //只输出info以上
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }


    public static void testLogParent(){
        Logger loggerA = Logger.getLogger("com.ido.log.LoggerJdkDemo");
        Logger loggerB = Logger.getLogger("com.ido.log");

        //如果父包修改了默认日志级别，子包会跟随
        Logger parent = loggerA.getParent();
        System.out.println(parent == loggerB); //true

        //所有日志记录器的顶级父元素，空字符串
        Logger parent1 = loggerB.getParent();
        System.out.println(parent1);//java.util.logging.LogManager$RootLogger@2503dbd3
    }

    public static void testProp() throws IOException {
        InputStream resourceAsStream = LoggerJdkDemo.class.getClassLoader().getResourceAsStream("log/logging.properties");
        LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(resourceAsStream);
        Logger logger = Logger.getLogger("com.ido.log.LoggerJdkDemo");
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info"); //只输出info以上
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    public static void diyTestProp() throws IOException {
        InputStream resourceAsStream = LoggerJdkDemo.class.getClassLoader().getResourceAsStream("log/logging.properties");
        LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(resourceAsStream);
        Logger logger = Logger.getLogger("com.ido");
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info"); //只输出info以上
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

}
