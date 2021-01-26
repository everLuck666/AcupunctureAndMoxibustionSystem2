package net.seehope.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.seehope.OrdersService;
import net.seehope.mapper.OrdersMapper;
import net.seehope.pojo.Orders;
import net.seehope.pojo.bo.GetOrdersBo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            criteria.andEqualTo("order_id",ordersBo.getOrderId());
        }
        List<Orders> orders = ordersMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(orders);
        return pageInfo;
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
}
