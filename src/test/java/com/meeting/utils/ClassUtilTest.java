package com.meeting.utils;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * User: FlowingFire
 * Date: 2017/10/15 0015
 * Time: 17:47
 * Description:
 */

public class ClassUtilTest {

    private List<Class> allClassByInterface;

    @Test
    public void getAllClassByInterface() throws Exception {
        allClassByInterface = ClassUtil.getAllClassByInterface(Class.forName("com.meeting.commands.CommandIn"));
        for (Class c:
                allClassByInterface) {
            System.out.println(c.getName());
        }
    }

    private List<Class<?>> classes;

    @Test
    public void getClasses() throws Exception {
        classes = ClassUtil.getClasses("com.meeting.commands");
        for (Class c:
             classes) {
            System.out.println(c.getName());
        }
    }



}
