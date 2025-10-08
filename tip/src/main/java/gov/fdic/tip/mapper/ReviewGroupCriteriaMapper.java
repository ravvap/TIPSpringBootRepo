package gov.fdic.tip.mapper;

import gov.fdic.tip.dto.ReviewGroupCriteriaDTO;
import gov.fdic.tip.entity.ReviewGroupCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Your Name
 * @Project TIP
 * @Module Review Group Criteria
 * @Date 2024-01-15
 * Mapper for converting between ReviewGroupCriteria entity and DTO.
 */
@Mapper(componentModel = "spring")
public interface ReviewGroupCriteriaMapper {

    ReviewGroupCriteriaMapper INSTANCE = Mappers.getMapper(ReviewGroupCriteriaMapper.class);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ReviewGroupCriteria toEntity(ReviewGroupCriteriaDTO dto);

    ReviewGroupCriteriaDTO toDto(ReviewGroupCriteria entity);

    List<ReviewGroupCriteriaDTO> toDtoList(List<ReviewGroupCriteria> entities);

    @Mapping(target = "reviewGroupCriteriaId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntityFromDto(ReviewGroupCriteriaDTO dto, @MappingTarget ReviewGroupCriteria entity);
}