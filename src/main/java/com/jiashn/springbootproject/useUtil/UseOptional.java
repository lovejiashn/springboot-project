package com.jiashn.springbootproject.useUtil;

import com.jiashn.springbootproject.useUtil.domain.Expert;
import com.jiashn.springbootproject.useUtil.domain.Position;
import com.jiashn.springbootproject.useUtil.domain.SystemUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author: jiangjs
 * @description: 使用Optional
 * @date: 2023/5/8 11:08
 **/
public class UseOptional {
    public static void main(String[] args) {
        List<SystemUser> users = Arrays.asList(
                new SystemUser("jiashn","男",20,new Position("总经理",1)),
                new SystemUser("张三","女",21,new Position("财务总监",2)),
                new SystemUser("王五","女",26,new Position("会计",3)),
                new SystemUser("李四","男",24,new Position("开发部经理",2))
        );

        SystemUser systemUser = new SystemUser("王五","女",26,new Position("会计",3));
        SystemUser user = null;

        /*//年龄大于21岁的用户名称
        if(CollectionUtils.isNotEmpty(users)){
            for (SystemUser user : users) {
                if (Objects.equals("赵一",user.getName())){
                    Position position = user.getPosition();
                    if (Objects.nonNull(position)){
                        System.out.println("循环获取职位名称：" +position.getName());
                    }
                }
            }
        }

        String name = Optional.of(users.stream().filter(user -> Objects.equals("赵一", user.getName())).findFirst())
                .get()
                .map(SystemUser::getPosition)
                .map(Position::getName)
                .orElse("");
        System.out.println("Optional获取职位名称：" + name);*/

        //empty
        Object o = Optional.empty();
        System.out.println("empty：" + o);

        //of ofNullable
        Optional<SystemUser> systemUserOptional = Optional.of(systemUser);
        System.out.println("systemUserOptional：" + systemUserOptional);
        /*Optional<SystemUser> userOptional = Optional.of(user);
        System.out.println("userOptional：" + userOptional);*/
        Optional<SystemUser> optionalSystemUser = Optional.ofNullable(systemUser);
        System.out.println("optionalSystemUser：" + optionalSystemUser);
        Optional<SystemUser> optionalUser = Optional.ofNullable(user);
        System.out.println("optionalUser：" + optionalUser);

        //isPresent
        boolean present = Optional.of(systemUser).isPresent();
        System.out.println("systemUser是否存在：" + present);

        boolean judge = Optional.ofNullable(user).isPresent();
        System.out.println("user是否存在：" + judge);

        //orElseGet，orElse
        SystemUser orElseGetUser = Optional.ofNullable(user).orElseGet(UseOptional::getSystemUser);
        System.out.println("orElseGetUser：" + orElseGetUser);
        SystemUser orElseUser = Optional.ofNullable(user).orElse(getSystemUser());
        System.out.println("orElseUser：" + orElseUser);
        System.out.println("-------------------------------------------------------------");

        SystemUser orElseGetSystemUser = Optional.of(systemUser).orElseGet(UseOptional::getSystemUser);
        System.out.println("orElseGetSystemUser：" + orElseGetSystemUser);
        SystemUser orElseSystemUser = Optional.of(systemUser).orElse(getSystemUser());
        System.out.println("orElseSystemUser：" + orElseSystemUser);

        //get
        SystemUser getSystemUser = Optional.ofNullable(systemUser).get();
        System.out.println("getSystemUser：" + getSystemUser);
        /*SystemUser getUser = Optional.ofNullable(user).get();
        System.out.println("getUser：" + getUser);*/

        //map
        String sysUserName = Optional.of(systemUser).map(SystemUser::getName).get();
        System.out.println("sysUserName：" + sysUserName);
        Optional<String> userName = Optional.ofNullable(user).map(SystemUser::getName);
        System.out.println("userName：" + userName);
        //flatMap
        String name = Optional.of(systemUser)
                .flatMap(SystemUser::optionalPosition)
                .map(Position::getName).get();
        System.out.println("name：" + name);


    }

    static SystemUser getSystemUser(){
        SystemUser systemUser =  new SystemUser("李四","男",24,new Position("开发部经理",2));
        System.out.println("默认数据：" + systemUser);
        return systemUser;
    }
}
