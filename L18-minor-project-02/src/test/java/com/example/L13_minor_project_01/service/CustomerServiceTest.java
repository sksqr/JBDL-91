package com.example.L13_minor_project_01.service;

import com.example.L13_minor_project_01.dto.CreateOrderItemRequestDto;
import com.example.L13_minor_project_01.dto.OrderDetailsResponseDto;
import com.example.L13_minor_project_01.dto.OrderItemResponseDto;
import com.example.L13_minor_project_01.dto.ProductResponseDto;
import com.example.L13_minor_project_01.dto.ProductSearchRequestDto;
import com.example.L13_minor_project_01.entity.Category;
import com.example.L13_minor_project_01.entity.Company;
import com.example.L13_minor_project_01.entity.Order;
import com.example.L13_minor_project_01.entity.OrderItem;
import com.example.L13_minor_project_01.entity.OrderStatus;
import com.example.L13_minor_project_01.entity.Product;
import com.example.L13_minor_project_01.entity.User;
import com.example.L13_minor_project_01.entity.UserRole;
import com.example.L13_minor_project_01.exception.BadRequestException;
import com.example.L13_minor_project_01.exception.ForbiddenOperationException;
import com.example.L13_minor_project_01.exception.ResourceNotFoundException;
import com.example.L13_minor_project_01.repo.OrderItemRepository;
import com.example.L13_minor_project_01.repo.OrderRepository;
import com.example.L13_minor_project_01.repo.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private RedisTemplate<String, ProductResponseDto> redisTemplate;

    @Mock
    private ValueOperations<String, ProductResponseDto> valueOperations;

    @InjectMocks
    private CustomerService customerService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getProductsByKeywordThrowsWhenRequestIsNull() {
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> customerService.getProductsByKeyword(null)
        );

        assertEquals("Request is required", exception.getMessage());
    }

    @Test
    void getProductsByKeywordReturnsMappedProducts() {
        ProductSearchRequestDto requestDto = new ProductSearchRequestDto();
        requestDto.setKeyword("  phone  ");

        Product product = createProduct(10L, "Phone", true, 8, new BigDecimal("799.99"));
        when(productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase("phone", "phone"))
                .thenReturn(List.of(product));

        List<ProductResponseDto> response = customerService.getProductsByKeyword(requestDto);

        assertEquals(1, response.size());
        assertEquals(product.getId(), response.getFirst().getId());
        assertEquals(product.getName(), response.getFirst().getName());
        assertEquals(product.getCompany().getId(), response.getFirst().getCompanyId());
        assertEquals(product.getCategory().getId(), response.getFirst().getCategoryId());
    }

    @Test
    void getProductByIdThrowsWhenIdIsNull() {
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> customerService.getProductById(null)
        );

        assertEquals("Id is required", exception.getMessage());
    }

    @Test
    void getProductByIdReturnsCachedValueWhenPresent() {
        ProductResponseDto cached = ProductResponseDto.builder().id(55L).name("Cached Product").build();
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("product:55")).thenReturn(cached);

        ProductResponseDto response = customerService.getProductById(55L);

        assertSame(cached, response);
        verify(productRepository, never()).findById(any());
    }

    @Test
    void getProductByIdLoadsFromRepositoryAndCachesWhenMissingInRedis() {
        Product product = createProduct(7L, "Laptop", true, 4, new BigDecimal("1200.00"));
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("product:7")).thenReturn(null);
        when(productRepository.findById(7L)).thenReturn(Optional.of(product));

        ProductResponseDto response = customerService.getProductById(7L);

        assertEquals(7L, response.getId());
        assertEquals("Laptop", response.getName());
        verify(valueOperations).set(eq("product:7"), any(ProductResponseDto.class));
    }

    @Test
    void getProductByIdThrowsWhenRepositoryMisses() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("product:99")).thenReturn(null);
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.getProductById(99L)
        );

        assertEquals("Product not found with id: 99", exception.getMessage());
    }

    @Test
    void createOrAddOrderItemThrowsWhenRequestIsNull() {
        setAuthenticatedUser(createUser(1L, "Customer"));

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> customerService.createOrAddOrderItem(null)
        );

        assertEquals("Request body is required", exception.getMessage());
    }

    @Test
    void createOrAddOrderItemCreatesNewDraftOrderAndItem() {
        User user = createUser(1L, "Customer");
        Product product = createProduct(11L, "Monitor", true, 10, new BigDecimal("250.00"));
        CreateOrderItemRequestDto requestDto = createOrderItemRequest(product.getId(), 2);

        setAuthenticatedUser(user);
        when(productRepository.findById(11L)).thenReturn(Optional.of(product));
        when(orderRepository.findFirstByUserIdAndStatusOrderByUpdatedAtDesc(1L, OrderStatus.DRAFT)).thenReturn(Optional.empty());
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    if (order.getId() == null) {
                        order.setId(101L);
                    }
                    return order;
                });
        when(orderItemRepository.findByOrderIdAndProductId(101L, 11L)).thenReturn(Optional.empty());
        when(orderItemRepository.save(any(OrderItem.class)))
                .thenAnswer(invocation -> {
                    OrderItem orderItem = invocation.getArgument(0);
                    orderItem.setId(501L);
                    return orderItem;
                });

        OrderItemResponseDto response = customerService.createOrAddOrderItem(requestDto);

        assertEquals(101L, response.getOrderId());
        assertEquals(501L, response.getOrderItemId());
        assertEquals(1L, response.getUserId());
        assertEquals(11L, response.getProductId());
        assertEquals(2, response.getQuantity());
        assertEquals(new BigDecimal("500.00"), response.getOrderTotalAmount());
        assertTrue(response.getNewOrderCreated());
    }

    @Test
    void createOrAddOrderItemUpdatesExistingDraftOrderItem() {
        User user = createUser(2L, "Customer");
        Product product = createProduct(12L, "Keyboard", true, 10, new BigDecimal("50.00"));
        Order draftOrder = createOrder(201L, user, OrderStatus.DRAFT, new BigDecimal("100.00"));
        OrderItem existingOrderItem = new OrderItem();
        existingOrderItem.setId(601L);
        existingOrderItem.setOrder(draftOrder);
        existingOrderItem.setProduct(product);
        existingOrderItem.setQuantity(2);
        existingOrderItem.setPrice(new BigDecimal("50.00"));
        CreateOrderItemRequestDto requestDto = createOrderItemRequest(product.getId(), 3);

        setAuthenticatedUser(user);
        when(productRepository.findById(12L)).thenReturn(Optional.of(product));
        when(orderRepository.findFirstByUserIdAndStatusOrderByUpdatedAtDesc(2L, OrderStatus.DRAFT)).thenReturn(Optional.of(draftOrder));
        when(orderItemRepository.findByOrderIdAndProductId(201L, 12L)).thenReturn(Optional.of(existingOrderItem));
        when(orderItemRepository.save(existingOrderItem)).thenReturn(existingOrderItem);
        when(orderRepository.save(draftOrder)).thenReturn(draftOrder);

        OrderItemResponseDto response = customerService.createOrAddOrderItem(requestDto);

        assertFalse(response.getNewOrderCreated());
        assertEquals(5, response.getQuantity());
        assertEquals(new BigDecimal("250.00"), response.getOrderTotalAmount());
        assertEquals(new BigDecimal("50.00"), response.getPrice());
    }

    @Test
    void createOrAddOrderItemThrowsWhenProductIsMissing() {
        User user = createUser(3L, "Customer");
        CreateOrderItemRequestDto requestDto = createOrderItemRequest(88L, 1);
        setAuthenticatedUser(user);
        when(productRepository.findById(88L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.createOrAddOrderItem(requestDto)
        );

        assertEquals("Product not found with id: 88", exception.getMessage());
    }

    @Test
    void createOrAddOrderItemThrowsWhenProductInactive() {
        User user = createUser(4L, "Customer");
        Product product = createProduct(13L, "Mouse", false, 5, new BigDecimal("20.00"));
        CreateOrderItemRequestDto requestDto = createOrderItemRequest(13L, 1);
        setAuthenticatedUser(user);
        when(productRepository.findById(13L)).thenReturn(Optional.of(product));

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> customerService.createOrAddOrderItem(requestDto)
        );

        assertEquals("Product is inactive", exception.getMessage());
    }

    @Test
    void createOrAddOrderItemThrowsWhenRequestedQuantityExceedsStock() {
        User user = createUser(5L, "Customer");
        Product product = createProduct(14L, "Tablet", true, 4, new BigDecimal("300.00"));
        Order draftOrder = createOrder(301L, user, OrderStatus.DRAFT, BigDecimal.ZERO);
        OrderItem existingOrderItem = new OrderItem();
        existingOrderItem.setId(701L);
        existingOrderItem.setOrder(draftOrder);
        existingOrderItem.setProduct(product);
        existingOrderItem.setQuantity(3);
        existingOrderItem.setPrice(new BigDecimal("300.00"));
        CreateOrderItemRequestDto requestDto = createOrderItemRequest(14L, 2);

        setAuthenticatedUser(user);
        when(productRepository.findById(14L)).thenReturn(Optional.of(product));
        when(orderRepository.findFirstByUserIdAndStatusOrderByUpdatedAtDesc(5L, OrderStatus.DRAFT)).thenReturn(Optional.of(draftOrder));
        when(orderItemRepository.findByOrderIdAndProductId(301L, 14L)).thenReturn(Optional.of(existingOrderItem));

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> customerService.createOrAddOrderItem(requestDto)
        );

        assertEquals("Requested quantity exceeds available stock", exception.getMessage());
    }

    @Test
    void getOrderDetailsByIdReturnsMappedOrder() {
        User user = createUser(6L, "Alice");
        Product product = createProduct(15L, "Camera", true, 3, new BigDecimal("900.00"));
        Order order = createOrderWithItem(401L, user, product, 2, new BigDecimal("1800.00"), OrderStatus.DRAFT);

        setAuthenticatedUser(user);
        when(orderRepository.findWithDetailsByIdAndUser(401L, user)).thenReturn(Optional.of(order));

        OrderDetailsResponseDto response = customerService.getOrderDetailsById(401L);

        assertEquals(401L, response.getOrderId());
        assertEquals(6L, response.getUserId());
        assertEquals("Alice", response.getUserName());
        assertEquals(OrderStatus.DRAFT, response.getStatus());
        assertEquals(1, response.getItems().size());
        assertEquals(new BigDecimal("1800.00"), response.getItems().getFirst().getLineTotal());
    }

    @Test
    void getOrderDetailsByIdThrowsWhenOrderDoesNotBelongToUser() {
        User user = createUser(7L, "Bob");
        setAuthenticatedUser(user);
        when(orderRepository.findWithDetailsByIdAndUser(999L, user)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.getOrderDetailsById(999L)
        );

        assertEquals("Order not found with id: 999", exception.getMessage());
    }

    @Test
    void submitOrderMarksDraftAsPlaced() {
        User user = createUser(8L, "Charlie");
        Product product = createProduct(16L, "Speaker", true, 7, new BigDecimal("150.00"));
        Order order = createOrderWithItem(501L, user, product, 2, new BigDecimal("300.00"), OrderStatus.DRAFT);

        setAuthenticatedUser(user);
        when(orderRepository.findWithDetailsById(501L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        OrderDetailsResponseDto response = customerService.submitOrder(501L);

        assertEquals(OrderStatus.PLACED, response.getStatus());
        assertEquals(OrderStatus.PLACED, order.getStatus());
    }

    @Test
    void submitOrderThrowsWhenOrderIsMissing() {
        when(orderRepository.findWithDetailsById(777L)).thenReturn(Optional.empty());
        setAuthenticatedUser(createUser(9L, "Dana"));

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.submitOrder(777L)
        );

        assertEquals("Order not found with id: 777", exception.getMessage());
    }

    @Test
    void submitOrderThrowsWhenAuthenticatedUserDoesNotOwnOrder() {
        User owner = createUser(10L, "Owner");
        User anotherUser = createUser(11L, "Other");
        Order order = createOrder(601L, owner, OrderStatus.DRAFT, BigDecimal.TEN);

        setAuthenticatedUser(anotherUser);
        when(orderRepository.findWithDetailsById(601L)).thenReturn(Optional.of(order));

        ForbiddenOperationException exception = assertThrows(
                ForbiddenOperationException.class,
                () -> customerService.submitOrder(601L)
        );

        assertEquals("User mismatch", exception.getMessage());
    }

    @Test
    void submitOrderThrowsWhenStatusIsNotDraft() {
        User user = createUser(12L, "Eva");
        Order order = createOrder(701L, user, OrderStatus.PLACED, BigDecimal.ONE);

        setAuthenticatedUser(user);
        when(orderRepository.findWithDetailsById(701L)).thenReturn(Optional.of(order));

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> customerService.submitOrder(701L)
        );

        assertEquals("Only DRAFT orders can be submitted", exception.getMessage());
    }

    @Test
    void createOrAddOrderItemPersistsNewOrderWithExpectedDefaults() {
        User user = createUser(13L, "Frank");
        Product product = createProduct(17L, "Headphones", true, 6, new BigDecimal("99.99"));
        CreateOrderItemRequestDto requestDto = createOrderItemRequest(17L, 1);

        setAuthenticatedUser(user);
        when(productRepository.findById(17L)).thenReturn(Optional.of(product));
        when(orderRepository.findFirstByUserIdAndStatusOrderByUpdatedAtDesc(13L, OrderStatus.DRAFT)).thenReturn(Optional.empty());
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    if (order.getId() == null) {
                        order.setId(801L);
                    }
                    return order;
                });
        when(orderItemRepository.findByOrderIdAndProductId(801L, 17L)).thenReturn(Optional.empty());
        when(orderItemRepository.save(any(OrderItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        customerService.createOrAddOrderItem(requestDto);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(2)).save(orderCaptor.capture());
        Order savedOrder = orderCaptor.getAllValues().getFirst();
        assertEquals(OrderStatus.DRAFT, savedOrder.getStatus());
        assertSame(user, savedOrder.getUser());
    }

    private void setAuthenticatedUser(User user) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
        );
    }

    private CreateOrderItemRequestDto createOrderItemRequest(Long productId, int quantity) {
        CreateOrderItemRequestDto requestDto = new CreateOrderItemRequestDto();
        requestDto.setProductId(productId);
        requestDto.setQuantity(quantity);
        return requestDto;
    }

    private User createUser(Long id, String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(name.toLowerCase() + "@example.com");
        user.setPassword("secret");
        user.setRole(UserRole.CUSTOMER);
        return user;
    }

    private Product createProduct(Long id, String name, boolean isActive, int stock, BigDecimal price) {
        Company company = new Company();
        company.setId(91L);
        company.setName("Acme");
        company.setNumber("GSTIN123");

        Category category = new Category();
        category.setId(41L);
        category.setName("Electronics");

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(name + " description");
        product.setImageUrl("/images/" + id + ".png");
        product.setPrice(price);
        product.setStock(stock);
        product.setIsActive(isActive);
        product.setCompany(company);
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.of(2024, 1, 1, 10, 0));
        product.setUpdatedAt(LocalDateTime.of(2024, 1, 2, 10, 0));
        return product;
    }

    private Order createOrder(Long id, User user, OrderStatus status, BigDecimal totalAmount) {
        Order order = new Order();
        order.setId(id);
        order.setUser(user);
        order.setStatus(status);
        order.setTotalAmount(totalAmount);
        order.setCreatedAt(LocalDateTime.of(2024, 2, 1, 9, 0));
        order.setUpdatedAt(LocalDateTime.of(2024, 2, 1, 9, 30));
        return order;
    }

    private Order createOrderWithItem(Long orderId, User user, Product product, int quantity, BigDecimal totalAmount, OrderStatus status) {
        Order order = createOrder(orderId, user, status, totalAmount);
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderId + 1000);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(product.getPrice());
        order.setOrderItems(List.of(orderItem));
        assertNotNull(order.getOrderItems());
        return order;
    }
}
