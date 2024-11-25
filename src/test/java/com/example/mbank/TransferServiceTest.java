package com.example.mbank;

import com.example.mbank.entity.Account;
import com.example.mbank.entity.User;
import com.example.mbank.exception.InsufficientBalanceException;
import com.example.mbank.repository.UserRepository;
import com.example.mbank.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransferService transferService;

    private User senderUser;
    private User recipientUser;

    @BeforeEach
    public void setUp() {
        senderUser = new User();
        senderUser.setName("sender");
        Account senderAccount = new Account();
        senderAccount.setBalance(BigDecimal.valueOf(100));
        senderUser.setAccount(senderAccount);

        recipientUser = new User();
        recipientUser.setId(1L);
        recipientUser.setName("recipient");
        Account recipientAccount = new Account();
        recipientAccount.setBalance(BigDecimal.valueOf(50));
        recipientUser.setAccount(recipientAccount);
    }

    @Test
    public void testTransferMoney_Success() {
        when(userRepository.findByName("sender")).thenReturn(Optional.of(senderUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(recipientUser));

        transferService.transferMoney("sender", 1L, BigDecimal.valueOf(30));

        assertEquals(BigDecimal.valueOf(70), senderUser.getAccount().getBalance());
        assertEquals(BigDecimal.valueOf(80), recipientUser.getAccount().getBalance());
    }

    @Test
    public void testTransferMoney_ToSelf_ShouldThrowException() {
        when(userRepository.findByName("sender")).thenReturn(Optional.of(senderUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(senderUser));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transferService.transferMoney("sender", 1L, BigDecimal.valueOf(30));
        });

        assertEquals("Cannot transfer money to yourself", exception.getMessage());
    }

    @Test
    public void testTransferMoney_InsufficientBalance_ShouldThrowException() {
        when(userRepository.findByName("sender")).thenReturn(Optional.of(senderUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(recipientUser));

        Exception exception = assertThrows(InsufficientBalanceException.class, () -> {
            transferService.transferMoney("sender", 1L, BigDecimal.valueOf(150));
        });

        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    public void testTransferMoney_SenderNotFound_ShouldThrowException() {
        when(userRepository.findByName("sender")).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            transferService.transferMoney("sender", 1L, BigDecimal.valueOf(30));
        });

        assertEquals("Sender user for transferMoney not found", exception.getMessage());
    }

    @Test
    public void testTransferMoney_RecipientNotFound_ShouldThrowException() {
        when(userRepository.findByName("sender")).thenReturn(Optional.of(senderUser));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            transferService.transferMoney("sender", 1L, BigDecimal.valueOf(30));
        });

        assertEquals("Recipient user for transferMoney not found", exception.getMessage());
    }
}

