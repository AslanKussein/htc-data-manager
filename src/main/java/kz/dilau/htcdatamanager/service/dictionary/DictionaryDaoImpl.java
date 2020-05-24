package kz.dilau.htcdatamanager.service.dictionary;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DictionaryDaoImpl implements DictionaryDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate npJdbcTemplate;
    private final EntityManager entityManager;
    final String SELECT_ONE_QUERY = "select * from %s where id = :id";
    final String SELECT_ONE_QUERY_POST_ID = "select * from %s where kaz_post_id = :kazPostId";
    final String SELECT_ALL_QUERY = "select * from %s";
    final String INSERT_CUSTOM_QUERY = "insert into %s (name_kz, name_en, name_ru) values (:nameKz, :nameEn, :nameRu)";
    final String INSERT_SYSTEM_QUERY = "insert into %s (name_kz, name_en, name_ru, code) values (:nameKz, :nameEn, :nameRu, :code)";
    final String UPDATE_QUERY = "update employee set age = :age where id = :id";
    final String DELETE_QUERY = "delete from %s where id = :id";

    @Override
    public DictionaryDto<Long> getByIdFromTable(Long id, String tableName) {
        SqlParameterSource params = new MapSqlParameterSource()
//                .addValue("tableName", tableName)
                .addValue("id", id);
        DictionaryDto<Long> dictionary = npJdbcTemplate
                .queryForObject(String.format(SELECT_ONE_QUERY, tableName), params, new DictionaryRowMapper());
        return dictionary;
    }

    @Override
    public List<DictionaryDto<Long>> getByAllFromTable(String tableName) {
        List<DictionaryDto<Long>> dictionaries = npJdbcTemplate
                .query(String.format(SELECT_ALL_QUERY, tableName), new DictionaryRowMapper());
        return dictionaries;
    }

    @Override
    public Long savePossibleReasonForBidding(Dictionary dictionary, DictionaryDto<Long> input) {
        String insertSql = "insert into " + dictionary.getTableName() + " (name_kz, name_en, name_ru, operation_code) values (:nameKz, :nameEn, :nameRu, :operationCode)";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nameKz", input.getNameKz())
                .addValue("nameEn", input.getNameEn())
                .addValue("nameRu", input.getNameRu())
                .addValue("operationCode", input.getOperationCode());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        npJdbcTemplate.update(insertSql, params, keyHolder, new String[]{"id"});
        return keyHolder.getKey().longValue();
    }

    @Override
    public Long saveCustomDictionary(Dictionary dictionary, DictionaryDto<Long> input) {

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nameKz", input.getNameKz())
                .addValue("nameEn", input.getNameEn())
                .addValue("nameRu", input.getNameRu());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        npJdbcTemplate.update(String.format(INSERT_CUSTOM_QUERY, dictionary.getTableName()), params, keyHolder, new String[]{"id"});
        return keyHolder.getKey().longValue();
    }

    @Override
    public Long saveSystemDictionary(Dictionary dictionary, DictionaryDto<Long> input) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nameKz", input.getNameKz())
                .addValue("nameEn", input.getNameEn())
                .addValue("nameRu", input.getNameRu())
                .addValue("code", input.getCode());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        npJdbcTemplate.update(String.format(INSERT_SYSTEM_QUERY, dictionary.getTableName()), params, keyHolder, new String[]{"id"});
        return keyHolder.getKey().longValue();
    }

    @Override
    public Integer deleteByIdFromTable(Long id, String tableName) {
        SqlParameterSource params = new MapSqlParameterSource()
//                .addValue("tableName", tableName)
                .addValue("id", id);
        int deleted = npJdbcTemplate.update(String.format(DELETE_QUERY, tableName), params);
//        if (deleted != 1) {
//            throw new RuntimeException();
//        }
        return deleted;
    }

    @Override
    public Integer update(Dictionary dictionary, Long id, DictionaryDto<Long> input) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("nameKz", input.getNameKz())
                .addValue("nameEn", input.getNameEn())
                .addValue("nameRu", input.getNameRu());

        StringBuilder queryBuilder = new StringBuilder("update ")
                .append(dictionary.getTableName())
                .append(" set name_kz = :nameKz, name_en = :nameEn, name_ru = :nameRu");
        if (dictionary.getType() == DictionaryType.SYSTEM) {
            params.addValue("code", input.getCode());
            queryBuilder.append(", code = :code");
        }
        if (dictionary == Dictionary.POSSIBLE_REASONS_FOR_BIDDING) {
            params.addValue("operationCode", input.getOperationCode());
            queryBuilder.append(", operation_code = :operationCode");
        }
        final String query = queryBuilder.append(" where id = :id").toString();
        System.out.println("query: " + query);
        int updated = npJdbcTemplate.update(query, params);
        return updated;
    }

    @Override
    public DictionaryDto<Long> getKazPostIdFromTable(String id, String tableName) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("kazPostId", id);
        return npJdbcTemplate
                .queryForObject(String.format(SELECT_ONE_QUERY_POST_ID, tableName), params, new DictionaryRowMapper());
    }
}
