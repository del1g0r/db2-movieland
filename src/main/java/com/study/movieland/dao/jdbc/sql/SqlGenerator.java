package com.study.movieland.dao.jdbc.sql;

import com.study.movieland.data.RequestParams;
import org.springframework.stereotype.Component;

@Component
public class SqlGenerator {

    private StringBuilder stringBuilder = new StringBuilder();

    public String getSQL(String sql, RequestParams requestParams) {
        if (requestParams != null) {
            stringBuilder.delete(0, stringBuilder.length());
            stringBuilder.append(sql);
            if (requestParams.isSorted()) {
                stringBuilder.append(" ORDER BY ");
                stringBuilder.append(requestParams.getSortFieldName());
                stringBuilder.append(' ');
                stringBuilder.append(requestParams.getSortDirection());
            }
            return stringBuilder.toString();
        }
        return sql;
    }
}
