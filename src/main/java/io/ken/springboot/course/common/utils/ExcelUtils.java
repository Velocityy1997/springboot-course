package io.ken.springboot.course.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author ZhaoWenHao
 * 处理Excel文件
 * @create 2019-12-27 12:46
 **/
public class ExcelUtils {
    public static List<Map> getImport(File file) {
        // TODO Auto-generated method stub
        if(file == null){
            return null;
        }

        if(file.getName().endsWith("xlsx")){
            //处理ecxel2007
            return readExcel2007(file);
        }else{
            //处理ecxel2003
            return readExcel2003(file);
        }
    }
    private static List<Map> readExcel2003(File file) {
        try{
            List<Map> rowList = new ArrayList<Map>();
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;
            Object value;
            /* 获取第一行的表头 */
            row = sheet.getRow(sheet.getFirstRowNum());
            List<Object> firstRow = new ArrayList<Object>();
            for( int j = row.getFirstCellNum() ; j <= row.getLastCellNum() ;j++){
                cell = row.getCell((short) j);
                if(cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
                    //当该单元格为空
                    if(j != row.getLastCellNum()){//判断是否是该行中最后一个单元格
                        firstRow.add("");
                    }
                    continue;
                }
                switch(cell.getCellType()){
                    case HSSFCell.CELL_TYPE_STRING:

                        firstRow.add(cell.getStringCellValue());
                        break;

                    default:
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        firstRow.add(cell.getStringCellValue());
                        break;
                }
            }//end for j
            /* 获取第一行的表头 */
            for(int i = sheet.getFirstRowNum() + 1;i <= sheet.getLastRowNum() ; i++ ){
                row = sheet.getRow(i);
                if (row == null || isBlankRow(row)) {//这里判断为空或只有空格的row忽略
                    continue;
                }


                Map<String,String> map = new HashMap<String,String>();//存放试题的Map集合
                for( int j = row.getFirstCellNum() ; j < firstRow.size() ;j++){
                    cell = row.getCell((short) j);

                    if(cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
                        //当该单元格为空
                        if(j != row.getLastCellNum()){//判断是否是该行中最后一个单元格
                            map.put((String) firstRow.get(j), "");


                        }
                        continue;
                    }
                    switch(cell.getCellType()){
                        case HSSFCell.CELL_TYPE_STRING://字符型数据
                            map.put((String) firstRow.get(j), cell.getStringCellValue());

                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC://数字型数据
                            //如果为时间格式的内容
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                //注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                map.put((String) firstRow.get(j),sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString());
                                break;
                            }
                            double d = cell.getNumericCellValue();
                            map.put((String) firstRow.get(j), Double.valueOf(d).longValue()+"");

                            break;
                        default:
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            map.put((String) firstRow.get(j), cell.getStringCellValue());
                            break;
                    }
                }//end for j
                rowList.add(map);
            }//end for i

            return rowList;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isBlankRow(HSSFRow row) {
        // TODO Auto-generated method stub

        if(row == null) {
            return true;
        }
        boolean result = true;
        for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
            HSSFCell cell = row.getCell((short) i);
            String value = "";
            if(cell != null){
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        value = String.valueOf((int) cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        value = String.valueOf(cell.getCellFormula());
                        break;
                    //case Cell.CELL_TYPE_BLANK:
                    //    break;
                    default:
                        break;
                }

                if(!value.trim().equals("")){
                    result = false;
                    break;
                }
            }
        }

        return result;

    }

    private static List<Map> readExcel2007(File file) {
        try{
            List<Map> rowList = new ArrayList<Map>();

            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;
            Object value;
            /* 获取第一行的表头 */
            row = sheet.getRow(sheet.getFirstRowNum());
            List<Object> firstRow = new ArrayList<Object>();
            for( int j = row.getFirstCellNum() ; j <= row.getLastCellNum() ;j++){
                cell = row.getCell((short) j);
                if(cell == null || cell.getCellType() == XSSFCell.CELL_TYPE_BLANK){
                    //当该单元格为空
                    if(j != row.getLastCellNum()){//判断是否是该行中最后一个单元格
                        firstRow.add("");
                    }
                    continue;
                }
                switch(cell.getCellType()){
                    case XSSFCell.CELL_TYPE_STRING:

                        firstRow.add(cell.getStringCellValue());
                        break;

                    default:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        firstRow.add(cell.getStringCellValue());
                        break;
                }
            }//end for j
            /* 获取第一行的表头 */
            for(int i = sheet.getFirstRowNum()+1;i <= sheet.getLastRowNum() ; i++ ){
                row = sheet.getRow(i);
                if (row == null || isBlankRow1(row)) {//这里判断为空或只有空格的row忽略
                    continue;
                }
                Map<String,String> map = new HashMap<String,String>();
                for( int j = row.getFirstCellNum() ; j <= row.getLastCellNum() ;j++){

                    cell = row.getCell((short) j);
                    if(cell == null || cell.getCellType() == XSSFCell.CELL_TYPE_BLANK){
                        //当该单元格为空
                        if(j != row.getLastCellNum()){//判断是否是该行中最后一个单元格
                            map.put((String) firstRow.get(j), "");

                        }
                        continue;
                    }
                    switch(cell.getCellType()){
                        case XSSFCell.CELL_TYPE_STRING:
                            map.put((String) firstRow.get(j), cell.getStringCellValue());

                            break; case HSSFCell.CELL_TYPE_NUMERIC:
                            //如果为时间格式的内容
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                //注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                map.put((String) firstRow.get(j),sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString());
                                break;
                            }
                        default:
                            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                            map.put((String) firstRow.get(j), cell.getStringCellValue());

                            break;
                    }
                }//end for j
                rowList.add(map);
            }

            return rowList;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isBlankRow1(XSSFRow row) {
        // TODO Auto-generated method stub

        if (row == null) {
            return true;
        }
        boolean result = true;
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            XSSFCell cell = row.getCell((short) i);
            String value = "";
            if (cell != null) {
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        value = String.valueOf((int) cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        value = String.valueOf(cell.getCellFormula());
                        break;
                    //case Cell.CELL_TYPE_BLANK:
                    //    break;
                    default:
                        break;
                }

                if (!value.trim().equals("")) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}
