package by.mjs.ivoninsky.gift.service.impl;

import by.mjs.ivoninsky.gift.dao.TagDao;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.service.TagService;
import by.mjs.ivoninsky.gift.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }


    @Override
    public List<TagDto> getAllTags() throws ServiceException {
        return tagDao.findAllTags();
    }

    @Override
    public TagDto getTagById(Long tagId) throws ServiceException {
        return tagDao.findTagById(tagId);
    }

    @Override
    public List<TagDto> getTagByName(String tagName) throws ServiceException {


        return tagDao.findTagByName(tagName);
    }

    @Override
    public void createTag(List<TagDto> giftCertificateDtoList) throws ServiceException {
        tagDao.createTag(giftCertificateDtoList);
    }

    @Override
    public void deleteTagById(Long tagId) throws ServiceException {
        tagDao.deleteTagById(tagId);
    }

    @Override
    public Set<GiftCertificateDto> getGiftsByTagId(Long tagId) throws ServiceException {
        return tagDao.findGiftsByTagId(tagId);
    }
}
