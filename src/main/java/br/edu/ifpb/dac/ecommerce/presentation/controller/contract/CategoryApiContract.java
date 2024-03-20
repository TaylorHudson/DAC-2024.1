package br.edu.ifpb.dac.ecommerce.presentation.controller.contract;

import br.edu.ifpb.dac.ecommerce.presentation.dto.CategoryRequestDto;
import br.edu.ifpb.dac.ecommerce.presentation.dto.CategoryResponseDto;
import br.edu.ifpb.dac.ecommerce.presentation.dto.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "Endpoints related to category")
@RequestMapping("/category")
public interface CategoryApiContract {

    @Operation(
            summary = "Save a category",
            description = "Save a category entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - see the error message",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping
    ResponseEntity<CategoryResponseDto> save(
            @RequestBody @Valid CategoryRequestDto requestDto
    );

    @Operation(
            summary = "Get all categories",
            description = "Get all category entities and their data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping
    ResponseEntity<List<CategoryResponseDto>> getCategories();

    @Operation(
            summary = "Get a category",
            description = "Get a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found - see the error message",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<CategoryResponseDto> getCategoryById(
            @Parameter(
                    description = "Id of the category",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id
    );

    @Operation(
            summary = "Update a category",
            description = "Update a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found - see the error message",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - see the error message",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/{id}")
    ResponseEntity<CategoryResponseDto> update(
            @Parameter(
                    description = "Id of the category",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id,
            @Parameter(
                    description = "The updated category payload",
                    required = true
            )
            @RequestBody @Valid CategoryRequestDto requestDto
    );

    @Operation(
            summary = "Delete a category",
            description = "Delete a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found - see the error message",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @Parameter(
                    description = "ID of the category",
                    required = true
            )
            @PathVariable Long id
    );

}