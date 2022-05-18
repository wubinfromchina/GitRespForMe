package com.wb.pojo.utils;

import com.wb.pojo.Activity;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * excel文件使用的工具类
 */
public class WorkbookUtils {

    public static HSSFWorkbook getOutHSSFWorkbook(List<Activity> allActList){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("市场活动列表");//页
        HSSFRow row = sheet.createRow(0);//行
        HSSFCell cell = row.createCell(0);//列
        cell.setCellValue("ID");
        cell = row.createCell(1);//列
        cell.setCellValue("所有者");
        cell = row.createCell(2);//列
        cell.setCellValue("活动名称");
        cell = row.createCell(3);//列
        cell.setCellValue("开始日期");
        cell = row.createCell(4);//列
        cell.setCellValue("结束日期");
        cell = row.createCell(5);//列
        cell.setCellValue("成本");
        cell = row.createCell(6);//列
        cell.setCellValue("备注");
        cell = row.createCell(7);//列
        cell.setCellValue("创建时间");
        cell = row.createCell(8);//列
        cell.setCellValue("创建者");
        cell = row.createCell(9);//列
        cell.setCellValue("修改时间");
        cell = row.createCell(10);//列
        cell.setCellValue("修改者");
        if (allActList != null && allActList.size()>0) {
            for (int i = 0; i < allActList.size(); i++) {
                row = sheet.createRow(i+1);//行
                cell = row.createCell(0);//列
                cell.setCellValue(allActList.get(i).getId());
                cell = row.createCell(1);//列
                cell.setCellValue(allActList.get(i).getOwner());
                cell = row.createCell(2);//列
                cell.setCellValue(allActList.get(i).getName());
                cell = row.createCell(3);//列
                cell.setCellValue(allActList.get(i).getStartDate());
                cell = row.createCell(4);//列
                cell.setCellValue(allActList.get(i).getEndDate());
                cell = row.createCell(5);//列
                cell.setCellValue(allActList.get(i).getCost());
                cell = row.createCell(6);//列
                cell.setCellValue(allActList.get(i).getDescription());
                cell = row.createCell(7);//列
                cell.setCellValue(allActList.get(i).getCreateTime());
                cell = row.createCell(8);//列
                cell.setCellValue(allActList.get(i).getCreateBy());
                cell = row.createCell(9);//列
                cell.setCellValue(allActList.get(i).getEditTime());
                cell = row.createCell(10);//列
                cell.setCellValue(allActList.get(i).getEditBy());
            }
        }
        return workbook;
    }

    //设置响应类型
    public static OutputStream getOutputStream(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=activities.xls");

        OutputStream out = response.getOutputStream();
        return out;
    }

    //从指定HSSFCell对象中获取列的值
    public static String getStringCellValue(HSSFCell cell){
        if (cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
            return cell.getStringCellValue();
        }else if (cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
            return String.valueOf((int)cell.getNumericCellValue());
        }else if (cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
            return String.valueOf(cell.getBooleanCellValue());
        }else if (cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
            return String.valueOf(cell.getCellFormula());
        }else {
            return "";
        }
    }

    //给市场活动实体类封装数据
    public static Activity setActivityColumn(Activity activity,int j,String cellValue){
        if (j==1){
            activity.setOwner(cellValue);
        }else if (j==2){
            activity.setName(cellValue);
        }else if (j==3){
            activity.setStartDate(cellValue);
        }else if (j==4){
            activity.setEndDate(cellValue);
        }else if (j==5){
            activity.setCost(cellValue);
        }else if (j==6){
            activity.setDescription(cellValue);
        }else if (j==7){
            activity.setCreateTime(cellValue);
        }else if (j==8){
            activity.setCreateBy(cellValue);
        }

        return activity;
    }
}
