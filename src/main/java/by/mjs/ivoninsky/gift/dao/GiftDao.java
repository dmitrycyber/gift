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
    List<GiftCertificateEntity> findAllGifts() throws DaoException;
    GiftCertificateEntity findGiftById(Long giftId) throws DaoException;
    List<GiftCertificateEntity> findGiftByName(CustomSearchRequest customSearchRequest) throws DaoException;
    List<GiftCertificateEntity> findGiftByPrice(CustomSearchRequest customSearchRequest) throws DaoException;
    List<GiftCertificateEntity> findGiftByDuration(CustomSearchRequest customSearchRequest) throws DaoException;

    GiftCertificateEntity createGift(GiftCertificateEntity giftCertificateEntity) throws DaoException;
    GiftCertificateEntity updateGift(GiftCertificateEntity giftCertificateEntity) throws DaoException;

    void deleteGiftById(Long giftId) throws DaoException;
}