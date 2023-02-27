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
    public static final String ADDRESS_POST_REQUEST_BODY = """
            { 
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
}