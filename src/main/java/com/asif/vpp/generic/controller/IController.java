package com.asif.vpp.generic.controller;

import com.asif.vpp.generic.payload.request.IDto;
import com.asif.vpp.generic.payload.response.MessageResponse;
import com.asif.vpp.generic.payload.response.PageData;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IController<D extends IDto> {

//    PageData getAll(Boolean isActive, Pageable pageable);

    <T>T getSingle(Long id);

    ResponseEntity<MessageResponse> create(D d);

    ResponseEntity<MessageResponse> update(D d, Long id);

    ResponseEntity<MessageResponse> updateActiveStatus(@PathVariable Long id, Boolean isActive);

    PageData getAll(Boolean isActive, Pageable pageable);
}
