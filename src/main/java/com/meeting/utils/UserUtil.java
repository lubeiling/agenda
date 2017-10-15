package com.meeting.utils;

import com.meeting.commands.CommandIn;
import com.meeting.mapper.AgendaMapper;
import com.meeting.mapper.UserMapper;
import com.meeting.mapper.mapperimpl.AgendaMapperImpl;
import com.meeting.mapper.mapperimpl.UserMapperImpl;
import com.meeting.pojo.Agenda;
import com.meeting.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * User: FlowingFire
 * Date: 2017/10/15 0015
 * Time: 21:45
 * Description:
 */
public class UserUtil {
    public final static Logger logger = LoggerFactory.getLogger(UserUtil.class);

    private static UserMapper userMapper=new UserMapperImpl();
    private static AgendaMapper agendaMapper=new AgendaMapperImpl();

    public static User updateUserByUserName(String username) {
        User userByUserName = userMapper.findUserByUserName(username);
        logger.debug("更新集合里面的集合");
        List<Agenda> attendgendas = userByUserName.getAttendgendas();
        List<Agenda> temp1=new ArrayList<Agenda>();
        List<Agenda> temp2=new ArrayList<Agenda>();
        if(attendgendas==null) attendgendas=new ArrayList<Agenda>();
        for (Agenda agenda:
                attendgendas) {
            Agenda agendaByTittle = agendaMapper.findAgendaByTittle(agenda.getTitle());
            temp1.add(agendaByTittle);
        }
        userByUserName.setAttendgendas(temp1);

        List<Agenda> createdgendas = userByUserName.getCreatedgendas();
        if(createdgendas==null) createdgendas=new ArrayList<>();
        for (Agenda agenda:
                createdgendas) {
            Agenda agendaByTittle = agendaMapper.findAgendaByTittle(agenda.getTitle());
            temp2.add(agendaByTittle);
        }
        userByUserName.setCreatedgendas(temp2);
        return userByUserName;
    }
}
