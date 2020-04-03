package io.ken.springboot.course.tools;

import java.util.List;

public class PageUtil {
	public static List startPage(List list, Integer limit,
            Integer offset) {
		if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }
        int size = list.size();
        Integer pageCount = 0; // 页数
        if (size % limit == 0) {
            pageCount = size / limit;
        } else {
            pageCount = size / limit + 1;
        }
        int fromIndex = (offset-1) * limit;
        int toIndex = fromIndex + limit;
        if (offset == pageCount) {
           
            fromIndex = (offset - 1) * limit;
            toIndex = size;
        }
        
        
        List pageList = list.subList(fromIndex, toIndex);
        return pageList;
	}
}
