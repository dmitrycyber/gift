package by.mjs.ivoninsky.gift.dao.impl;

import by.mjs.ivoninsky.gift.dao.GiftTagDao;
import by.mjs.ivoninsky.gift.model.entity.GiftTagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class GiftTagDaoImpl implements GiftTagDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_GIFT_TAG_QUERY = "insert into gift_tags (gift_id, tag_id) values (?, ?)";
    RowMapper<GiftTagEntity> GIFT_TAG_ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new GiftTagEntity(
                resultSet.getLong("gift_id"),
                resultSet.getLong("tag_id"));
    };

    private static final String COLUMN_ID = "id";


    @Autowired
    public GiftTagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertNewGiftTagEntity(GiftTagEntity giftTagEntity) {
        jdbcTemplate.update(INSERT_GIFT_TAG_QUERY, giftTagEntity.getGiftId(), giftTagEntity.getTagId());
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(
//                connection -> {
//                    PreparedStatement ps =
//                            connection.prepareStatement(INSERT_GIFT_TAG_QUERY, new String[] {COLUMN_ID});
//                    ps.setLong(1, giftTagEntity.getGiftId());
//                    ps.setLong(2, giftTagEntity.getTagId());
//                    return ps;
//                },
//                keyHolder);

    }
}
