package by.mjs.ivoninsky.gift.service;

import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.CustomSearchRequest;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.service.exception.ServiceException;

import java.util.List;
import java.util.Set;

public interface GiftService {
    List<GiftCertificateDto> getAllGifts();
    GiftCertificateDto getGiftById(Long giftId);

    List<GiftCertificateDto> searchGifts(CustomSearchRequest customSearchRequest);

    GiftCertificateDto createGift(GiftCertificateDto giftCertificateDto);
    GiftCertificateDto updateGift(GiftCertificateDto giftCertificateDto);

    void deleteGiftById(Long giftId);
}
