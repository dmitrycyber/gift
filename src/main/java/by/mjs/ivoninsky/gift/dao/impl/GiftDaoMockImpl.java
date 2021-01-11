package by.mjs.ivoninsky.gift.dao.impl;

import by.mjs.ivoninsky.gift.dao.GiftDao;
import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.dao.exception.GiftNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class GiftDaoMockImpl implements GiftDao {
    private final List<GiftCertificateDto> giftList = new ArrayList<>();

    {
        for (long i = 1; i < 11; i++) {
            giftList.add(GiftCertificateDto.builder()
                    .id(i)
                    .name("name-" + i).build());
        }
    }



    @Override
    public List<GiftCertificateDto> findAllGifts() throws DaoException {
        return giftList;
    }

    @Override
    public GiftCertificateDto findGiftById(Long giftId) throws DaoException {
        return giftList.stream()
                .filter(giftCertificateDto -> giftCertificateDto.getId().equals(giftId))
                .findAny().orElseThrow(GiftNotFoundException::new);
    }

    @Override
    public GiftCertificateDto findGiftByName(String giftName) throws DaoException {
        return giftList.stream()
                .filter(giftCertificateDto -> giftCertificateDto.getName().equals(giftName))
                .findAny().orElseThrow(GiftNotFoundException::new);
    }

    @Override
    public void createGift(List<GiftCertificateDto> giftCertificateDtoList) throws DaoException {
        giftList.addAll(giftCertificateDtoList);
    }

    @Override
    public void updateGift(GiftCertificateDto giftCertificateDto) throws DaoException {

    }

    @Override
    public void deleteGiftById(Long giftId) throws DaoException {
        GiftCertificateDto giftById = giftList.stream()
                .filter(giftCertificateDto -> giftCertificateDto.getId().equals(giftId))
                .findAny().orElseThrow(GiftNotFoundException::new);

        giftList.remove(giftById);
    }

    @Override
    public Set<TagDto> findTagsByGiftId(Long giftId) throws DaoException {
        return null;
    }
}
