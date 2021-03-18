package com.lww.theone.util.handler;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//把结果集中的一行数据封装称一个对象， 专门针对结果集有多条数据的情况
public class BeanListHandler<T> implements IResultSetHandler<List<T>> {             //IResultSetHandler<    List<T>   >      这里写什么就决定返回类型是什么


    private Class<T> classType;            //用于表示被封装的数据类型

    //构造器
    public BeanListHandler(Class<T> classType) {                      //new BeanHandler（User.class）  此时T就是User
        this.classType = classType;
    }

    @Override
    public List<T> handle(ResultSet rs) throws Exception {
        List<T> list=new ArrayList<>();
        while (rs.next()){
            //1.创建对应类的一个对象
            T obj = classType.newInstance();
            //2.取出结果集中当前光标所在行的某一列的数据
            BeanInfo beanInfo = Introspector.getBeanInfo(classType, Object.class);    //反射
            //获取此对象中存在的所有属性
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                //遍历属性
                for (PropertyDescriptor pd : propertyDescriptors) {
                    String columnName = pd.getName();     //获取对象的属性名
                    //根据此属性名 找出数据库返回的结果集中同名的列名的数据
                    Object val = rs.getObject(columnName);
                    //调用该对象的set方法，将值设置进去
                    pd.getWriteMethod().invoke(obj, val);     //将遍历到的当期属性  给他设置对应的值进去     。。。下一次遍历下一个属性
                }
                list.add(obj);
        }
        return list;
    }
}
