package by.mjs.ivoninsky.gift.service;

import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.CustomSearchRequest;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.service.exception.ServiceException;

import java.util.List;
import java.util.Set;

public interface GiftService {
    List<GiftCertificateDto> getAllGifts() throws ServiceException;
    GiftCertificateDto getGiftById(Long giftId) throws ServiceException;

    List<GiftCertificateDto> searchGifts(CustomSearchRequest customSearchRequest) throws ServiceException;

    GiftCertificateDto createGift(GiftCertificateDto giftCertificateDto) throws ServiceException;
    GiftCertificateDto updateGift(GiftCertificateDto giftCertificateDto) throws ServiceException;

    void deleteGiftById(Long giftId) throws ServiceException;
}
