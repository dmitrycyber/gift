package by.mjs.ivoninsky.gift.dao.impl;

import by.mjs.ivoninsky.gift.dao.GiftDao;
import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

@Component
public class GiftDaoImpl implements GiftDao {

    private final JdbcTemplate jdbcTemplate;

    RowMapper<GiftCertificateDto> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new GiftCertificateDto(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("duration"),
                null,
                null,
//                resultSet.getString("create_date"),
//                resultSet.getString("last_update_date"),
                null);
    };


    @Autowired
    public GiftDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<GiftCertificateDto> findAllGifts() throws DaoException {
        return jdbcTemplate.query("select * from gift_certificate", ROW_MAPPER);
    }

    @Override
    public GiftCertificateDto findGiftById(Long giftId) throws DaoException {
        return null;
    }

    @Override
    public GiftCertificateDto findGiftByName(String giftName) throws DaoException {
        return null;
    }

    @Override
    public void createGift(List<GiftCertificateDto> giftCertificateDtoList) throws DaoException {

    }

    @Override
    public void updateGift(GiftCertificateDto giftCertificateDto) throws DaoException {

    }

    @Override
    public void deleteGiftById(Long giftId) throws DaoException {

    }

    @Override
    public Set<TagDto> findTagsByGiftId(Long giftId) throws DaoException {
        return null;
    }
}
