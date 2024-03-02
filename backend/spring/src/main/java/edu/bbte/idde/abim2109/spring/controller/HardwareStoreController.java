package edu.bbte.idde.abim2109.spring.controller;

import edu.bbte.idde.abim2109.spring.exception.BadRequestException;
import edu.bbte.idde.abim2109.spring.exception.NotFoundException;
import edu.bbte.idde.abim2109.spring.mapper.HardwareStoreMapper;
import edu.bbte.idde.abim2109.spring.model.HardwareStore;
import edu.bbte.idde.abim2109.spring.controller.dto.incoming.HardwareStoreCreationDTO;
import edu.bbte.idde.abim2109.spring.controller.dto.outgoing.HardwareStoreFullDto;
import edu.bbte.idde.abim2109.spring.controller.dto.outgoing.HardwareStoreReducedDto;
import edu.bbte.idde.abim2109.spring.model.filter.HardwareStoreFilter;
import edu.bbte.idde.abim2109.spring.service.jpa.HardwareStoreJpaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@Controller
@RestController
@Slf4j
@RequestMapping("api/hardware-stores")
public class HardwareStoreController {
    @Autowired
    HardwareStoreMapper hardwareStoreMapper;

    @Autowired
    HardwareStoreJpaService hardwareStoreService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Collection<HardwareStoreReducedDto>> findAllHardwareStores(
            @ModelAttribute HardwareStoreFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "desc") String sortOrder
    ) {
        log.info("GET /api/hardware-stores");

        try {
            HardwareStore.class.getDeclaredField(sortField);
        } catch (NoSuchFieldException e) {
            throw new BadRequestException("Invalid sortField: " + sortField);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        Page<HardwareStore> hardwareStorePage = hardwareStoreService.getAllHardwareStores(pageable, filter);
        Collection<HardwareStoreReducedDto> hardwareStoreDtos =
                hardwareStoreMapper.modelsToReducedDtos(hardwareStorePage.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(hardwareStorePage.getTotalElements()));

        return new ResponseEntity<>(hardwareStoreDtos, headers, HttpStatus.OK);
    }

    @GetMapping("/{hardwareStoreId}")
    @ResponseStatus(HttpStatus.OK)
    public HardwareStoreFullDto findHardwareStoreById(@PathVariable("hardwareStoreId") Long hardwareStoreId) {
        log.info("GET /api/hardware-stores/{}", hardwareStoreId);
        HardwareStore hardwareStore = hardwareStoreService.getHardwareStoreById(hardwareStoreId);

        return hardwareStoreMapper.modelToFullDto(hardwareStore);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HardwareStoreFullDto createHardwareStore(
            @RequestBody @Valid HardwareStoreCreationDTO hardwareStoreCreationDTO) {


        if (hardwareStoreCreationDTO == null) {
            throw new BadRequestException("Hardware store cannot be null");
        }

        HardwareStore hardwareStore = hardwareStoreMapper.creatonDTOToModel(hardwareStoreCreationDTO);
        HardwareStore createdHardwareStore = hardwareStoreService.saveHardwareStore(hardwareStore);

        return hardwareStoreMapper.modelToFullDto(createdHardwareStore);
    }

    @PutMapping("/{hardwareStoreId}")
    @ResponseStatus(HttpStatus.OK)
    public HardwareStoreFullDto updateHardwareStore(
            @PathVariable("hardwareStoreId") Long hardwareStoreId,
            @RequestBody @Valid HardwareStoreCreationDTO hardwareStoreCreationDTO) {
        log.info("PUT /api/hardware-stores/{}", hardwareStoreId);

        if (hardwareStoreCreationDTO == null) {
            throw new BadRequestException("Hardware store item cannot be null");
        }

        HardwareStore getHardwareStore = hardwareStoreService.getHardwareStoreById(hardwareStoreId);
        if (getHardwareStore == null) {
            throw new NotFoundException("Hardware store item not found");
        }

        HardwareStore hardwareStore = hardwareStoreMapper.creatonDTOToModel(hardwareStoreCreationDTO);

        getHardwareStore.setName(hardwareStore.getName());
        getHardwareStore.setCategory(hardwareStore.getCategory());
        getHardwareStore.setDescription(hardwareStore.getDescription());
        getHardwareStore.setPrice(hardwareStore.getPrice());
        getHardwareStore.setQuantity(hardwareStore.getQuantity());
        HardwareStore updatedHardwareStore =
                hardwareStoreService.saveHardwareStore(getHardwareStore);

        return hardwareStoreMapper.modelToFullDto(updatedHardwareStore);
    }

    @DeleteMapping("/{hardwareStoreId}")
    @ResponseStatus(HttpStatus.OK)
    public Integer deleteHardwareStore(
            @PathVariable("hardwareStoreId") Long hardwareStoreId) {
        log.info("DELETE /api/hardware-stores/{}", hardwareStoreId);

        HardwareStore getHardwareStore = hardwareStoreService.getHardwareStoreById(hardwareStoreId);

        if (getHardwareStore == null) {
            throw new NotFoundException("Hardware store item not found");
        }

        hardwareStoreService.deleteHardwareStore(hardwareStoreId);

        return hardwareStoreId.intValue();
    }
}
