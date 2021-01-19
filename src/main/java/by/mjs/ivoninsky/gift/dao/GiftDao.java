package by.mjs.ivoninsky.gift.dao;

import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.CustomSearchRequest;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.model.entity.GiftCertificateEntity;
import by.mjs.ivoninsky.gift.model.entity.TagEntity;

import java.util.List;
import java.util.Set;

public interface GiftDao {
    List<GiftCertificateEntity> findAllGifts();
    GiftCertificateEntity findGiftById(Long giftId) throws DaoException;
    List<GiftCertificateEntity> findAndSortGift(CustomSearchRequest customSearchRequest);
//    List<GiftCertificateEntity> findGiftByPrice(CustomSearchRequest customSearchRequest) throws DaoException;
//    List<GiftCertificateEntity> findGiftByDuration(CustomSearchRequest customSearchRequest) throws DaoException;

    GiftCertificateEntity createGift(GiftCertificateEntity giftCertificateEntity);
    GiftCertificateEntity updateGift(GiftCertificateEntity giftCertificateEntity);

    void deleteGiftById(Long giftId);
}