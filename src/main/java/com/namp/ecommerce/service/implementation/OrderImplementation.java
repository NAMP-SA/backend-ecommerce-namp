package com.namp.ecommerce.service.implementation;

import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.namp.ecommerce.dto.OrderDTO;
import com.namp.ecommerce.dto.OrderDetailDTO;
import com.namp.ecommerce.dto.OrderWithDoDTO;
import com.namp.ecommerce.mapper.MapperOrder;
import com.namp.ecommerce.model.DiscountCoupon;
import com.namp.ecommerce.model.Order;
import com.namp.ecommerce.model.Product;
import com.namp.ecommerce.repository.IDiscountCouponDAO;
import com.namp.ecommerce.repository.IOrderDAO;
import com.namp.ecommerce.repository.IProductDAO;
import com.namp.ecommerce.repository.IStateDAO;
import com.namp.ecommerce.service.IOrderDetailService;
import com.namp.ecommerce.service.IOrderService;
import com.namp.ecommerce.service.IStateService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderImplementation implements IOrderService {

    @Autowired
    private IOrderDAO orderDAO;

    @Autowired
    private MapperOrder mapperOrder;

    @Autowired
    private IStateDAO stateDAO;

    @Autowired
    private IDiscountCouponDAO discountCouponDAO;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private IProductDAO productDAO;

    @Autowired
    private IStateService stateService;

    @Override
    public List<OrderDTO> getOrders() {
        return orderDAO.findAll()
                .stream()
                .map(mapperOrder::convertOrderToDTO)
                .collect(Collectors.toList());
    }

    // Listar los detalles del Pedido con getOrdersWhitDetailsOrders() y con ID

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        orderDTO.setDateTime(new Timestamp(System.currentTimeMillis()));
        orderDTO.setIdState(stateService.findbyid(1));
        // Convertir el DTO a la entidad Order
        Order order = mapperOrder.convertDtoToOrder(orderDTO);
        // Guardar la entidad en la base de datos
        Order savedOrder = orderDAO.save(order);
        // Convertir la entidad guardada de nuevo a DTO
        return mapperOrder.convertOrderToDTO(savedOrder);

    }

    @Override
    public OrderDTO update(OrderDTO existingOrderDTO, Order order) {
        // Buscar la orden existente
        Order existingOrder = orderDAO.findByIdOrder(existingOrderDTO.getIdOrder());
        if (existingOrder == null) {
            return null;
        }
        // Actualizar los campos de la entidad existente
        if (order.getDateTime() != null) {
            existingOrder.setDateTime(order.getDateTime());
        }
        if (order.getIdState() != null) {
            existingOrder.setIdState(order.getIdState());
        }

        existingOrder.setIdUser(order.getIdUser());
        // Guardar la orden actualizada
        Order updatedOrder = orderDAO.save(existingOrder);
        // Devolver el DTO de la orden actualizada
        return mapperOrder.convertOrderToDTO(updatedOrder);
    }

    @Override
    public void delete(OrderDTO orderDTO) {
        Order order = orderDAO.findByIdOrder(orderDTO.getIdOrder());
        if (order == null) {
            throw new EntityNotFoundException("Order not found with ID: " + orderDTO.getIdOrder());
        }
        orderDAO.delete(order);
    }

    @Override
    public OrderDTO findById(long id) {

        Order order = orderDAO.findByIdOrder(id);

        if (order == null) {

            return null;

        }

        return mapperOrder.convertOrderToDTO(order);
    }

    @Override
    public OrderWithDoDTO getOrdersIdWithOrderDetails(long id) {
        Order order = orderDAO.findByIdOrder(id);
        if (order == null) {
            return null;
        }

        return mapperOrder.convertOrderWithOrderDetailToDto(order);
    }

    @Override
    public List<OrderWithDoDTO> getOrdersWithOrderDetails() {
        return orderDAO.findAll()
                .stream()
                .map(mapperOrder::convertOrderWithOrderDetailToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void decreaseStocks(OrderDTO orderDTO) {

        Order order = orderDAO.findByIdOrder(orderDTO.getIdOrder());
        OrderWithDoDTO orderWithDoDTO = mapperOrder.convertOrderWithOrderDetailToDto(order);
        for (OrderDetailDTO orderDetail : orderWithDoDTO.getOrderDetail()) {
            if (orderDetail.getIdProduct() != null) {
                orderDetailService.decreaseStockProduct(orderDetail, orderDetail.getIdProduct());
            }
            if (orderDetail.getIdCombo() != null) {
                orderDetailService.decreaseStockCombo(orderDetail, orderDetail.getIdCombo());
            }

        }

    }

    @Override
    public boolean checkStocks(OrderDTO orderDTO) {
        // Esto esta medio raro, no se si puedo usar el DAO del producto aca. Si alguien
        // tiene otra solucion es bienvenida
        List<Product> products = productDAO.findAll();
        for (Product product : products) {
            product.setSimulatedStock(product.getStock());
        }

        Order order = orderDAO.findByIdOrder(orderDTO.getIdOrder());
        OrderWithDoDTO orderWithDoDTO = mapperOrder.convertOrderWithOrderDetailToDto(order);
        if (!orderWithDoDTO.getOrderDetail().isEmpty()) {
            for (OrderDetailDTO orderDetail : orderWithDoDTO.getOrderDetail()) {
                if (orderDetail.getIdProduct() != null) {
                    if (!orderDetailService.checkStockProduct(orderDetail, orderDetail.getIdProduct())) {
                        return false;
                    }
                    ;
                }
                if (orderDetail.getIdCombo() != null) {
                    if (!orderDetailService.checkStockCombo(orderDetail, orderDetail.getIdCombo())) {
                        return false;
                    }
                    ;
                }

            }

            return true;
        }

        return false;

    }

    // METODO PROVISIORIO

    @Override
    public OrderDTO confirmOrder(OrderDTO orderDTO) {
        this.decreaseStocks(orderDTO);

        Order order = orderDAO.findByIdOrder(orderDTO.getIdOrder());
        order.setIdState(stateDAO.findByIdState(2));
        Order savedOrder = orderDAO.save(order);

        // Convertir la entidad guardada de nuevo a DTO
        return mapperOrder.convertOrderToDTO(savedOrder);

    }

    @Override
    public OrderDTO addCoupon(Long idOrder, String couponCode) {
        Order order = orderDAO.findByIdOrder(idOrder);

        DiscountCoupon discountCoupon = discountCouponDAO.findByCodigo(couponCode);
        if (discountCoupon == null) {
            return null;
        } else if (discountCoupon.isVigente() == true) {
            order.setIdDiscountCoupon(discountCoupon);
            Order orderWithCoupon = orderDAO.save(order);
            return mapperOrder.convertOrderToDTO(orderWithCoupon);
        }
        return null;

    }

}
