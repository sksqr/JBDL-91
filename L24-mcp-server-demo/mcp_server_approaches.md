# Exposing E-Commerce Controllers as MCP Server

## Controllers Overview

| Controller | Base Path | Endpoints |
|---|---|---|
| **AdminController** | `/api/admin` | Create company, create seller, create category, list sellers (paginated), delete seller |
| **SellerController** | `/api/seller` | Create product, upload image, bulk CSV upload, update product, pending orders, accept order |
| **CustomerController** | `/api/customer` | Search products, add order item, get order details, submit order |

---

## Approach 1: Spring AI MCP Server (Recommended)

The most Spring-native way. Spring AI provides an MCP server starter that integrates directly into your Spring Boot app using **SSE (Server-Sent Events) transport** over HTTP.

### Step 1 — Add dependencies to `pom.xml`

```xml
<!-- Spring AI BOM -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-bom</artifactId>
            <version>2.0.0-M4</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

        <!-- MCP Server with WebMVC SSE transport -->
<dependency>
<groupId>org.springframework.ai</groupId>
<artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
</dependency>
```

### Step 2 — Configure in `application.properties`

```properties
spring.ai.mcp.server.name=ecommerce-mcp-server
spring.ai.mcp.server.version=1.0.0
spring.ai.mcp.server.type=SYNC
spring.ai.mcp.server.sse-message-endpoint=/mcp/messages
```

### Step 3 — Create Tool beans wrapping existing services

#### AdminMcpTools.java

```java
@Service
public class AdminMcpTools {

    private final AdminService adminService;

    public AdminMcpTools(AdminService adminService) {
        this.adminService = adminService;
    }

    @Tool(description = "Create a new company with its primary admin user")
    public CompanyResponseDto createCompany(
            @ToolParam(description = "Company name") String companyName,
            @ToolParam(description = "Primary user's name") String userName,
            @ToolParam(description = "Primary user's email") String userEmail,
            @ToolParam(description = "Primary user's password") String password) {
        var dto = new CreateCompanyRequestDto();
        // map fields to dto
        return adminService.createCompany(dto);
    }

    @Tool(description = "Create a seller user for an existing company")
    public SellerResponseDto createSeller(
            @ToolParam(description = "Seller name") String name,
            @ToolParam(description = "Seller email") String email,
            @ToolParam(description = "Company ID") Long companyId) {
        var dto = new CreateSellerRequestDto();
        // map fields
        return adminService.createSeller(dto);
    }

    @Tool(description = "Create a product category")
    public CategoryResponseDto createCategory(
            @ToolParam(description = "Category name") String name) {
        var dto = new CreateCategoryRequestDto();
        // map fields
        return adminService.createCategory(dto);
    }

    @Tool(description = "List all sellers with pagination")
    public Page<SellerResponseDto> getAllSellers(
            @ToolParam(description = "Page number (0-based)") int page,
            @ToolParam(description = "Page size") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return adminService.getAllSellers(pageable);
    }

    @Tool(description = "Delete a seller by ID")
    public String deleteSeller(
            @ToolParam(description = "Seller user ID") Long id) {
        adminService.deleteSeller(id);
        return "Seller " + id + " deleted successfully";
    }
}
```

#### CustomerMcpTools.java

```java
@Service
public class CustomerMcpTools {

    private final CustomerService customerService;

    public CustomerMcpTools(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Tool(description = "Search products by keyword in name or description")
    public List<ProductResponseDto> searchProducts(
            @ToolParam(description = "Search keyword") String keyword) {
        var dto = new ProductSearchRequestDto();
        // set keyword
        return customerService.getProductsByKeyword(dto);
    }

    @Tool(description = "Add an item to user's cart/draft order. Creates a new order if none exists.")
    public OrderItemResponseDto addOrderItem(
            @ToolParam(description = "User ID") Long userId,
            @ToolParam(description = "Product ID") Long productId,
            @ToolParam(description = "Quantity") int quantity) {
        var dto = new CreateOrderItemRequestDto();
        // map fields
        return customerService.createOrAddOrderItem(dto);
    }

    @Tool(description = "Get full order details including items and total")
    public OrderDetailsResponseDto getOrderDetails(
            @ToolParam(description = "Order ID") Long orderId) {
        return customerService.getOrderDetailsById(orderId);
    }

    @Tool(description = "Submit a draft order, changing status from DRAFT to PLACED")
    public OrderDetailsResponseDto submitOrder(
            @ToolParam(description = "Order ID") Long orderId) {
        return customerService.submitOrder(orderId);
    }
}
```

