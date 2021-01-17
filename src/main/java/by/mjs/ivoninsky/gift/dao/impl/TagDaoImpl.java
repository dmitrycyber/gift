package by.mjs.ivoninsky.gift.dao.impl;

import by.mjs.ivoninsky.gift.dao.TagDao;
import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.entity.GiftCertificateEntity;
import by.mjs.ivoninsky.gift.model.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Component
public class TagDaoImpl implements TagDao {
    private final JdbcTemplate jdbcTemplate;


    RowMapper<TagEntity> TAG_ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new TagEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"));
    };

    private static final String SELECT_TAGS_BY_NAME = "SELECT * FROM tag WHERE name = ?";

    private static final String COLUMN_ID = "id";

    private static final String INSERT_TAG_QUERY = "insert into tag (name) values (?)";

    private static final int ZERO_INDEX = 0;





    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<TagEntity> findAllTags() throws DaoException {
        return null;
    }

    @Override
    public TagEntity findTagById(Long tagId) throws DaoException {
        return null;
    }

    @Override
    public List<TagEntity> findTagByName(String tagName) throws DaoException {
        return jdbcTemplate.query(SELECT_TAGS_BY_NAME, TAG_ROW_MAPPER, tagName);
    }

    @Override
    public TagEntity createTag(TagEntity tagEntity) throws DaoException {
        List<TagEntity> tagByPartName = findTagByName(tagEntity.getName());

        if (!tagByPartName.isEmpty()){
            return tagByPartName.get(ZERO_INDEX);
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_TAG_QUERY, new String[] {COLUMN_ID});
                    ps.setString(1, tagEntity.getName());
                    return ps;
                },
                keyHolder);


        Long tagId = (Long) keyHolder.getKey();

        tagEntity.setId(tagId);

        return tagEntity;
    }

    @Override
    public TagEntity deleteTagById(Long tagId) throws DaoException {
        return null;
    }



}
