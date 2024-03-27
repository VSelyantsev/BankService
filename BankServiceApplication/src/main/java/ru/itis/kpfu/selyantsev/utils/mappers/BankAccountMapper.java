package ru.itis.kpfu.selyantsev.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.kpfu.selyantsev.dto.response.BankAccountResponse;
import ru.itis.kpfu.selyantsev.model.BankAccount;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    @Mapping(target = "userId", expression = "java(BankAccountMapper.getUserIdFromBankAccount(bankAccount))")
    BankAccountResponse toResponse(BankAccount bankAccount);

    static UUID getUserIdFromBankAccount(BankAccount bankAccount) {
        return bankAccount.getUser().getUserId();
    }

}
