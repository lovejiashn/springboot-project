package com.jiashn.springbootproject.annotation.usetransient;

import java.io.*;

/**
 * @author: jiangjs
 * @description: Transient使用： 在序列化实体中如果某些变量不需要持久化，则可以使用。例如：一些敏感信息，密码等
 *                          1、Transient只能修饰在变量上，不能使用在类、方法上
 *                          2、一旦变量被Transient修饰后，序列化后无法获取访问
 *                          3、被Transient修饰的变量，不在被序列化，一个静态变量不管是否被Transient修饰，均不序列化
 * @date: 2023/3/2 9:46
 **/
public class Transient {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Employee employee = new Employee();
        employee.setUserName("张三").setPassWord("123456").setAge(20);
        System.out.println("获取实体数据：" + employee);
        writeDataToTxt(employee);
        Employee toTxt = readDataToTxt();
        System.out.println("序列化后获取数据：" + toTxt);
    }

    static void writeDataToTxt(Employee employee) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("D:\\eaafile\\text.txt"));
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\eaafile\\text2.txt"));
        stream.writeObject(employee);
        oos.write(("姓名:"+employee.getUserName()+"  password:"+employee.getPassWord()+"  age:"+employee.getAge()).getBytes("UTF-8"));
        stream.flush();
        oos.flush();
        stream.close();
        oos.close();
    }

    static Employee readDataToTxt() throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream("D:\\eaafile\\text.txt"));
        Employee employee = (Employee)stream.readObject();
        stream.close();
        return employee;
    }
}
