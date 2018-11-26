package com.study.movieland.dao.jdbc.sql;

import com.study.movieland.data.RequestParams;
import org.junit.Assert;
import org.junit.Test;

public class SqlGeneratorTest {

    @Test
    public void testGetSql() {
        String sql = "SELECT * FROM TABLE";
        String expectedSql = sql;
        SqlGenerator sqlGenerator = new SqlGenerator();
        RequestParams requestParams = new RequestParams.Builder().build();

        String actualSql = sqlGenerator.getSQL(sql, requestParams);

        Assert.assertEquals(expectedSql, actualSql);
    }

    @Test
    public void testGetSqlOrderBy() {
        String sql = "SELECT * FROM TABLE";
        String expectedSql = "SELECT * FROM TABLE ORDER BY field DESC";
        SqlGenerator sqlGenerator = new SqlGenerator();
        RequestParams requestParams = new RequestParams.Builder()
                .sortFieldName("field")
                .sortDirection("desc")
                .build();

        String actualSql = sqlGenerator.getSQL(sql, requestParams);

        Assert.assertEquals(expectedSql, actualSql);
    }


    @Test
    public void testGetSqlOrderByWithDefDirection() {
        String sql = "SELECT * FROM TABLE";
        String expectedSql = "SELECT * FROM TABLE ORDER BY field ASC";
        SqlGenerator sqlGenerator = new SqlGenerator();
        RequestParams requestParams = new RequestParams.Builder()
                .sortFieldName("field")
                .sortDirection("")
                .build();

        String actualSql = sqlGenerator.getSQL(sql, requestParams);

        Assert.assertEquals(expectedSql, actualSql);
    }
}
