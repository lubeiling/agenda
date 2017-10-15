package com.meeting.utils;

import com.meeting.commands.CommandIn;
import com.meeting.pojo.Agenda;
import com.meeting.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * User: FlowingFire
 * Date: 2017/10/15 0015
 * Time: 21:35
 * Description:
 */
public class AgendaUtil {
    public final static Logger logger = LoggerFactory.getLogger(AgendaUtil.class);

    public static boolean isAttend(User user, Agenda agenda) {

        if(user.getAttendgendas()==null) return true;
        for (Agenda agendal :
                user.getAttendgendas()){

            if(isInner(agendal.getStarttime().getTime(),agenda.getStarttime().getTime(),agenda.getEndtime().getTime())){
                return false;
            }
            if(isInner(agendal.getEndtime().getTime(),agenda.getStarttime().getTime(),agenda.getEndtime().getTime())){
                return false;
            }
        }
        return true;
    }

    public static boolean isInner(long time, long time1, long endtime) {

        if(time>=time1 && time<=endtime){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isContent(Date start, Date end, Date time) {

        if(time.getTime()>=start.getTime() && time.getTime()<=end.getTime()){
            return true;
        }
        return false;
    }


}
