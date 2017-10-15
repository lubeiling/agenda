package com.meeting.commands;

import com.meeting.mapper.AgendaMapper;
import com.meeting.mapper.UserMapper;
import com.meeting.mapper.mapperimpl.AgendaMapperImpl;
import com.meeting.mapper.mapperimpl.UserMapperImpl;
import com.meeting.pojo.Agenda;
import com.meeting.pojo.User;
import com.meeting.utils.AgendaUtil;
import com.meeting.utils.Login;
import com.meeting.utils.UserUtil;
import org.apache.commons.cli.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: FlowingFire
 * Date: 2017/10/15 0015
 * Time: 21:19
 * Description:查询当前用户一个时间段所有的会议命令类
 */
public class QueryCommand implements CommandIn {
    Agenda agenda =new Agenda();
    UserMapper userMapper=new UserMapperImpl();
    AgendaMapper agendaMapper =new AgendaMapperImpl();
    DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Override
    public void simpleHelp() {
        System.out.printf("%-20s","query");
        System.out.println("查询当前用户会议:query -s 1990-10-1 12:00 -e 2017-10-1 10:00");
    }

    @Override
    public boolean getOptions(String[] args) {
        Options options = new Options();
        Option opt = new Option("h", "help", false, "Print help");
        opt.setRequired(false);
        options.addOption(opt);



        opt = new Option("s", "startTime", true, "yyyy-MM-dd HH:mm");
        opt.setRequired(true);
        opt.setArgs(2);
        options.addOption(opt);

        opt = new Option("e", "endTime", true, "yyyy-MM-dd HH:mm");
        opt.setRequired(true);
        opt.setArgs(2);
        options.addOption(opt);





        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(110);
        CommandLine commandLine = null;
        CommandLineParser parser = new PosixParser();
        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption('h')) {
                // 打印使用帮助
                hf.printHelp("query", options, true);
            }

            // 打印opts的名称和值
            System.out.println("--------------------------------------");
            Option[] opts = commandLine.getOptions();
            if (opts != null) {
                for (Option opt1 : opts) {
                   /* String name = opt1.getLongOpt();
                    String value = commandLine.getOptionValue(name);
                    System.out.println(name + "=>" + value);*/

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

                }
            }
            //将获得的参数传到UserContorllor去注册。
           return checkParameters();

        }
        catch (Exception e) {
            hf.printHelp("query", options, true);
            return false;
        }
    }

    @Override
    public void excute() {
        if(Login.isLogin()) {
            User user= UserUtil.updateUserByUserName(Login.getUser().getUsername());
            List<Agenda> list=queryAll(agenda.getStarttime(),agenda.getEndtime(),user);
            System.out.println("------会议列表---------");
            System.out.println();
            System.out.printf("|%-20s|","起始时间");
            System.out.printf("|%-20s|","终止时间");
            System.out.printf("|%-12s|","主题");
            System.out.printf("|%-12s|","发起者");
            System.out.printf("|%-12s|","参与者");
            System.out.println("");
            for (Agenda agenda:
                    list) {
                String st =String.format("%1$tY-%1$tm-%1$td %1$tT", agenda.getStarttime());
                String et =String.format("%1$tY-%1$tm-%1$td %1$tT",agenda.getEndtime());
                System.out.printf("|%-23s|",st);
                System.out.printf("|%-24s|",et);
                System.out.printf("|%-12s|",agenda.getTitle());
                System.out.printf("|%-15s|",agenda.getInitiatorName());
                for (String username:
                        agenda.getParticipators()) {
                    System.out.printf("|%-8s",username);
                }

                System.out.println();
            }
        }else {
            System.out.println("请登录！");
        }
    }




    @Override
    public boolean checkParameters() {
        return true;
    }

    private List<Agenda> queryAll(Date start, Date end, User user) {
        List<Agenda> agendaList1=new ArrayList<>();
        List<Agenda> agendaList2=new ArrayList<>();
        List<Agenda> createdgendas = user.getCreatedgendas();
        List<Agenda> attendgendas = user.getAttendgendas();
        logger.debug("先找创建的");
        for (Agenda agenda:
                createdgendas) {
            if(AgendaUtil.isContent(start,end,agenda.getStarttime()) || AgendaUtil.isContent(start,end,agenda.getEndtime())){
                agendaList1.add(agenda);
            }
        }
        for (Agenda agenda:
                attendgendas) {
            if(AgendaUtil.isContent(start,end,agenda.getStarttime()) || AgendaUtil.isContent(start,end,agenda.getEndtime())){
                agendaList2.add(agenda);
            }
        }
        agendaList1.removeAll(agendaList2);
        agendaList1.addAll(agendaList2);
        return agendaList1;
    }
}
