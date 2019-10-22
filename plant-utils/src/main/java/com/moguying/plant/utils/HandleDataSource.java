package com.moguying.plant.utils;

public class HandleDataSource {
    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static void putDataSource(String dataSource){
        holder.set(dataSource);
    }

    public static String getDataSource(){
        return holder.get();
    }
}
