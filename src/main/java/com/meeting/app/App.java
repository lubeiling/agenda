package com.meeting.app;

import java.util.Scanner;

import com.meeting.commands.CommandIn;
import com.meeting.utils.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * User: FlowingFire
 * Date: 2017/10/15 0015
 * Time: 18:53
 * Description:
 */
public class App {
    private final static Logger logger = LoggerFactory.getLogger(App.class);
    private static CommandIn command;

    public static void main(String[] args) {
        logger.debug("程序开始执行！");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.nextLine();
            args = s.split(" ");
            command = ClassUtil.getCommand(args[0]);
            if(command!=null&&command.getOptions(args)){
                command.excute();
            }
        }
    }
}