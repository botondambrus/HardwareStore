package edu.bbte.idde.abim2109.spring.mapper;

import edu.bbte.idde.abim2109.spring.controller.dto.incoming.PurchaseCreationDTO;
import edu.bbte.idde.abim2109.spring.controller.dto.outgoing.PurchaseDTO;
import edu.bbte.idde.abim2109.spring.model.Purchase;
import org.mapstruct.*;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class PurchaseMapper {
    @IterableMapping(elementTargetType = PurchaseDTO.class)
    public abstract Collection<PurchaseDTO> modelsToDtos(Iterable<Purchase> model);

    @Mapping(target = "hardwareStoreId", ignore = true)
    public abstract PurchaseDTO modelToDto(Purchase model);

    @AfterMapping
    public void setHardwareStoreId(Purchase model, @MappingTarget PurchaseDTO dto) {
        dto.setHardwareStoreId(model.getHardwareStore().getId());
    }

    public abstract Purchase creatonDTOToModel(PurchaseCreationDTO dto);
}

