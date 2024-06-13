package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Address;
import org.example.entity.Receipt;
import org.example.entity.ShoppingCart;
import org.example.repository.ReceiptRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingService {
    private final RabbitTemplate rabbitTemplate;
    private final ReceiptRepository receiptRepository;


    @Transactional
    public Receipt checkout(String userId, String addressId) {
        Address address = (Address) rabbitTemplate.convertSendAndReceive("exchange.direct", "getAddress.Route", addressId);
        ShoppingCart cart = getCartById(userId);
        if (cart != null) {
            Receipt receipt = new Receipt();
            receipt.setAddress(address);
            receipt.setUserId(userId);
            receipt.setItems(cart.getItems());
            receipt.setTotalPrice(cart.getTotalPrice());
            Double balance = getBalance(userId);
            if(!(balance > cart.getTotalPrice())) {
                throw new RuntimeException("Yetersiz bakiye");
            }
            rabbitTemplate.convertAndSend("exchange.direct", "updateBalance.Route", userId+"*"+cart.getTotalPrice());
            receiptRepository.save(receipt);
            // sepeti sil
            rabbitTemplate.convertAndSend("exchange.direct", "deleteCart.Route", cart);
            return receipt;
        }
        return null;
    }


    @Transactional
    public ShoppingCart getCartById(String profileId) {
        return (ShoppingCart) rabbitTemplate.convertSendAndReceive("exchange.direct", "getCart.Route", profileId);

    }

    @Transactional
    public Double getBalance(String profileId) {
        return (Double) rabbitTemplate.convertSendAndReceive("exchange.direct", "getBalance.Route", profileId);
    }
}
