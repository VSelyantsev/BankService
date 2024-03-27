package ru.itis.kpfu.selyantsev.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.kpfu.selyantsev.dto.response.BankAccountResponse;
import ru.itis.kpfu.selyantsev.model.BankAccount;

@RequestMapping(value = "/api/v1/transfer")
public interface TransferApi {

    @Operation(summary = "Transfer amount from current auth. User to other user by Phone Number")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Money was successfully transfer, return BankAccount with changed amount",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BankAccount.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Not Enough Money on the Bank Account or incorrect user phone number"
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    BankAccountResponse transferMoney(
            @Parameter(description = "Money that will be debited from current auth. User bank account")
            @RequestParam Double amount,
            @Parameter(description = "User phone number which money will be transfered")
            @RequestParam String userPhoneNumber
    );
}
