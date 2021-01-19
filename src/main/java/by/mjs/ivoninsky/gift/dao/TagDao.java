package by.mjs.ivoninsky.gift.dao;

import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.model.entity.GiftCertificateEntity;
import by.mjs.ivoninsky.gift.model.entity.TagEntity;

import java.util.List;
import java.util.Set;

public interface TagDao {
    List<TagEntity> findAllTags();
    TagEntity findTagById(Long tagId);
    List<TagEntity> findTagByName(String tagName);
    List<TagEntity> findTagByPartName(String tagName);

    TagEntity createTag(TagEntity tagEntity);
    void deleteTagById(Long tagId);


}
