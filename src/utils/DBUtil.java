package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;

public class DBUtil {
    private static final DataSource ds=new ComboPooledDataSource();
    public static DataSource getDataSource()
    {
        return  ds;
    }
}
