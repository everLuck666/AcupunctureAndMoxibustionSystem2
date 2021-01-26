package net.seehope;

public interface OrderService {

    //得到今日收入

    String getTodayIncome();



    //得到本月收入
    String getMonthIncome();


    //得到累计收入
    String totalIncome();


}
