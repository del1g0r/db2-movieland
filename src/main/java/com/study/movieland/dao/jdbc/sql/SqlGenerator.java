package com.study.movieland.dao.jdbc.sql;

import com.study.movieland.data.RequestParams;
import org.springframework.stereotype.Component;

@Component
public class SqlGenerator {

    public String getSQL(String sql, RequestParams requestParams) {
        if (requestParams != null) {
            StringBuilder stringBuilder = new StringBuilder(sql);
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
