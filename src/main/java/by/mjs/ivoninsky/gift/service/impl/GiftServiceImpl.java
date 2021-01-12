package by.mjs.ivoninsky.gift.service.impl;

import by.mjs.ivoninsky.gift.dao.GiftDao;
import by.mjs.ivoninsky.gift.model.CustomSearchRequest;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.service.GiftService;
import by.mjs.ivoninsky.gift.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class GiftServiceImpl implements GiftService {
    private final GiftDao giftDao;

    @Autowired
    public GiftServiceImpl(@Qualifier("giftDaoImpl") GiftDao giftDao) {
        this.giftDao = giftDao;
    }


    @Override
    public List<GiftCertificateDto> getAllGifts() throws ServiceException {
        return giftDao.findAllGifts();
    }

    @Override
    public GiftCertificateDto getGiftById(Long giftId) throws ServiceException {
        return giftDao.findGiftById(giftId);
//        throw new ServiceException();
    }

    @Override
    public List<GiftCertificateDto> searchGifts(CustomSearchRequest customSearchRequest) throws ServiceException {
        //TODO add logic, delete getByName from DAO
        return null;
    }

    //return created object
    @Override
    public void createGift(List<GiftCertificateDto> giftCertificateDtoList) throws ServiceException {
        giftDao.createGift(giftCertificateDtoList);
    }

    //return updated object
    @Override
    public void updateGift(GiftCertificateDto giftCertificateDto) throws ServiceException {
        giftDao.updateGift(giftCertificateDto);
    }

    @Override
    public void deleteGiftById(Long giftId) throws ServiceException {
        giftDao.deleteGiftById(giftId);
    }

    @Override
    public Set<TagDto> getTagsByGiftId(Long giftId) throws ServiceException {
        return giftDao.findTagsByGiftId(giftId);
    }
}
