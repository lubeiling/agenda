package com.meeting.commands;

/**
 * User: FlowingFire
 * Date: 2017/10/15 0015
 * Time: 17:45
 * Description:命令接口
 */
public interface CommandIn {
    public void simpleHelp();
    public boolean getOptions(String args[]);
    public void excute();
    public boolean checkParameters();
}