#### SellerMcpTools.java

```java
@Service
public class SellerMcpTools {

    private final SellerService sellerService;

    public SellerMcpTools(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Tool(description = "Create a new product for a company")
    public ProductResponseDto createProduct(
            @ToolParam(description = "Product name") String name,
            @ToolParam(description = "Product description") String description,
            @ToolParam(description = "Price") double price,
            @ToolParam(description = "Company ID") Long companyId,
            @ToolParam(description = "Category ID") Long categoryId) {
        var dto = new CreateProductRequestDto();
        // map fields
        return sellerService.createProduct(dto);
    }

    @Tool(description = "Get all pending (PLACED) orders for a company")
    public List<OrderDetailsResponseDto> getPendingOrders(
            @ToolParam(description = "Company ID") Long companyId) {
        return sellerService.getPendingOrdersByCompanyId(companyId);
    }

    @Tool(description = "Accept a placed order, changing status from PLACED to ACCEPTED")
    public OrderDetailsResponseDto acceptOrder(
            @ToolParam(description = "Order ID") Long orderId) {
        return sellerService.acceptOrder(orderId);
    }
}
```

### Step 4 — Register tools as `ToolCallbackProvider`

```java
@Configuration
public class McpToolConfig {

    @Bean
    public ToolCallbackProvider adminTools(AdminMcpTools adminMcpTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(adminMcpTools)
                .build();
    }

    @Bean
    public ToolCallbackProvider sellerTools(SellerMcpTools sellerMcpTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(sellerMcpTools)
                .build();
    }

    @Bean
    public ToolCallbackProvider customerTools(CustomerMcpTools customerMcpTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(customerMcpTools)
                .build();
    }
}
```

### Step 5 — Connect from an MCP client (e.g., Claude Code)

In your MCP client config (e.g., `.claude/settings.json`):

```json
{
  "mcpServers": {
    "ecommerce": {
      "type": "sse",
      "url": "http://localhost:8080/sse"
    }
  }
}
```

---

## Approach 2: OpenAPI-to-MCP Bridge (Quickest, Zero Code Change)

Since this project already has **SpringDoc OpenAPI** configured, the app auto-generates an OpenAPI spec at `/v3/api-docs`. An external bridge tool can convert every REST endpoint into an MCP tool with **no code changes**.

```bash
# Example with an OpenAPI-to-MCP bridge tool
npx @anthropic/openapi-mcp http://localhost:8080/v3/api-docs

# If using a corporate npm proxy, bypass it with:
npx --registry https://registry.npmjs.org @anthropic/openapi-mcp http://localhost:8080/v3/api-docs
```

**Trade-off**: Less control over tool descriptions and parameter naming.

---

## Approach 3: MCP Java SDK (Standalone)

If Spring AI compatibility with Spring Boot 4 is an issue, use the raw MCP Java SDK:

```xml
<dependency>
    <groupId>io.modelcontextprotocol</groupId>
    <artifactId>mcp-spring-webmvc</artifactId>
    <version>0.10.0</version>
</dependency>
```

This gives lower-level control but requires manual tool registration via `McpServer.sync()` / `McpServer.async()` builders.

---

## Comparison

