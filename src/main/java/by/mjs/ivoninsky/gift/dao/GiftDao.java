package by.mjs.ivoninsky.gift.dao;

import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;

import java.util.List;
import java.util.Set;

public interface GiftDao {
    List<GiftCertificateDto> findAllGifts() throws DaoException;
    GiftCertificateDto findGiftById(Long giftId) throws DaoException;
    GiftCertificateDto findGiftByName(String giftName) throws DaoException;
    void createGift(List<GiftCertificateDto> giftCertificateDtoList) throws DaoException;
    void updateGift(GiftCertificateDto giftCertificateDto) throws DaoException;
    void deleteGiftById(Long giftId) throws DaoException;
    Set<TagDto> findTagsByGiftId(Long giftId) throws DaoException;
}
