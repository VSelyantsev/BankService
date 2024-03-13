package ru.itis.kpfu.selyantsev.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itis.kpfu.selyantsev.dto.request.*;
import ru.itis.kpfu.selyantsev.dto.response.EmailAddressResponse;
import ru.itis.kpfu.selyantsev.dto.response.PhoneNumberResponse;
import ru.itis.kpfu.selyantsev.dto.response.UserResponse;
import ru.itis.kpfu.selyantsev.model.EmailAddress;
import ru.itis.kpfu.selyantsev.model.PhoneNumber;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/api/v1/users")
@Tag(name = "User", description = "Api for performing interaction with USER entity")
public interface UserApi {

    @Operation(summary = "Create User by UserRequest")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User Created and Return UserResponse Performance",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserRequest.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/registration",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    UserResponse create(
            @Parameter(name = "Request representation of Object", required = true)
            @RequestBody UserRequest userRequest
    );

    @Operation(summary = "Add Phone Number to Numbers List of Authenticated User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Number successfully added to list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid Phone Number"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @RequestMapping(
            value = "/addPhoneNumber",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE,
            method = RequestMethod.PATCH
    )
    @ResponseStatus(HttpStatus.OK)
    UserResponse addPhoneNumber(
            @Parameter(name = "Object representation of Phone Number", required = true)
            @RequestBody PhoneNumberRequest phoneNumberRequest
    );

    @Operation(summary = "Edit selected Phone Number")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Selected Phone Number was successfully updated",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid Phone Number"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @RequestMapping(
            value = "/editPhoneNumber",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE,
            method = RequestMethod.PATCH
    )
    @ResponseStatus(HttpStatus.OK)
    UserResponse editPhoneNumber(
            @Parameter(name = "Object representation of Edit Phone Number Request", required = true)
            @RequestBody EditPhoneNumberRequest editPhoneNumberRequest
    );

    @Operation(summary = "Add Email to Emails List of Authenticated User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Email was successfully added",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid Email"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @RequestMapping(
            value = "/addEmail",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE,
            method = RequestMethod.PATCH
    )
    @ResponseStatus(HttpStatus.OK)
    UserResponse addEmail(
            @Parameter(name = "Add Email to Emails List of Authenticated User", required = true,
                    content = @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = String.class),
                        examples = @ExampleObject(
                                name = "Example of Request",
                                value = "{\n \"email\": \"exampleOfEmail@mail.ru\"\n}",
                                description = "Validated By @Email"))
            ) @RequestBody EmailAddressRequest email);

    @Operation(summary = "Edit Selected Email")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Selected Email Was Successfully Updated",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid Email"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @RequestMapping(
            value = "/editEmail",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE,
            method = RequestMethod.PATCH
    )
    @ResponseStatus(HttpStatus.OK)
    UserResponse editEmail(
            @Parameter(name = "Object representation of Edit Email Request")
            @RequestBody EditEmailRequest editEmailRequest
    );

    @Operation(summary = "Delete Selected Phone Number")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Selected Phone Number Was Successfully Deleted",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))
            )
    })
    @RequestMapping(
            value = "/deletePhoneNumber",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE,
            method = RequestMethod.PATCH
    )
    @ResponseStatus(HttpStatus.OK)
    List<PhoneNumberResponse> deletePhoneNumber(
            @Parameter(name = "Phone Number to delete", required = true,
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(
                                    name = "Example of Request",
                                    value = "{\n \"phoneNumber\": \"88004908142\"\n}",
                                    description = "Validated By Regular Expression"))
            ) @RequestBody PhoneNumberRequest phoneNumber
    );

    @Operation(summary = "Delete Selected Email")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Selected Email Was Successfully Deleted",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid Email"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @RequestMapping(
            value = "/deleteEmail",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE,
            method = RequestMethod.PATCH
    )
    @ResponseStatus(HttpStatus.OK)
    List<EmailAddressResponse> deleteEmail(
            @Parameter(name = "Email to delete", required = true,
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(
                                    name = "Example of Request",
                                    value = "{\n \"email\": \"exampleOfEmail@mail.ru\"\n}",
                                    description = "Validated By @Email"))
            ) @RequestBody EmailAddressRequest email);
}
