package gov.fdic.tip.mapper;

import gov.fdic.tip.dto.ReviewCycleGroupDTO;
import gov.fdic.tip.entity.ReviewCycleGroup;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;


/**
 * @author Prasad Ravva
 * @Project TIP
 * @Module Review Cycle Group
 * @Date 9/28/2025
 * Mapper interface for converting between ReviewCycleGroup entity and ReviewCycleGroupDTO.
 */

@Mapper(componentModel = "spring")
public interface ReviewCycleGroupMapper {

    @Mapping(target = "reviewCycleGroupId", ignore = true)
    @Mapping(target = "createdDttm", ignore = true)
    @Mapping(target = "updatedDttm", ignore = true)
    ReviewCycleGroup toEntity(ReviewCycleGroupDTO dto);

    ReviewCycleGroupDTO toDto(ReviewCycleGroup entity);

    @Mapping(target = "reviewCycleGroupId", ignore = true)
    @Mapping(target = "createdDttm", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedDttm", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ReviewCycleGroupDTO dto, @MappingTarget ReviewCycleGroup entity);

    List<ReviewCycleGroupDTO> toDtoList(List<ReviewCycleGroup> entities);
}