package kz.dilau.htcdatamanager.component.dictionary;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DictionaryRowMapper implements RowMapper<DictionaryDto<Long>> {
    @Override
    public DictionaryDto<Long> mapRow(ResultSet resultSet, int i) throws SQLException {
        DictionaryDto<Long> dictionary = new DictionaryDto<>();
        dictionary.setId(resultSet.getLong("id"));
        dictionary.setNameKz(resultSet.getString("name_kz"));
        dictionary.setNameEn(resultSet.getString("name_en"));
        dictionary.setNameRu(resultSet.getString("name_ru"));
        if (hasColumn(resultSet, "code")) {
            dictionary.setCode(resultSet.getString("code"));
        }
        if (hasColumn(resultSet, "operation_code")) {
            dictionary.setOperationCode(resultSet.getString("operation_code"));
        }
        return dictionary;
    }

    private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int i = 1; i <= columns; i++) {
            if (columnName.equals(rsmd.getColumnName(i))) {
                return true;
            }
        }
        return false;
    }
}
