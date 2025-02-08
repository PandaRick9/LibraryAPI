package by.baraznov.booktrackerservice.mapper;

import org.mapstruct.Builder;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * BaseMapper is a generic interface for mapping between entity and DTO objects.
 * It provides methods for converting single objects and lists, as well as merging updates.
 *
 * @param <E> Entity type
 * @param <D> DTO type
 */
@MapperConfig(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface BaseMapper<E,D> {
    /**
     * Converts an entity to a DTO.
     * @param e Entity instance
     * @return DTO instance
     */
    D toDto(E e);

    /**
     * Converts a DTO to an entity.
     * @param d DTO instance
     * @return Entity instance
     */
    E toEntity(D d);

    /**
     * Converts a list of entities to a list of DTOs.
     * @param list List of entities
     * @return List of DTOs
     */
    List<D> toDtos(List<E> list);

    /**
     * Converts a list of DTOs to a list of entities.
     * @param list List of DTOs
     * @return List of entities
     */
    List<E> toEntities(List<D> list);

    /**
     * Merges DTO data into an existing entity.
     * @param entity Target entity
     * @param dto Source DTO
     * @return Updated entity
     */
    E merge(@MappingTarget E entity, D dto);
}