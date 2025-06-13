package com.company.apis.Controllers.Employer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Enums.PayPal;
import com.company.apis.Enums.PaypalPaymentMethod;
import com.company.apis.Models.DTO.SubcriptionPlanDTO;
import com.company.apis.Services.PayPalService;
import com.company.apis.Services.SubcriptionPlanService;
import com.company.apis.Utils.HttpException;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;

@RestController
@RequestMapping("/employer/paypal")
public class PaymentController {

    BaseController<String> _baseController = new BaseController<String>();
    BaseController<List<SubcriptionPlanDTO>> _baseControllers = new BaseController<List<SubcriptionPlanDTO>>();
    @Autowired
    private PayPalService paypalService;
    @Autowired
    SubcriptionPlanService subcriptionPlanService;
    private static int id_sub;

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestParam("id") int id, @RequestParam("price") Double price) {
        try {
            // method get link paypal
            // get id sub for api under
            id_sub = id;
            // get data subcription plan
            String description = subcriptionPlanService.getById(id).getName();
            // set data for paypal
            Payment payment = paypalService.createPayment(
                    // price of subcription plan
                    price,
                    "USD",
                    PaypalPaymentMethod.PAYPAL,
                    PayPal.SALE,
                    description,
                    // link back when click cancel
                    "http://localhost:3000/employer/emprofile",
                    // link back when click pay
                    "http://localhost:3000/employer/emprofile");
            for (Links links : payment.getLinks())
                if (links.getRel().equals("approval_url"))
                    return _baseController.success(links.getHref());
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
        return null;
    }

    @GetMapping("/")
    public ResponseEntity<?> successPay(@RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId) {
        try {
            // deduction to paypal account
            Payment payment = paypalService.executePayment(paymentId, payerId);
            // create subcription plan for company
            subcriptionPlanService.createForEmployer(id_sub);
            if (payment.getState().equals("approved"))
                return _baseController.success("Success");
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
        return null;
    }
}
