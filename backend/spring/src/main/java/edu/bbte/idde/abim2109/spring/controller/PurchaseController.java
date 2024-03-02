package edu.bbte.idde.abim2109.spring.controller;

import edu.bbte.idde.abim2109.spring.controller.dto.incoming.PurchaseCreationDTO;
import edu.bbte.idde.abim2109.spring.controller.dto.outgoing.PurchaseDTO;
import edu.bbte.idde.abim2109.spring.exception.BadRequestException;
import edu.bbte.idde.abim2109.spring.mapper.HardwareStoreMapper;
import edu.bbte.idde.abim2109.spring.mapper.PurchaseMapper;
import edu.bbte.idde.abim2109.spring.model.HardwareStore;
import edu.bbte.idde.abim2109.spring.model.Purchase;
import edu.bbte.idde.abim2109.spring.model.filter.PurchaseFilter;
import edu.bbte.idde.abim2109.spring.service.jpa.HardwareStoreJpaService;
import edu.bbte.idde.abim2109.spring.service.jpa.PurchaseJpaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
import java.util.Date;

@Profile("jpa")
@Controller
@RestController
@Slf4j
@RequestMapping("api/hardware-stores/{hardwareStoreId}/purchases")
public class PurchaseController {
    @Autowired
    HardwareStoreMapper hardwareStoreMapper;

    @Autowired
    HardwareStoreJpaService hardwareStoreService;

    @Autowired
    PurchaseJpaService purchaseService;

    @Autowired
    PurchaseMapper purchaseMapper;

    @GetMapping()
    public ResponseEntity<Collection<PurchaseDTO>> findAllPurchaseByHardwareStore(
            @PathVariable String hardwareStoreId,
            @ModelAttribute PurchaseFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        log.info("GET /api/hardware-stores/{}/purchases", hardwareStoreId);

        try {
            Purchase.class.getDeclaredField(sortField);
        } catch (NoSuchFieldException e) {
            throw new BadRequestException("Invalid sortField: " + sortField);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        Page<Purchase> purchasePage = purchaseService.getAllPurchasesByHardwareStoreId(
                Long.valueOf(hardwareStoreId), pageable, filter);

        Collection<PurchaseDTO> purchaseDtos = purchaseMapper.modelsToDtos(purchasePage.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(purchasePage.getTotalElements()));

        return new ResponseEntity<>(purchaseDtos, headers, HttpStatus.OK);
    }

    @GetMapping("{purchaseId}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseDTO getPurchase(
            @PathVariable String hardwareStoreId,
            @PathVariable String purchaseId) {

        if (purchaseId == null) {
            log.error("Purchase id is null");
            throw new BadRequestException("Purchase id is null");
        }

        HardwareStore hardwareStore = hardwareStoreService.getHardwareStoreById(Long.valueOf(hardwareStoreId));

        Purchase purchase = hardwareStore.getPurchases().stream()
                .filter(purchase1 -> purchase1.getId().equals(Long.valueOf(purchaseId)))
                .findFirst().orElse(null);

        if (purchase == null) {
            log.error("Purchase with id {} not found", purchaseId);
            throw new BadRequestException("Purchase not found");
        }

        return purchaseMapper.modelToDto(purchase);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseDTO addPurchase(
            @RequestBody @Valid PurchaseCreationDTO purchaseCreationDTO,
            @PathVariable String hardwareStoreId) {
        log.info("POST /api/hardware-store/{}/add-purchase", hardwareStoreId);

        HardwareStore hardwareStore = hardwareStoreService.getHardwareStoreById(Long.valueOf(hardwareStoreId));

        Purchase purchase = purchaseMapper.creatonDTOToModel(purchaseCreationDTO);
        purchase.setHardwareStore(hardwareStore);
        purchase.setDate(new Date());

        hardwareStore.getPurchases().add(purchase);
        hardwareStoreService.saveHardwareStore(hardwareStore);

        HardwareStore savedHardwareStore = hardwareStoreService.saveHardwareStore(hardwareStore);
        Purchase savedPurchase = savedHardwareStore.getPurchases().stream()
                .filter(pur -> pur.getName().equals(purchase.getName())
                        && pur.getEmail().equals(purchase.getEmail())
                        && pur.getAddress().equals(purchase.getAddress()) && pur.getQuantity()
                            .equals(purchase.getQuantity())
                )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Purchase not found in saved HardwareStore"));

        return purchaseMapper.modelToDto(savedPurchase);
    }

    @PutMapping("{purchaseId}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseDTO updatePurchase(
            @PathVariable String hardwareStoreId,
            @PathVariable String purchaseId,
            @RequestBody @Valid PurchaseCreationDTO purchaseUpdateDTO) {
        log.info("PUT /api/hardware-stores/{}/purchases/{}", hardwareStoreId, purchaseId);

        if (purchaseId == null) {
            log.error("Purchase id is null");
            throw new BadRequestException("Purchase id is null");
        }

        HardwareStore hardwareStore = hardwareStoreService.getHardwareStoreById(Long.valueOf(hardwareStoreId));

        Purchase purchase = hardwareStore.getPurchases().stream()
                .filter(purchase1 -> purchase1.getId().equals(Long.valueOf(purchaseId)))
                .findFirst().orElse(null);

        if (purchase == null) {
            log.error("Purchase with id {} not found", purchaseId);
            throw new BadRequestException("Purchase not found");
        }

        Purchase purchaseUpdate = purchaseMapper.creatonDTOToModel(purchaseUpdateDTO);

        purchase.setAddress(purchaseUpdate.getAddress());
        purchase.setName(purchaseUpdate.getName());
        purchase.setEmail(purchaseUpdate.getEmail());
        purchase.setQuantity(purchaseUpdate.getQuantity());

        hardwareStoreService.saveHardwareStore(hardwareStore);

        return purchaseMapper.modelToDto(purchase);
    }

    @DeleteMapping("{purchaseId}")
    @ResponseStatus(HttpStatus.OK)
    public Long deletePurchase(
            @PathVariable String hardwareStoreId,
            @PathVariable String purchaseId) {
        log.info("DELETE /api/hardware-stores/{}/purchases/{}", hardwareStoreId, purchaseId);

        if (purchaseId == null) {
            log.error("Purchase id is null");
            throw new BadRequestException("Purchase id is null");
        }

        HardwareStore hardwareStore = hardwareStoreService.getHardwareStoreById(Long.valueOf(hardwareStoreId));

        Purchase purchase = hardwareStore.getPurchases().stream()
                .filter(purchase1 -> purchase1.getId().equals(Long.valueOf(purchaseId)))
                .findFirst().orElse(null);

        if (purchase == null) {
            log.error("Purchase with id {} not found", purchaseId);
            throw new BadRequestException("Purchase not found");
        }

        hardwareStore.getPurchases().remove(purchase);
        hardwareStoreService.saveHardwareStore(hardwareStore);

        return Long.valueOf(purchaseId);
    }
}
