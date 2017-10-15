package com.meeting.commands;

import com.meeting.utils.JSonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: FlowingFire
 * Date: 2017/10/15 0015
 * Time: 17:45
 * Description:命令接口
 */
public interface CommandIn {
    public final static Logger logger = LoggerFactory.getLogger(CommandIn.class);
    /**
     * @description 命令的简单帮助
     * @method  simpleHelp
     * @date: 2017年10月15日 20:53:51
     * @author:刘烨
     */
    public void simpleHelp();
    /**
     * @description 获取命令行的参数
     * @method  getOptions
     * @param args
     * @return 返回true代表参数正常，false代表传入的参数不符合标准。
     * @date: 2017年10月15日 20:54:57
     * @author:刘烨
     */
    public boolean getOptions(String args[]);
    /**
     * @description 执行对应的命令
     * @method excute
     * @date: 2017年10月15日 20:56:56
     * @author:刘烨
     */
    public void excute();
    /**
     * @description 传入命令类的参数要做检查
     * @method  checkParameters
     * @return 返回true代表参数符合标准，false代表传入的参数不符合标准。
     * @date: 2017年10月15日 20:57:59
     * @author:刘烨
     */
    public boolean checkParameters();
}