| Approach | Effort | Control | Best For |
|---|---|---|---|
| **Spring AI MCP Server** | Medium | High | Production apps, fine-grained tool design |
| **OpenAPI Bridge** | Zero | Low | Quick prototyping, demo |
| **MCP Java SDK** | High | Full | Custom protocols, Spring Boot 4 compat issues |

**Recommendation**: Start with **Approach 1 (Spring AI)** since it fits naturally into the Spring Boot architecture. Skip file-upload endpoints (image upload, CSV upload) for MCP tools — they don't translate well to LLM tool calls.

---

## Approach 4: Node.js MCP Server with `@modelcontextprotocol/sdk` (Implemented)

A standalone Node.js MCP server that wraps the Spring Boot REST API. Each controller endpoint is registered as an MCP tool using the official `@modelcontextprotocol/sdk`. The server communicates over **stdio** transport.

**Location**: `mcp-server/` directory in the project root.

### Project Structure

```
mcp-server/
├── package.json       # Dependencies: @modelcontextprotocol/sdk, zod
├── server.js          # MCP server with 13 tools mapped to REST APIs
└── node_modules/
```

### Registered Tools (13 total)

| Admin Tools (5) | Seller Tools (4) | Customer Tools (4) |
|---|---|---|
| `admin_create_company` | `seller_create_product` | `customer_search_products` |
| `admin_create_seller` | `seller_update_product` | `customer_add_order_item` |
| `admin_create_category` | `seller_get_pending_orders` | `customer_get_order` |
| `admin_get_sellers` | `seller_accept_order` | `customer_submit_order` |
| `admin_delete_seller` | | |

> File-upload endpoints (image upload, CSV bulk upload) are intentionally excluded — they don't translate well to LLM tool calls.

### How It Works

1. The MCP server runs as a subprocess, communicating via **stdin/stdout** (stdio transport).
2. When an MCP client invokes a tool, the server maps it to an HTTP call against the Spring Boot REST API at `http://localhost:8080`.
3. The response JSON is returned to the MCP client as tool output.

```
MCP Client (e.g., Claude Code)
    ↕ stdio (JSON-RPC)
Node.js MCP Server (mcp-server/server.js)
    ↕ HTTP (fetch)
Spring Boot App (localhost:8080)
    ↕ JPA
MySQL Database
```

### Setup

```bash
cd mcp-server
npm install --registry https://registry.npmjs.org
```

### Usage with Claude Code

Add to `.claude.json` or Claude Code settings:

```json
{
  "mcpServers": {
    "ecommerce": {
      "command": "node",
      "args": ["<project-root>/mcp-server/server.js"]
    }
  }
}
```

To use a different API base URL (e.g., deployed server):

```json
{
  "mcpServers": {
    "ecommerce": {
      "command": "node",
      "args": ["<project-root>/mcp-server/server.js"],
      "env": {
        "API_BASE_URL": "http://your-server:8080"
      }
    }
  }
}
```

### Prerequisites

- **Node.js** 18+ installed
- **Spring Boot app running** on port 8080 (or set `API_BASE_URL` env var)

### Verification

```bash
# Test server starts and lists all 13 tools
printf '{"jsonrpc":"2.0","id":1,"method":"initialize","params":{"protocolVersion":"2024-11-05","capabilities":{},"clientInfo":{"name":"test","version":"1.0.0"}}}\n{"jsonrpc":"2.0","id":2,"method":"tools/list","params":{}}\n' \
  | node mcp-server/server.js 2>/dev/null
```

---

## Updated Comparison

| Approach | Effort | Control | Transport | Best For |
|---|---|---|---|---|
| **Spring AI MCP Server** | Medium | High | SSE (HTTP) | Production apps, Spring-native |
| **OpenAPI Bridge** | Zero | Low | Varies | Quick prototyping, demo |
| **MCP Java SDK** | High | Full | Any | Custom protocols, Spring Boot 4 compat |
| **Node.js MCP Server** | Low | High | Stdio | Ready-to-use, works with Claude Code |
