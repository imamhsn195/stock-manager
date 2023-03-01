package me.imamhasan.stockmanager.config;

public class SwaggerPayloads {
    public static final String PRODUCT_POST_REQUEST_BODY = """
            {
                "name": "Product One",
                "description": "Product One description",
                "productCategory" : {
                    "id" : 1,
                    "name": "Product Category"
                },
                "price": 10,
                "quantity": 100
            }
        """;
    public static final String PRODUCT_PUT_REQUEST_BODY = """
            {
                "id" : 1,
                "name": "Product One",
                "description": "Product One description",
                "productCategory" : {
                    "id" : 1,
                    "name": "Product Category"
                },
                "price": 10,
                "quantity": 100
            }
        """;

    public static final String SUPPLIER_POST_REQUEST_BODY = """
            {
                "name" : "John Doe",
                "email" : "johndoe@example.com",
                "phone" : "(123)456-7890",
                "address" : {
                     "id" : 1,
                     "street" : "123 Main St",
                     "city" : "Anytown",
                     "state" : "CA", 
                     "country" : "USA", 
                     "zipCode" : "12345"
                 }
            }
        """;
    public static final String SUPPLIER_PUT_REQUEST_BODY = """
            {
                "id" : 1,
                "name" : "John Doe",
                "email" : "johndoe@example.com",
                "phone" : "(123)456-7890",
                "address" : { 
                     "id" : 1,
                     "street" : "123 Main St",
                     "city" : "Anytown", 
                     "state" : "CA", 
                     "country" : "USA", 
                     "zipCode" : "12345"
                 }
            }
        """;
    public static final String CUSTOMER_POST_REQUEST_BODY = """  
            {
                "name" : "John Doe",
                "email" : "johndoe@example.com",
                "phone" : "(123)456-7890",
                "address" : {
                     "id" : 1,
                     "street" : "123 Main St",
                     "city" : "Anytown", 
                     "state" : "CA", 
                     "country" : "USA", 
                     "zipCode" : "12345"
                 }
            }
        """;
    public static final String CUSTOMER_PUT_REQUEST_BODY = """  
            {
                "id" : 1,
                "name" : "John Doe",
                "email" : "johndoe@example.com",
                "phone" : "(123)456-7890",
                "address" : { 
                     "id" : 1,
                     "street" : "123 Main St",
                     "city" : "Anytown", 
                     "state" : "CA", 
                     "country" : "USA", 
                     "zipCode" : "12345"
                 }
            }
        """;
    public static final String ADDRESS_POST_REQUEST_BODY = """
            { 
                "street" : "123 Main St",
                "city" : "Anytown", 
                "state" : "CA", 
                "country" : "USA", 
                "zipCode" : "12345"
            }
        """;
    public static final String ADDRESS_PUT_REQUEST_BODY = """
            { 
                "id" : 1,
                "street" : "123 Main St",
                "city" : "Anytown", 
                "state" : "CA", 
                "country" : "USA", 
                "zipCode" : "12345"
            }
        """;
    public static final String PRODUCT_CATEGORY_POST_REQUEST_BODY = """
            {
                "name": "Product Category"
            }    
        """;
    public static final String PRODUCT_CATEGORY_PUT_REQUEST_BODY = """
            {
                "id" : 1,
                "name": "Product Category"
            }    
        """;
    public static final String ORDER_POST_REQUEST_BODY = """
            {
                "orderDate" : "2023-01-12",
                "customer" : {
                    "name" : "John Doe",
                    "email" : "johndoe@example.com",
                    "phone" : "(123)456-7890",
                    "address" : {
                        "id" : 1
                    }
                }
            }
        """;
    public static final String ORDER_PUT_REQUEST_BODY = """
            {
                "id" : 1,
                "orderDate" : "2023-01-12",
                "customer" : {
                    "name" : "John Doe",
                    "email" : "johndoe@example.com",
                    "phone" : "(123)456-7890",
                    "address" : {
                        "id" : 1
                    }
                }
            }
        """;
    public static final String ORDER_ITEM_POST_REQUEST_BODY = """
            {
                "order" : {
                        "id" : 1
                    },
                "product" : {
                        "id" : 1
                    },
                "quantityOrdered" : 10,
                "orderDate" : "2022-01-25"
            }
        """;
    public static final String ORDER_ITEM_PUT_REQUEST_BODY = """
            {
                "id" : 1,
                "order" : {
                        "id" : 1
                    },
                "product" : {
                        "id" : 1
                    },
                "quantityOrdered" : 10,
                "orderDate" : "2022-01-25"
            }
        """;
    public static final String PURCHASE_ITEM_POST_REQUEST_BODY = """
                {
                    "purchase" : {
                        "id" : 1
                    },
                    "product" : {
                        "id" : 1
                    },
                    "quantityPurchased" : 10,
                    "purchaseDate" : "2022-01-25"
                }
            """;
    public static final String PURCHASE_ITEM_PUT_REQUEST_BODY = """
            {
                "id" : 1,
                "purchase" : {
                        "id" : 1
                    },
                "product" : {
                        "id" : 1
                    },
                "quantityPurchased" : 10,
                "purchaseDate" : "2022-01-25"
            }
        """;
    public static final String PURCHASE_POST_REQUEST_BODY = """
            {
                "purchaseDate" : "2023-01-12",
                "supplier" : {
                "name" : "John Doe",
                "email" : "johndoe@example.com",
                "phone" : "(123)456-7890",
                "address" : {
                        "id" : 1
                    }
                }
            }
        """;
    public static final String PURCHASE_PUT_REQUEST_BODY = """
            {
                "id" : 1,
                "purchaseDate" : "2023-01-12",
                "supplier" : {
                "name" : "John Doe",
                "email" : "johndoe@example.com",
                "phone" : "(123)456-7890",
                "address" : {
                        "id" : 1
                    }
                }
            }
        """;
}
