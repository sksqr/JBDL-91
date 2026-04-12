import { McpServer } from "@modelcontextprotocol/sdk/server/mcp.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import { z } from "zod";

const BASE_URL = process.env.API_BASE_URL || "http://localhost:8080";

// Helper: call the Spring Boot REST API
async function callApi(method, path, { query, body } = {}) {
  const url = new URL(path, BASE_URL);
  if (query) {
    for (const [k, v] of Object.entries(query)) {
      if (v !== undefined && v !== null) url.searchParams.set(k, String(v));
    }
  }

  const opts = {
    method,
    headers: { "Content-Type": "application/json" },
  };
  if (body) opts.body = JSON.stringify(body);

  const res = await fetch(url, opts);
  const text = await res.text();

  if (!res.ok) {
    return { content: [{ type: "text", text: `Error ${res.status}: ${text}` }] };
  }

  return {
    content: [{ type: "text", text: text || `Success (${res.status})` }],
  };
}

// ── Create MCP Server ────────────────────────────────────────────────
const server = new McpServer({
  name: "ecommerce-mcp-server",
  version: "1.0.0",
});

// ═══════════════════════════════════════════════════════════════════════
//  ADMIN TOOLS
// ═══════════════════════════════════════════════════════════════════════

server.tool(
  "admin_create_company",
  "Create a company and its primary admin user in one request",
  {
    companyName: z.string().describe("Company name"),
    companyNumber: z.string().describe("Company registration number"),
    isActive: z.boolean().optional().default(true).describe("Whether company is active"),
    primaryUserName: z.string().describe("Primary user's full name"),
    primaryUserEmail: z.string().describe("Primary user's email"),
    primaryUserPassword: z.string().describe("Primary user's password"),
  },
  async ({ companyName, companyNumber, isActive, primaryUserName, primaryUserEmail, primaryUserPassword }) => {
    return callApi("POST", "/api/admin/company", {
      body: {
        name: companyName,
        number: companyNumber,
        isActive,
        primaryUser: {
          name: primaryUserName,
          email: primaryUserEmail,
          password: primaryUserPassword,
        },
      },
    });
  }
);

server.tool(
  "admin_create_seller",
  "Create a seller user for an existing company",
  {
    companyId: z.number().describe("ID of the company"),
    name: z.string().describe("Seller's full name"),
    email: z.string().describe("Seller's email"),
    password: z.string().describe("Seller's password"),
  },
  async ({ companyId, name, email, password }) => {
    return callApi("POST", "/api/admin/seller", {
      body: { companyId, name, email, password },
    });
  }
);

server.tool(
  "admin_create_category",
  "Create a new product category",
  {
    name: z.string().describe("Category name"),
    description: z.string().optional().describe("Category description"),
  },
  async ({ name, description }) => {
    return callApi("POST", "/api/admin/category", {
      body: { name, description },
    });
  }
);

server.tool(
  "admin_get_sellers",
  "Get paginated list of all sellers",
  {
    page: z.number().optional().default(0).describe("Page number (0-based)"),
    size: z.number().optional().default(10).describe("Page size"),
    sortBy: z.string().optional().default("id").describe("Sort field (e.g. id, createdAt)"),
    direction: z.string().optional().default("asc").describe("Sort direction: asc or desc"),
  },
  async ({ page, size, sortBy, direction }) => {
    return callApi("GET", "/api/admin/sellers", {
      query: { page, size, sortBy, direction },
    });
  }
);

server.tool(
  "admin_delete_seller",
  "Delete a seller by ID (cannot delete primary company user)",
  {
    sellerId: z.number().describe("Seller user ID to delete"),
  },
  async ({ sellerId }) => {
    return callApi("DELETE", `/api/admin/seller/${sellerId}`);
  }
);

// ═══════════════════════════════════════════════════════════════════════
//  SELLER TOOLS
// ═══════════════════════════════════════════════════════════════════════

server.tool(
  "seller_create_product",
  "Create a new product for a company under a category",
  {
    name: z.string().describe("Product name"),
    description: z.string().optional().describe("Product description"),
    imageUrl: z.string().optional().describe("Product image URL"),
    price: z.number().positive().describe("Product price (must be > 0)"),
    stock: z.number().int().describe("Stock quantity"),
    companyId: z.number().describe("Company ID"),
    categoryId: z.number().describe("Category ID"),
    isActive: z.boolean().optional().default(true).describe("Whether product is active"),
  },
  async (params) => {
    return callApi("POST", "/api/seller/product", { body: params });
  }
);

server.tool(
  "seller_update_product",
  "Update an existing product by product ID",
  {
    productId: z.number().describe("Product ID to update"),
    name: z.string().describe("Product name"),
    description: z.string().optional().describe("Product description"),
    imageUrl: z.string().optional().describe("Product image URL"),
    price: z.number().positive().describe("Product price (must be > 0)"),
    stock: z.number().int().describe("Stock quantity"),
    companyId: z.number().describe("Company ID"),
    categoryId: z.number().describe("Category ID"),
    isActive: z.boolean().optional().default(true).describe("Whether product is active"),
  },
  async ({ productId, ...body }) => {
    return callApi("PUT", `/api/seller/products/${productId}`, { body });
  }
);

server.tool(
  "seller_get_pending_orders",
  "Get all PLACED (pending) orders for a company",
  {
    companyId: z.number().describe("Company ID"),
  },
  async ({ companyId }) => {
    return callApi("GET", "/api/seller/orders/pending", {
      query: { companyId },
    });
  }
);

server.tool(
  "seller_accept_order",
  "Accept a placed order (changes status from PLACED to ACCEPTED)",
  {
    orderId: z.number().describe("Order ID to accept"),
  },
  async ({ orderId }) => {
    return callApi("PUT", `/api/seller/orders/${orderId}/accept`);
  }
);

// ═══════════════════════════════════════════════════════════════════════
//  CUSTOMER TOOLS
// ═══════════════════════════════════════════════════════════════════════

server.tool(
  "customer_search_products",
  "Search products by keyword in name or description",
  {
    keyword: z.string().describe("Search keyword"),
  },
  async ({ keyword }) => {
    return callApi("GET", "/api/customer/products", {
      query: { keyword },
    });
  }
);

server.tool(
  "customer_add_order_item",
  "Add an item to user's cart (DRAFT order). Creates a new order if no active draft exists.",
  {
    userId: z.number().describe("Customer user ID"),
    productId: z.number().describe("Product ID to add"),
    quantity: z.number().int().min(1).describe("Quantity (minimum 1)"),
  },
  async ({ userId, productId, quantity }) => {
    return callApi("POST", "/api/customer/order-item", {
      body: { userId, productId, quantity },
    });
  }
);

server.tool(
  "customer_get_order",
  "Get full order details including all items and total amount",
  {
    orderId: z.number().describe("Order ID"),
  },
  async ({ orderId }) => {
    return callApi("GET", `/api/customer/order/${orderId}`);
  }
);

server.tool(
  "customer_submit_order",
  "Submit a draft order (changes status from DRAFT to PLACED)",
  {
    orderId: z.number().describe("Order ID to submit"),
  },
  async ({ orderId }) => {
    return callApi("PUT", `/api/customer/order/${orderId}/submit`);
  }
);

// ── Start Server ─────────────────────────────────────────────────────
async function main() {
  const transport = new StdioServerTransport();
  await server.connect(transport);
  console.error("E-Commerce MCP Server running on stdio");
}

main().catch((err) => {
  console.error("Fatal error:", err);
  process.exit(1);
});
