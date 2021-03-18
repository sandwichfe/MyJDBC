package com.lww.theone.util.handler;


import java.sql.ResultSet;

/**
 * 结果集处理器，规范处理结果集的方法名称
 */
public interface IResultSetHandler<T> {                             //这里尖括号里面的写的T     下面的handle的返回类型也是T      这里的类型就决定了下面的方法的返回类型


    //处理结果集
    T handle(ResultSet rs) throws Exception;
}
