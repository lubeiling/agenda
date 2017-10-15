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

    public void simpleHelp();
    public boolean getOptions(String args[]);
    public void excute();
    public boolean checkParameters();
}
