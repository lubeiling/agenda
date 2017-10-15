package com.meeting.utils;

import com.meeting.commands.CommandIn;

import java.util.ArrayList;
import java.util.List;

/**
 * User: FlowingFire
 * Date: 2017/10/15 0015
 * Time: 17:56
 * Description:
 */
public class HelpUtil {
    private static List<Class> allClassByInterface;
    private static List<CommandIn> commands;
    private static Class<?> aClass;
    private static Object object;
    List<Class> commandClasses;

    private static void init(){
        try {
            aClass = Class.forName("com.meeting.commands.CommandIn");
            allClassByInterface = ClassUtil.getAllClassByInterface(aClass);
            commands=new ArrayList<CommandIn>();
            for (Class aClass1 : allClassByInterface) {
                CommandIn command=(CommandIn) aClass1.newInstance();
                commands.add(command);
            }

        } catch (Exception e) {
            System.out.println("获取帮助失败");
            return;

        }
    }
    public static void printHelp(){
        if(commands==null) init();
        System.out.println("指令名              简介");
        for (CommandIn command:
             commands) {
            command.simpleHelp();
        }
    }
}
