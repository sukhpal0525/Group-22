package org.aston.ecommerce.user.admin;


import lombok.extern.slf4j.Slf4j;
import org.aston.ecommerce.order.OrderRepository;
import org.aston.ecommerce.order.Order;
import org.aston.ecommerce.order.OrderService;
import org.aston.ecommerce.order.Status;
import org.aston.ecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Controller
public class AdminOrders {

    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/admin/all-orders")
    public String returnAllOrders(Model model) {
//      List<Order> allOrders = this.orderRepository.findAll();
//      log.debug("Number of orders: {}", allOrders.size());

        model.addAttribute("unprocessedOrders", this.orderService.findOrdersByStatusFilter(Status.UNPROCESSED));
        model.addAttribute("processedOrders", this.orderService.findOrdersByStatusFilter(Status.PROCESSED));
        model.addAttribute("shippedOrders", this.orderService.findOrdersByStatusFilter(Status.SHIPPED));
        model.addAttribute("successOrders", this.orderService.findOrdersByStatusFilter(Status.SUCCESS));
        model.addAttribute("failedOrders", this.orderService.findOrdersByStatusFilter(Status.FAILED));
        return "admin_orders";
    }

    @GetMapping("/admin/edit_order/{id}")
    public String adminEditOrder(Model model, @PathVariable("id") String id) {

        Long longId = null;
        //ToDo: do this protection for the other routes where it is necessary to do so
        try{
            longId = Long.parseLong(id);
        }catch(Exception e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "page not found"
            );
        }

        Optional<Order> optOrder = this.orderRepository.findById(longId);
        if(!optOrder.isPresent()) return "redirect:/admin/all-orders";
        Order order = optOrder.get();
        model.addAttribute("editOrder", order);
        return "admin_order_edit";
    }

    @PostMapping("/admin/edit_order")
    public String handleAdminEditOrder(@RequestParam("updateStatus") String updateStatus,
                                                       @RequestParam("editOrderId") String editOrderId,
                                                       RedirectAttributes redirectAttrs){

        Optional<Order> optOrder = this.orderRepository.findById(Long.parseLong(editOrderId));
        if(!optOrder.isPresent()) {
            redirectAttrs.addFlashAttribute("fail_msg", "Error! Failed to update order.");
            return "redirect:/admin/edit_order/" + editOrderId;
        }
        Order updateOrder = optOrder.get();

        Status status = Status.parseStatusStr(updateStatus.toUpperCase());
        updateOrder.setStatus(status);

        try{
            this.orderRepository.save(updateOrder);
        }catch(Exception e){
            redirectAttrs.addFlashAttribute("fail_msg", "Error! Failed to update order.");
            return "redirect:/admin/edit_order/" + editOrderId;
        }

        return "redirect:/admin/all-orders";
    }

    @GetMapping("/admin/view_order/{id}")
    public String adminViewOrder(Model model, @PathVariable("id") String id){

        Long longId = null;
        //ToDo: do this protection for the other routes where it is necessary to do so
        try{
            longId = Long.parseLong(id);
        }catch(Exception e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "page not found"
            );
        }

        Optional<Order> optOrder = this.orderRepository.findById(longId);
        if(!optOrder.isPresent()) return "redirect:/admin/all-orders";
        Order order = optOrder.get();
        model.addAttribute("viewOrder", order);
        return "admin_view_order";
    }

    @PostMapping("/admin/status_dropdown")
    public String changeOrderStatus(@RequestParam("orderStatus") String orderStatus,
                                    @RequestParam("orderId") String orderId,
                                    RedirectAttributes redirectAttrs){
        Optional<Order> optOrder = this.orderRepository.findById(Long.parseLong(orderId));
        Order updateOrder = optOrder.get();

        Status status = Status.parseStatusStr(orderStatus.toUpperCase());
        updateOrder.setStatus(status);

        try{
            this.orderRepository.save(updateOrder);
        }catch(Exception e){
            return "redirect:/admin/all-orders";
        }

        return "redirect:/admin/all-orders";
    }

}
