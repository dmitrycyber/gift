package by.mjs.ivoninsky.gift.dao.impl;

import by.mjs.ivoninsky.gift.dao.GiftDao;
import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.entity.GiftCertificateEntity;
import by.mjs.ivoninsky.gift.model.entity.GiftTagEntity;
import by.mjs.ivoninsky.gift.model.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GiftDaoImpl implements GiftDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String COLUMN_ID = "id";
    private static final String FIND_ALL_GIFTS_QUERY = "select * from gift_certificate";
    private static final String FIND_GIFT_BY_ID_QUERY = "select * from gift_certificate where id = ?";
    private static final String FIND_GIFT_TAG_BY_GIFT_ID_QUERY = "select * from gift_tags where gift_id = ?";
    private static final String FIND_TAG_BY_ID_QUERY = "select * from tag where id = ?";


    private static final String SELECT_TAGS_BY_GIFT = "SELECT t.id, t.name "
            + "FROM gift_tags gt, tag t "
            + "WHERE gt.tag_id = t.id and gt.gift_id=?;";

    private static final String INSERT_GIFT_QUERY = "insert into gift_certificate (name,description,price,duration,create_date,last_update_date) values (?, ?, ?, ?, ?, ?)";


    RowMapper<GiftCertificateEntity> GIFT_ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new GiftCertificateEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("price"),
                resultSet.getInt("duration"),
                resultSet.getTimestamp("create_date"),
                resultSet.getTimestamp("last_update_date"));
    };

    RowMapper<TagEntity> TAG_ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new TagEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"));
    };






    @Autowired
    public GiftDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<GiftCertificateEntity> findAllGifts() throws DaoException {
        List<GiftCertificateEntity> query = jdbcTemplate.query(FIND_ALL_GIFTS_QUERY, GIFT_ROW_MAPPER);

        for (GiftCertificateEntity giftCertificateEntity : query){
            Set<TagEntity> tags = getTagsByGiftId(giftCertificateEntity.getId());
            giftCertificateEntity.setTags(tags);
        }

        return query;
    }

    @Override
    public GiftCertificateEntity findGiftById(Long giftId) throws DaoException {

        GiftCertificateEntity entity = jdbcTemplate.queryForObject(FIND_GIFT_BY_ID_QUERY, GIFT_ROW_MAPPER, giftId);

        return entity;
    }

    @Override
    public GiftCertificateEntity findGiftByName(String giftName) throws DaoException {
        return null;
    }

    @Override
    public GiftCertificateEntity createGift(GiftCertificateEntity entity) throws DaoException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_GIFT_QUERY, new String[] {COLUMN_ID});
                    ps.setString(1, entity.getName());
                    ps.setString(2, entity.getDescription());
                    ps.setInt(3, entity.getPrice());
                    ps.setInt(4, entity.getDuration());
                    ps.setTimestamp(5, currentTimestamp);
                    ps.setTimestamp(6, currentTimestamp);


                    return ps;
                },
                keyHolder);


        Long giftId = (Long) keyHolder.getKey();

        entity.setId(giftId);
        entity.setCreateDate(currentTimestamp);
        entity.setLastUpdateDate(currentTimestamp);

        return entity;
    }

    @Override
    public GiftCertificateEntity updateGift(GiftCertificateEntity giftCertificateEntity) throws DaoException {
        return null;
    }

    @Override
    public void deleteGiftById(Long giftId) throws DaoException {

    }

    private Set<TagEntity> getTagsByGiftId(Long giftId){
//        List<GiftTagEntity> query = jdbcTemplate.query(FIND_GIFT_TAG_BY_GIFT_ID_QUERY, GIFT_TAG_ROW_MAPPER, giftId);
//        Set<GiftTagEntity> giftTagEntities = new HashSet<>(query);
//
//        Set<TagEntity> tags = new HashSet<>();
//
//        if (!giftTagEntities.isEmpty()){
//            for (GiftTagEntity giftTagEntity : giftTagEntities){
//                Long tag_id = giftTagEntity.getTag_id();
//                System.out.println("TAG ID " + tag_id);
//
//                TagEntity tagEntity = jdbcTemplate.queryForObject(FIND_TAG_BY_ID_QUERY, TAG_ROW_MAPPER, tag_id);
//
//                tags.add(tagEntity);
//
//                System.out.println("TAG ENTITY " + tagEntity);
//            }
//        }
        List<TagEntity> queryTags = jdbcTemplate.query(SELECT_TAGS_BY_GIFT, TAG_ROW_MAPPER, giftId);

        return new HashSet<>(queryTags);
    }

}
