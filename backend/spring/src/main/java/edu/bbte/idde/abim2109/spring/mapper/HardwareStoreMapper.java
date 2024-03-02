package edu.bbte.idde.abim2109.spring.mapper;

import edu.bbte.idde.abim2109.spring.model.HardwareStore;
import edu.bbte.idde.abim2109.spring.controller.dto.incoming.HardwareStoreCreationDTO;
import edu.bbte.idde.abim2109.spring.controller.dto.outgoing.HardwareStoreFullDto;
import edu.bbte.idde.abim2109.spring.controller.dto.outgoing.HardwareStoreReducedDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class HardwareStoreMapper {
    @IterableMapping(elementTargetType = HardwareStoreReducedDto.class)
    public abstract Collection<HardwareStoreReducedDto> modelsToReducedDtos(Iterable<HardwareStore> model);

    public abstract HardwareStoreFullDto modelToFullDto(HardwareStore model);

    public abstract HardwareStore creatonDTOToModel(HardwareStoreCreationDTO dto);
}
