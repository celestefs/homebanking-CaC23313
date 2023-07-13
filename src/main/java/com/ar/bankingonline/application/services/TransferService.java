package com.ar.bankingonline.application.services;

import com.ar.bankingonline.api.dtos.TransferDto;
import com.ar.bankingonline.api.mappers.TransferMapper;
import com.ar.bankingonline.application.exceptions.InsufficientFundsException;
import com.ar.bankingonline.domain.exceptions.AccountNotFoundException;
import com.ar.bankingonline.domain.exceptions.TransferNotFoundException;
import com.ar.bankingonline.domain.models.Account;
import com.ar.bankingonline.domain.models.Transfer;
import com.ar.bankingonline.infrastructure.repositories.AccountRepository;
import com.ar.bankingonline.infrastructure.repositories.TransfersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {

    @Autowired
    private TransfersRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    public TransferService(TransfersRepository repository) {
        this.repository = repository;
    }

    public List<TransferDto> getTransfers(){
        List<Transfer> transfers = repository.findAll();
        return transfers.stream()
                .map(TransferMapper::transferToDto)
                .collect(Collectors.toList());
    }

    public TransferDto getTransferById(Long id) {
        Transfer transfer = repository.findById(id).orElseThrow(() ->
                new TransferNotFoundException("Transfer not found with id: " + id));
        return TransferMapper.transferToDto(transfer);
    }

    public TransferDto updateTransfer(Long id, TransferDto transferDto) {
        Transfer transfer = repository.findById(id).orElseThrow(() -> new TransferNotFoundException("Transfer not found with id: " + id));
        Transfer updatedTransfer = TransferMapper.dtoToTransfer(transferDto);
        updatedTransfer.setId(transfer.getId());
        return TransferMapper.transferToDto(repository.save(updatedTransfer));
    }

    public String deleteTransfer(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "Se ha eliminado la transferencia";
        } else {
            return "No se ha eliminado la transferencia";
        }
    }

    @Transactional
    public TransferDto performTransfer(TransferDto dto) {
        Account originAccount = accountRepository.findById(dto.getOrigin())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + dto.getOrigin()));
        Account destinationAccount = accountRepository.findById(dto.getTarget())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + dto.getTarget()));

        if (originAccount.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds in the account with id: " + dto.getOrigin());
        }

        originAccount.setBalance(originAccount.getBalance().subtract(dto.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(dto.getAmount()));

        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        Transfer transfer = new Transfer();
        Date date = new Date();
        transfer.setDate(date);
        transfer.setOrigin(originAccount.getId());
        transfer.setTarget(destinationAccount.getId());
        transfer.setAmount(dto.getAmount());
        transfer = repository.save(transfer);

        return TransferMapper.transferToDto(transfer);
    }

}
