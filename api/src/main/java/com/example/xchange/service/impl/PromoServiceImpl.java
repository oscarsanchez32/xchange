package com.example.xchange.service.impl;

import com.example.xchange.dto.DiscountDTO;
import com.example.xchange.entity.Discount;
import com.example.xchange.exception.InvalidInputException;
import com.example.xchange.exception.PaymentException;
import com.example.xchange.repository.PromoRepository;
import com.example.xchange.service.PromoService;
import com.stripe.Stripe;
import com.stripe.model.Coupon;
import com.stripe.model.PromotionCode;
import com.stripe.model.PromotionCodeCollection;
import com.stripe.param.CouponCreateParams;
import com.stripe.param.PromotionCodeCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromoServiceImpl implements PromoService {

    private final PromoRepository promoRepository;

    @Value("${stripe.key}")
    private String stripeKey;

    @Override
    public Discount getDiscount(String code) {
        Optional<Discount> discountOptional = promoRepository.findByCode(code);
        discountOptional.orElseThrow(()->{throw new InvalidInputException("Promo code does not exist.");});
        return discountOptional.get();
    }

    @Override
    public Discount createDiscount(DiscountDTO discountDTO) {
        Stripe.apiKey = stripeKey;
        Discount dsc = new Discount();
        dsc.setId(0);
        dsc.setAmount(discountDTO.getAmount());
        dsc.setCode(discountDTO.getCode());
        Discount createdDiscount;

        try{
            CouponCreateParams params = CouponCreateParams.builder()
                    .setName(discountDTO.getCode())
                    .setAmountOff(discountDTO.getAmount().longValue()*100)
                    .setCurrency("€")
                    .build();

            Coupon coupon = Coupon.create(params);

            PromotionCodeCreateParams promoParams = PromotionCodeCreateParams.builder()
                    .setCode(discountDTO.getCode())
                    .setCoupon(coupon.getId())
                    .setActive(true)
                    .build();

            PromotionCode promotionCode = PromotionCode.create(promoParams);
            createdDiscount = promoRepository.save(dsc);
        } catch (Exception e){
            throw new PaymentException("Error creando el código de promoción. "+e);
        }

        return createdDiscount;
    }

    @Override
    public void deleteDiscount(String code) {
        Stripe.apiKey = stripeKey;
        Discount discount = getDiscount(code);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code", code);
            PromotionCodeCollection collection = PromotionCode.list(params);
            collection.getData().get(0).getCoupon().delete();
            promoRepository.delete(discount);
        } catch (Exception e){
            throw new PaymentException("Error deleting promo. "+e);
        }
    }
}
