package core;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseUtility {
    Statement statement = null;

    public DatabaseUtility(){
        try{
            Connection connection = DriverManager.getConnection(
                    DBConstants.URL_PARTIAL + DBConstants.DB_NAME,
                    DBConstants.USERNAME,
                    DBConstants.PASSWORD
            );
            statement = connection.createStatement();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public DefaultTableModel getData(String query){
        DefaultTableModel tableModel = null;
        try{
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            // get column names
            int columnCount = resultSetMetaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for(int i = 0; i < columnCount; i++){
                columnNames[i] = resultSetMetaData.getColumnName(i + 1);
            }
            tableModel = new DefaultTableModel(columnNames, 0);

            // get row data
            while(resultSet.next()) {
                String[] data = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    data[i] = resultSet.getString(i + 1);
                }
                tableModel.addRow(data);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return tableModel;
    }

    public int executeUpdate(String query){
        int result = 0;
        try{
            result = statement.executeUpdate(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String[] getSingleColumnValues(String singleColumnQuery){
        String[] strings = null;
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            ResultSet resultSet = statement.executeQuery(singleColumnQuery);
            while (resultSet.next()){
                arrayList.add(resultSet.getString(1));
            }
            strings = arrayList.toArray(new String[0]);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return strings;
    }

    public String[] getSingleRowValues(String singleRowQuery){
        String[] strings = null;
        try{
            ResultSet resultSet = statement.executeQuery(singleRowQuery);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            strings = new String[columnCount];
            resultSet.next();
            for(int i = 0; i < columnCount; i++){
                strings[i] = resultSet.getString(i + 1);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return strings;
    }

}
