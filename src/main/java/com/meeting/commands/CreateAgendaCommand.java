package com.meeting.commands;

import com.meeting.mapper.AgendaMapper;
import com.meeting.mapper.UserMapper;
import com.meeting.mapper.mapperimpl.AgendaMapperImpl;
import com.meeting.mapper.mapperimpl.UserMapperImpl;
import com.meeting.pojo.Agenda;
import com.meeting.pojo.User;
import com.meeting.utils.AgendaUtil;
import com.meeting.utils.Login;
import org.apache.commons.cli.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * User: FlowingFire
 * Date: 2017/10/15 0015
 * Time: 19:58
 * Description:
 */
public class CreateAgendaCommand implements CommandIn {

    Agenda agenda =new Agenda();
    UserMapper userMapper=new UserMapperImpl();
    AgendaMapper agendaMapper =new AgendaMapperImpl();
    DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Override
    public void simpleHelp() {
        System.out.printf("%-20s","createAgenda");
        System.out.println("创建会议:createAgenda -s 2017-10-1 12:00 -e 2017-10-1 14:00 -t 班会1 -p ly2 ly3");
    }

    @Override
    public boolean getOptions(String[] args) {
        String[] participators={};
        Options options = new Options();
        Option opt = new Option("h", "help", false, "Print help");
        opt.setRequired(false);
        options.addOption(opt);
        opt = new Option("t", "title", true, "title");
        opt.setRequired(true);
        options.addOption(opt);


        opt = new Option("s", "startTime", true, "yyyy-MM-dd HH:mm");
        opt.setRequired(true);
        opt.setArgs(2);
        options.addOption(opt);

        opt = new Option("e", "endTime", true, "yyyy-MM-dd HH:mm");
        opt.setRequired(true);
        opt.setArgs(2);
        options.addOption(opt);

        opt = new Option("p", "participator", true, "participator最多接受10个人");
        opt.setRequired(true);
        opt.setArgs(10);
        options.addOption(opt);



        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(110);
        CommandLine commandLine = null;
        CommandLineParser parser = new PosixParser();
        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption('h')) {
                // 打印使用帮助
                hf.printHelp("testApp", options, true);
            }

            // 打印opts的名称和值
            System.out.println("--------------------------------------");
            Option[] opts = commandLine.getOptions();
            if (opts != null) {
                for (Option opt1 : opts) {
                   /* String name = opt1.getLongOpt();
                    String value = commandLine.getOptionValue(name);
                    System.out.println(name + "=>" + value);*/
                    if("title".equalsIgnoreCase(opt1.getLongOpt())){
                        agenda.setTitle(commandLine.getOptionValue(opt1.getLongOpt()));
                    }
                    if("startTime".equalsIgnoreCase(opt1.getLongOpt())){
                        //user.setPassword(commandLine.getOptionValue(opt1.getLongOpt()));
                        String date=opt1.getValue(0)+" "+opt1.getValue(1);
                        Date parse = df.parse(date);
                        agenda.setStarttime(parse);
                    }
                    if("endTime".equalsIgnoreCase(opt1.getLongOpt())){
                        //user.setEmail(commandLine.getOptionValue(opt1.getLongOpt()));
                        String date=opt1.getValue(0)+" "+opt1.getValue(1);
                        Date parse = df.parse(date);
                        agenda.setEndtime(parse);
                    }
                    if("participator".equalsIgnoreCase(opt1.getLongOpt())){
                        List<String> usernames = Arrays.asList(opt1.getValues());
                        agenda.setParticipators(usernames);
                        // user.setPhone(commandLine.getOptionValue(opt1.getLongOpt()));
                    }
                }
            }
            //将获得的参数传到UserContorllor去注册。
           return checkParameters();

        }
        catch (Exception e) {
            hf.printHelp("testApp", options, true);
            return false;
        }
    }

    @Override
    public void excute() {
        if(Login.isLogin()) {
            if(agendaMapper.findAgendaByTittle(agenda.getTitle())!=null){
                System.out.println("会议重复！");
            }
            boolean flag=true;
            List<User> userList=new ArrayList<User>();
            agenda.setInitiatorName(Login.getUser().getUsername());
            logger.debug("判断会议人能否参加");
            for (String username :
                    agenda.getParticipators()){
                User user=userMapper.findUserByUserName(username);
                if(user!=null) {
                    userList.add(user);
                    if (!AgendaUtil.isAttend(user, agenda)) {
                        System.out.println(username + "这个用户不能参加");
                        flag= false;
                    }
                }else {
                    System.out.println("没有"+username+"这个用户");
                }
            }
            logger.debug("更新参与的用户信息");
            if(userList.isEmpty()) {
                logger.debug(" 没有这些用户");
                flag= false;
            }
            for (User user :
                    userList){
                if(user.getAttendgendas()!=null) {
                    user.getAttendgendas().add(agenda);
                }else {
                    List<Agenda> agendaList =new ArrayList<Agenda>();
                    agendaList.add(agenda);
                    user.setAttendgendas(agendaList);
                }
                userMapper.addUser(user);
            }
            logger.debug("更新创建人信息");
            String username = Login.getUser().getUsername();
            User userByUserName = userMapper.findUserByUserName(username);
            List<Agenda> createdgendas = userByUserName.getCreatedgendas();
            if(createdgendas==null){
                createdgendas=new ArrayList<Agenda>();
                userByUserName.setCreatedgendas(createdgendas);
            }
            createdgendas.add(agenda);
            userMapper.addUser(userByUserName);
            logger.debug("生成会议");
            flag= agendaMapper.add(agenda);
            if(flag){
                System.out.println("创建会议成功");
            }else {
                System.out.println("创建会议失败");
            }
        }else {
            System.out.println("请登录！");
        }
    }

    @Override
    public boolean checkParameters() {
        return true;
    }


}
