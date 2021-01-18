package by.mjs.ivoninsky.gift.dao.impl;

import by.mjs.ivoninsky.gift.dao.GiftDao;
import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.dao.exception.GiftNotFoundException;
import by.mjs.ivoninsky.gift.model.CustomSearchRequest;
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
    private static final String FIND_GIFT_BY_NAME_QUERY = "select * from gift_certificate where name like ?";
    private static final String FIND_GIFT_BY_PRICE_QUERY = "select * from gift_certificate where price >= ? and price <= ?";
    private static final String FIND_GIFT_BY_DURATION_QUERY = "select * from gift_certificate where duration >= ? and duration <= ?";
    private static final String DELETE_GIFT_BY_ID = "delete from gift_certificate where id = ?";
    private static final String UPDATE_BY_ID_QUERY = "UPDATE gift_certificate set name=?, description=?, price=?, duration=? WHERE id=?;";
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
        List<GiftCertificateEntity> query = jdbcTemplate.query(FIND_GIFT_BY_ID_QUERY, GIFT_ROW_MAPPER, giftId);

        if (query.size() != 1){
            throw new GiftNotFoundException();
        }

        return query.get(0);
    }

    @Override
    public List<GiftCertificateEntity> findGiftByName(CustomSearchRequest customSearchRequest) throws DaoException {
        List<GiftCertificateEntity> query = jdbcTemplate.query(FIND_GIFT_BY_NAME_QUERY, GIFT_ROW_MAPPER,
                "%" + customSearchRequest.getName() + "%");

        for (GiftCertificateEntity giftCertificateEntity : query){
            Set<TagEntity> tags = getTagsByGiftId(giftCertificateEntity.getId());
            giftCertificateEntity.setTags(tags);
        }

        return query;
    }

    @Override
    public List<GiftCertificateEntity> findGiftByPrice(CustomSearchRequest customSearchRequest) throws DaoException {
        List<GiftCertificateEntity> query = jdbcTemplate.query(FIND_GIFT_BY_PRICE_QUERY, GIFT_ROW_MAPPER,
                customSearchRequest.getPriceFrom(), customSearchRequest.getPriceTo());

        for (GiftCertificateEntity giftCertificateEntity : query){
            Set<TagEntity> tags = getTagsByGiftId(giftCertificateEntity.getId());
            giftCertificateEntity.setTags(tags);
        }

        System.out.println("PRICE RESP " + query);

        return query;
    }

    @Override
    public List<GiftCertificateEntity> findGiftByDuration(CustomSearchRequest customSearchRequest) throws DaoException {
        List<GiftCertificateEntity> query = jdbcTemplate.query(FIND_GIFT_BY_DURATION_QUERY, GIFT_ROW_MAPPER,
                customSearchRequest.getDurationFrom(), customSearchRequest.getDurationTo());

        for (GiftCertificateEntity giftCertificateEntity : query){
            Set<TagEntity> tags = getTagsByGiftId(giftCertificateEntity.getId());
            giftCertificateEntity.setTags(tags);
        }

        return query;
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
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        int rows = jdbcTemplate.update(UPDATE_BY_ID_QUERY,
                giftCertificateEntity.getName(),
                giftCertificateEntity.getDescription(),
                giftCertificateEntity.getPrice(),
                giftCertificateEntity.getDuration(),
                giftCertificateEntity.getId());

        if (rows == 0){
            throw new GiftNotFoundException();
        }

        giftCertificateEntity.setLastUpdateDate(currentTimestamp);

        return giftCertificateEntity;
    }

    @Override
    public void deleteGiftById(Long giftId) throws DaoException {
        jdbcTemplate.update(DELETE_GIFT_BY_ID, giftId);
    }

    private Set<TagEntity> getTagsByGiftId(Long giftId){
        List<TagEntity> queryTags = jdbcTemplate.query(SELECT_TAGS_BY_GIFT, TAG_ROW_MAPPER, giftId);

        return new HashSet<>(queryTags);
    }

}
