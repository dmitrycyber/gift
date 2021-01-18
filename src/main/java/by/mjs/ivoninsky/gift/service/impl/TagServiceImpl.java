package by.mjs.ivoninsky.gift.service.impl;

import by.mjs.ivoninsky.gift.dao.TagDao;
import by.mjs.ivoninsky.gift.dao.exception.GiftNotFoundException;
import by.mjs.ivoninsky.gift.dao.exception.TagNotFoundException;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.model.entity.TagEntity;
import by.mjs.ivoninsky.gift.service.TagService;
import by.mjs.ivoninsky.gift.service.exception.ServiceException;
import by.mjs.ivoninsky.gift.util.converter.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<TagDto> getAllTags() throws ServiceException {
        throw new TagNotFoundException();

//        List<TagEntity> allTags = tagDao.findAllTags();
//
//        return allTags.stream()
//                .map(EntityConverter::convertTagEntityDto)
//                .collect(Collectors.toList());
    }

    @Override
    public TagDto getTagById(Long tagId) throws ServiceException {
        return EntityConverter.convertTagEntityDto(tagDao.findTagById(tagId));
    }

    @Override
    public List<TagDto> getTagByName(String tagName) throws ServiceException {
        List<TagEntity> tagByName = tagDao.findTagByName(tagName);

        return tagByName.stream()
                .map(EntityConverter::convertTagEntityDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TagDto> getTagByPartName(String tagName) throws ServiceException {
        List<TagEntity> tagByName = tagDao.findTagByPartName(tagName);

        return tagByName.stream()
                .map(EntityConverter::convertTagEntityDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto createTag(TagDto tagDto) throws ServiceException {
        TagEntity tagEntity = EntityConverter.convertTagDtoEntity(tagDto);

        return EntityConverter.convertTagEntityDto(tagDao.createTag(tagEntity));
    }

    @Override
    public void deleteTagById(Long tagId) throws ServiceException {
        tagDao.deleteTagById(tagId);
    }
}
