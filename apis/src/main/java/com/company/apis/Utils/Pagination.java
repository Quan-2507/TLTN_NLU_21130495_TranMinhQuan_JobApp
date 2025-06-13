package com.company.apis.Utils;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> {
    // pagination(0,0,data) => getAll()
    // pagination(0,-1,data), pagination(0,2,data) => [] empty
    // pagination(0,0,data) => getAll()
    public List<T> pagination(int size, int page, List<T> data) {
        if (size == 0 && page == 0) {
            // get all
            return data;
        }
        if (size <= 0 || page < 0) {
            // size = 0 => empty
            data = new ArrayList<>();
            return data;
        }
        if (data == null || data.isEmpty()) {
            return data;
        }
        int startIndex = Math.max(0, (page - 1) * size);
        int endIndex = Math.min(startIndex + size, data.size());
        if (startIndex > data.size()) {
            // example: size = 5 and page = 5 => get start = 25 but data have 20 => empty;
            data = new ArrayList<>();
            return data;
        }
        if (endIndex > data.size()) {
            // example: size = 5 and page = 5 => get start = 25 to 30 but data have 27 => get 25 to 27 ;
            return data.subList(startIndex, data.size() - 1);
        }
        return data.subList(startIndex, endIndex);
    }
}
