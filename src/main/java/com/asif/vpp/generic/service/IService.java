package com.asif.vpp.generic.service;

import com.asif.vpp.generic.model.BaseEntity;
import com.asif.vpp.generic.payload.request.IDto;
import com.asif.vpp.generic.payload.response.PageData;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface IService<E extends BaseEntity, D extends IDto> {

    E create(D d);

    E update(D d, Long id);

    <T> T getSingle(Long id);

    E findById(Long id);

    void updateActiveStatus(Long id, Boolean b);

    E saveItem(E entity);

    List<E> saveItemList(List<E> entityList);

    default void validateClientData(D d, Long id) {
    }

    PageData getAll(Boolean isActive, Pageable pageable);

    List<E> findAllByIds(Collection<Long> ids);
}
