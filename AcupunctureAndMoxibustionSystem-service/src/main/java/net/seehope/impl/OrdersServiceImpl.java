package net.seehope.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.tools.corba.se.idl.constExpr.Or;
import net.seehope.IndexService;
import net.seehope.OrdersService;
import net.seehope.mapper.OrdersMapper;
import net.seehope.pojo.Goods;
import net.seehope.pojo.Orders;
import net.seehope.pojo.bo.GetOrdersBo;
import net.seehope.pojo.vo.OrderVo;
import net.seehope.util.ExcelFormatUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {

    Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Autowired
    OrdersMapper ordersMapper;

    @Override
    public Integer getWaitingOrders() {
        return ordersMapper.queryWaitingOrders();
    }

    @Override
    public Integer getFinishedOrders() {
        return ordersMapper.queryFinishedOrders();
    }



    @Override
    public PageInfo getAllOrders(GetOrdersBo ordersBo) {
        PageHelper.startPage(ordersBo.getPage(),ordersBo.getPageSize());
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        if(ordersBo.getStatus() != null && ordersBo.getStatus() != ""){
            criteria.andEqualTo("status",ordersBo.getStatus());
        }
        if(ordersBo.getOrderId() != null && ordersBo.getOrderId() != ""){
            criteria.andEqualTo("orderId",ordersBo.getOrderId());
        }
        example.setOrderByClause("order_time DESC");
        List<Orders> orders = ordersMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(orders);
        return pageInfo;
    }

    @Override
    public void updateOrder(String orderId) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        Orders orders1 = ordersMapper.selectOne(orders);
        ordersMapper.delete(orders);
        orders1.setStatus("1");
        ordersMapper.insert(orders1);
    }

    @Override
    public ResponseEntity<byte[]> exportExcel(HttpServletRequest request, HttpServletResponse response, String excelName) {
        try {
            logger.info(">>>>>>>>>>开始导出excel>>>>>>>>>>");


            List<Orders> list=ordersMapper.selectAll();

            BaseFrontServiceImpl baseFrontController = new BaseFrontServiceImpl();
            return baseFrontController.buildResponseEntity(export(list), excelName + ".xlsx");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(">>>>>>>>>>导出excel 异常，原因为：" + e.getMessage());
        }
        return null;
    }

    private InputStream export(List<Orders> list) {
        logger.info(">>>>>>>>>>>>>>>>>>>>开始进入导出方法>>>>>>>>>>");
        Map<String, String> map = new HashMap();
        map.put("0","未发货");
        map.put("1","已发货");
        ByteArrayOutputStream output = null;
        InputStream inputStream1 = null;
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);// 保留1000条数据在内存中
        SXSSFSheet sheet = wb.createSheet();
        // 设置报表头样式
        CellStyle header = ExcelFormatUtil.headSytle(wb);// cell样式
        CellStyle content = ExcelFormatUtil.contentStyle(wb);// 报表体样式

        // 每一列字段名
        String[] strs = new String[] { "订单号", "商品名称", "商品数量", "用户昵称", "联系方式",
                "收货地址","订单总额","下单时间","备注信息","状态"};

        // 字段名所在表格的宽度
        int[] ints = new int[] { 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};

        // 设置表头样式
        ExcelFormatUtil.initTitleEX(sheet, header, strs, ints);
        logger.info(">>>>>>>>>>>>>>>>>>>>表头样式设置完成>>>>>>>>>>");

        if (list != null && list.size() > 0) {
            logger.info(">>>>>>>>>>>>>>>>>>>>开始遍历数据组装单元格内容>>>>>>>>>>");
            for (int i = 0; i < list.size(); i++) {
                Orders user = list.get(i);
                SXSSFRow row = sheet.createRow(i + 1);
                int j = 0;

                SXSSFCell cell = row.createCell(j++);
                cell.setCellValue(user.getOrderId());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getProductName());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getProductNumber());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getUserName());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getUserPhone());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getUserAddress());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getOrderAmout());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getOrderTime());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getRemark());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(map.get(user.getStatus()));
                cell.setCellStyle(content);

            }
            logger.info(">>>>>>>>>>>>>>>>>>>>结束遍历数据组装单元格内容>>>>>>>>>>");
        }
        try {
            output = new ByteArrayOutputStream();
            wb.write(output);
            inputStream1 = new ByteArrayInputStream(output.toByteArray());
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                    if (inputStream1 != null)
                        inputStream1.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream1;
    }



    @Autowired
    IndexService indexService;
    @Override
    public String getTodayIncome() {
        long todays = indexService.getStartTime();
        long todaye = indexService.getEndTime();

        List<Orders> ordersList =  ordersMapper.getIncome(new Date(todays),new Date(todaye));
        int sum = 0;
        for(Orders orders:ordersList){
            System.out.println(orders.getOrderAmout());

            sum += orders.getOrderAmout();
        }

        return sum+"";
    }

    @Override
    public String getMonthIncome() {

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));//东八区时间

        //获取本月最小天数
        int day = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        System.out.println(day);
        //获取本月最大天数
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(days);
        c.set(Calendar.DAY_OF_MONTH,day);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);

        Date dates = c.getTime();

        c.set(Calendar.DAY_OF_MONTH,days);
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        c.set(Calendar.MILLISECOND,999);

        Date datee = c.getTime();

        List<Orders> ordersList =  ordersMapper.getIncome(dates,datee);
        int sum = 0;
        for(Orders orders:ordersList){
            System.out.println(orders.getOrderAmout());

            sum += orders.getOrderAmout();
        }
        return sum +"";
    }

    @Override
    public String totalIncome() {
        List<Orders> ordersList = ordersMapper.selectAll();
        int sum = 0;
        for(Orders orders:ordersList){
            System.out.println(orders.getOrderAmout());

            sum += orders.getOrderAmout();
        }
        return sum + "";
    }

    @Override
    public List<OrderVo> getAllIncome() {
        Date minDate =ordersMapper.orderMinDate();
        List list = new ArrayList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(minDate);

        Calendar calendar2 = Calendar.getInstance();
        Date maxDate = ordersMapper.orderMaxDate();
        calendar2.setTime(maxDate);

//        System.out.println(calendar.get(Calendar.YEAR));
//
//        System.out.println(calendar2.get(Calendar.YEAR));
//
//        System.out.println(calendar.get(Calendar.MONTH));
//
//        System.out.println(calendar2.get(Calendar.MONTH));
//


        while (true){
            Date date = calendar.getTime();
            OrderVo orderVo = new OrderVo();
            orderVo.setDate(simpleDateFormat.format(date));
            orderVo.setIncome(getTodayIncome(date));
            list.add(orderVo);

            if((calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) &&  (calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH))
            && (calendar.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH))){
                break;
            }

            calendar.add(Calendar.DAY_OF_MONTH,1);

        }


        return list;
    }

    @Override
    public String getTodayIncome(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        Date todays = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        Date todaye = calendar.getTime();

        List<Orders> ordersList =  ordersMapper.getIncome(todays,todaye);
        int sum = 0;
        for(Orders orders:ordersList){
            System.out.println(orders.getOrderAmout());

            sum += orders.getOrderAmout();
        }

        return sum+"";


    }
}
