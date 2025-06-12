package com.namp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.namp.ecommerce.dto.OrderDTO;
import com.namp.ecommerce.model.DiscountCoupon;
import com.namp.ecommerce.model.Order;
import com.namp.ecommerce.service.IOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api-namp")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/admin/order")
    public ResponseEntity<?> getOrders() {
        try {
            return ResponseEntity.ok(orderService.getOrders());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the orders:" + e.getMessage());
        }
    }

    @GetMapping("/admin/orderWithOrderDetails")
    public ResponseEntity<?> getOrdersWithOrderDetails() {
        try {
            return ResponseEntity.ok(orderService.getOrdersWithOrderDetails());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

                    .body("Error showing the orders " + e.getMessage());
        }
    }

    @GetMapping("/user/orderWithOrderDetails/{id}")
    public ResponseEntity<?> getOrderIdWithOrderDetails(@PathVariable long id) {
        try {
            if (orderService.getOrdersIdWithOrderDetails(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Order with ID " + id + " not found");
            }
            return ResponseEntity.ok(orderService.getOrdersIdWithOrderDetails(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the order " + e.getMessage());
        }
    }

    @PostMapping("/user/order")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO createdOrderDTO = orderService.save(orderDTO);
            if (createdOrderDTO == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This order already exists.");
            }
            return ResponseEntity.ok(createdOrderDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the order:" + e.getMessage());
        }
    }

    @DeleteMapping("/user/order/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable long id) {
        try {
            OrderDTO orderDTO = orderService.findById(id);

            if (orderDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The order does not exist");
            }

            orderService.delete(orderDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the order:" + e.getMessage());
        }
    }

    @PutMapping("/user/order/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable long id, @Valid @RequestBody Order order) {
        try {
            OrderDTO existingOrderDTO = orderService.findById(id);

            if (existingOrderDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The order does not exists ");
            }
            OrderDTO updatedOrderDTO = orderService.update(existingOrderDTO, order);

            if (updatedOrderDTO == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("The entered order already exists");
            }
            return ResponseEntity.ok(updatedOrderDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating the order:" + e.getMessage());
        }
    }

    @PostMapping("/user/order/addCoupon/{id}")
    public ResponseEntity<?> addCoupon(@PathVariable long id, @RequestBody DiscountCoupon discountCoupon) {
        try {

            OrderDTO orderDTO = orderService.findById(id);

            if (orderDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The order does not exists ");
            }

            OrderDTO orderDTOWithCoupon = orderService.addCoupon(id, discountCoupon.getCode());
            if (orderDTOWithCoupon == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("The coupon does not exists or is not valid");
            }

            return ResponseEntity.ok(orderDTOWithCoupon);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding the coupon:" + e.getMessage());
        }
    }

    @PostMapping("/user/order/deleteCoupon/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable long id) {
        try {
            OrderDTO orderDTO = orderService.findById(id);

            if (orderDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The order does not exists ");
            }

            OrderDTO orderDTOWithoutCoupon = orderService.deleteCoupon(id);
            if (orderDTOWithoutCoupon == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Error deleting the coupon from the order");
            }
            return ResponseEntity.ok(orderDTOWithoutCoupon);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the coupon:" + e.getMessage());
        }
    }

    @PostMapping("/user/confirmOrder/{id}")
    public ResponseEntity<?> confirmOrder(@PathVariable long id) {
        OrderDTO orderDTO = orderService.findById(id);
        if (orderService.checkStocks(orderDTO)) {
            return ResponseEntity.ok(orderService.confirmOrder(orderDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The order could not be confirmed");
        }

    }
}
