package com.jiashn.springbootproject.reflect;

import com.jiashn.springbootproject.desensitize.aop.JsonSensitive;
import com.jiashn.springbootproject.reflect.domain.Animal;
import com.jiashn.springbootproject.reflect.domain.Dog;
import com.jiashn.springbootproject.reflect.domain.EatSome;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/6/30 9:41
 **/
@Slf4j
public class UseReflect {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException, NoSuchMethodException {
        Class<Animal> clazz = Animal.class;
        Class<Dog> dogClazz = Dog.class;
        //获取类加载器
        ClassLoader classLoader = clazz.getClassLoader();
        log.info("类加载器：" + classLoader);
        //获取当前类及其父类中的所有公共类和接口的对象
        Class<?>[] classes = dogClazz.getClasses();
        for (Class<?> aClass : classes) {
            log.info("获取的类及父类中的类对象：" + aClass);
        }
        //获取当前类中所有类和接口类的对象
        Class<?>[] declaredClasses = dogClazz.getDeclaredClasses();
        for (Class<?> aClass : declaredClasses) {
            log.info("获取的类对象：" + aClass);
        }
        //创建类的实例
        Dog dog = dogClazz.newInstance();
        dog.setName("橙橙");
        dog.setBreed("柴犬");
        log.info("创建类的实例：" + dog.getName());

        //获取当前类的完整路径名称
        String name = dogClazz.getName();
        log.info("获取当前类的完整路径名称：" + name);
        //获取类的名称
        String simpleName = dogClazz.getSimpleName();
        log.info("获取类的名称：" + simpleName);

        //获取当前类的包
        Package aPackage = dogClazz.getPackage();
        log.info("获取当前类的包：" + aPackage);
        //获取当前类的父类的对象
        Class<? super Dog> superclass = dogClazz.getSuperclass();
        log.info("获取当前类的父类的对象：" + superclass);
        //获取当前类的实现类或接口
        Class<?>[] interfaces = dogClazz.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            log.info("获取当前类的实现类或接口：" + anInterface);
        }
        //属性
        //根据名称获取属性对象
        Field breedField = dogClazz.getField("breed");
        log.info("根据名称获取属性对象：" + breedField);
        Object o = breedField.get(dog);
        log.info("获取属性值：" + o);
        //获取该类所有公有的属性对象
        Field[] fields = dogClazz.getFields();
        for (Field field : fields) {
            log.info("获取该类公有的属性对象：" + field);
        }
        //获取当前类及父类指定的属性对象
        Field nameField = clazz.getDeclaredField("name");
        log.info("获取当前类及父类指定的属性对象：" + nameField);

        Dog dog1 = new Dog();
        dog1.setName("小希希");
        dog1.setBreed("边牧");
        Field breed = dog1.getClass().getDeclaredField("breed");
        Object o1 = breed.get(dog1);
        log.info("获取小希希的品种属性值：" + o1);
        //获得该类所有属性对象 指标
        Field[] clzFields = clazz.getDeclaredFields();
        for (Field field : clzFields) {
            log.info("获得该类所有属性对象：" + field);
        }

        List<Field> allFields = getAllFields(dogClazz);
        for (Field field : allFields) {
            log.info("获取dog当前类及父类属性：" + field);
        }

        //method方法
        Method setName = dogClazz.getMethod("setName",String.class);
        log.info("获取setName方法：" + setName);


        Method method = dogClazz.getDeclaredMethod("getBreed");
        log.info("获取getBreed方法：" + method);
        Method[] declaredMethods = dogClazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
             log.info("获取Dog所有方法：" + declaredMethod);
        }

        Constructor<Dog> constructor = dogClazz.getConstructor(String.class);
        log.info("获取带参数构造方法：" + constructor);
        Constructor<?>[] constructors = dogClazz.getConstructors();
        for (Constructor<?> con : constructors) {
             log.info("获取Dog下所有构造方法：" + con);
        }
        Method[] methods = dogClazz.getMethods();
        for (Method dogMethod : methods) {
            log.info("获取Dog及父类所有方法：" + dogMethod);
        }

        //Annotation注解
        NotBlank annotation = dogClazz.getDeclaredField("breed").getAnnotation(NotBlank.class);
        log.info("获取Dog中Data注解：" + annotation);
        String message = annotation.message();
        log.info("获取注解信息：" + message);
        boolean isAnnotation = dogClazz.isAnnotation();
        boolean notBlank = NotBlank.class.isAnnotation();
        log.info("验证是否为注解类型的类：dog，" + isAnnotation+ ";notBlank：" + notBlank);
        boolean present = dogClazz.getDeclaredField("breed").isAnnotationPresent(NotBlank.class);
        log.info("指定NotBlank是否为注解类型：" + present);

        //其他
        boolean anonymousClass = dogClazz.isAnonymousClass();
        log.info("当前类是否为匿名类：" + anonymousClass);
        boolean array = dogClazz.isArray();
        log.info("是否为数据类型：" + array);
        boolean isEnum = dogClazz.isEnum();
        log.info("是否为枚举类型：" + isEnum);
        boolean instance = dogClazz.isInstance(dog);
        log.info("obj是否该类的实例：" + instance);
        boolean anInterface = dogClazz.isInterface();
        log.info("是否为接口：" + anInterface);
        Class<?>[] clazzs = dogClazz.getDeclaredClasses();
        for (Class<?> aClass : clazzs) {
            log.info("类名："+aClass.getSimpleName() + ";是否为内部类：" + aClass.isMemberClass());
            log.info("类名："+aClass.getSimpleName() + ";是否为局部类：" + aClass.isLocalClass());
        }

    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> allFields = new ArrayList<>();
        while (Objects.nonNull(clazz)){
            Field[] declaredFields = clazz.getDeclaredFields();
            allFields.addAll(Arrays.asList(declaredFields));
            clazz = clazz.getSuperclass();
        }
        return allFields;
    }
}
