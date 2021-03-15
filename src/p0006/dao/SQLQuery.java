package p0006.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

public class SQLQuery {
    public static boolean executeNonQuery(String sqlStatement, Object... values) throws Exception {
        boolean result;
        int index = 1;
        try (Connection cnn = MyConnection.makeConnection() ) {
            if(cnn == null) return false;
            PreparedStatement ps = cnn.prepareStatement(sqlStatement);

            for (index = 0; index < values.length; index++) {
                ps.setObject(index + 1, values[index]);
            }
            result = (ps.executeUpdate() > 0);
        }
        return result;
    }

    public static Vector<Vector<String>> executeQuery(String sqlStatement, Object... values) throws Exception {
        Connection cnn = null;
        PreparedStatement pre;
        Vector<Vector<String>> dataTable;
        dataTable = new Vector<>();
        int index, columnsNumber;
        try {
            cnn = MyConnection.makeConnection();
            if(cnn == null)
                return dataTable;
            pre = cnn.prepareStatement(sqlStatement);
            for (index = 0; index < values.length; index++) {
                pre.setObject(index + 1, values[index]);
            }
            ResultSet rs = pre.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                Vector<String> rowData = new Vector<>();
                for (index = 1; index <= columnsNumber; index++) 
                    rowData.add(rs.getString(index));
                dataTable.add(rowData);
            }
        } finally {
            if (cnn != null) {
                cnn.close();
            }
        }
        return dataTable;
    }
}
