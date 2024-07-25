package br.edu.ifpb.dac.ecommerce.presentation.controller.contract;

import br.edu.ifpb.dac.ecommerce.presentation.dto.*;
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

@Tag(name = "User", description = "Endpoints related to user")
@RequestMapping("/user")
public interface UserApiContract {

    @Operation(
            summary = "Save user",
            description = "Save user entity")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(
                            schema = @Schema(implementation = UserResponseDto.class)
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
    @PostMapping
    ResponseEntity<UserResponseDto> save(
            @RequestBody @Valid UserRequestDto requestDto
    );

    @Operation(
            summary = "Get all user",
            description = "Get all user entities and their data")
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
    ResponseEntity<List<UserResponseDto>> getUsers();

    @Operation(
            summary = "Get a user",
            description = "Get a user by id"
    )
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
    ResponseEntity<UserResponseDto> getUserById(
            @Parameter(
                    description = "Id of the user",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id
    );

    @Operation(
            summary = "Update a user"
    )
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
    @PutMapping
    ResponseEntity<UserResponseDto> update(
            @Parameter(
                    description = "The updated user payload",
                    required = true
            )
            @RequestBody @Valid UserRequestDto requestDto
    );

    @Operation(
            summary = "Delete a user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
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
                    description = "ID of the user",
                    required = true
            )
            @PathVariable Long id
    );

}