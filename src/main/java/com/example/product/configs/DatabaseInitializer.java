package com.example.product.configs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.product.constants.GenderEnum;
import com.example.product.constants.PermissionEnum;
import com.example.product.constants.RoleEnum;
import com.example.product.entities.users.Permission;
import com.example.product.entities.users.Role;
import com.example.product.entities.users.User;
import com.example.product.repositories.PermissionRepo;
import com.example.product.repositories.RoleRepo;
import com.example.product.repositories.UserRepository;

@Service
public class DatabaseInitializer implements CommandLineRunner {

        private final PermissionRepo permissionRepository;
        private final RoleRepo roleRepository;
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public DatabaseInitializer(
                        PermissionRepo permissionRepository,
                        RoleRepo roleRepository,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
                this.permissionRepository = permissionRepository;
                this.roleRepository = roleRepository;
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) throws Exception {
                System.out.println(">>> START INIT DATABASE");
                long countPermissions = this.permissionRepository.count();
                long countRoles = this.roleRepository.count();
                long countUsers = this.userRepository.count();

                if (countPermissions == 0) {
                        ArrayList<Permission> arr = new ArrayList<>();
                        arr.add(new Permission(PermissionEnum.CREATE_ACCOUNT_IMAGE, "/api/v1/account-images", "POST",
                                        "ACCOUNT_IMAGES"));
                        arr.add(new Permission(PermissionEnum.UPDATE_ACCOUNT_IMAGE, "/api/v1/account-images/{id}",
                                        "PUT",
                                        "ACCOUNT_IMAGES"));
                        arr.add(new Permission(PermissionEnum.DELETE_ACCOUNT_IMAGE, "/api/v1/account-images/{id}",
                                        "DELETE",
                                        "ACCOUNT_IMAGES"));
                        arr.add(new Permission(PermissionEnum.GET_ACCOUNT_IMAGE_BY_ID, "/api/v1/account-images/{id}",
                                        "GET",
                                        "ACCOUNT_IMAGES"));
                        arr.add(new Permission(PermissionEnum.GET_ACCOUNT_IMAGES, "/api/v1/account-images", "GET",
                                        "ACCOUNT_IMAGES"));

                        arr.add(new Permission(PermissionEnum.CREATE_ADDRESS, "/api/v1/addresses", "POST",
                                        "ADDRESSES"));
                        arr.add(new Permission(PermissionEnum.UPDATE_ADDRESS, "/api/v1/addresses/{id}", "PUT",
                                        "ADDRESSES"));
                        arr.add(new Permission(PermissionEnum.DELETE_ADDRESS, "/api/v1/addresses/{id}", "DELETE",
                                        "ADDRESSES"));
                        arr.add(new Permission(PermissionEnum.GET_ADDRESS_BY_ID, "/api/v1/addresses/{id}", "GET",
                                        "ADDRESSES"));
                        arr.add(new Permission(PermissionEnum.GET_ADDRESSES, "/api/v1/addresses", "GET", "ADDRESSES"));

                        arr.add(new Permission(PermissionEnum.CREATE_CATEGORY, "/api/v1/categories", "POST",
                                        "CATEGORIES"));
                        arr.add(new Permission(PermissionEnum.UPDATE_CATEGORY, "/api/v1/categories/{id}", "PUT",
                                        "CATEGORIES"));
                        arr.add(new Permission(PermissionEnum.DELETE_CATEGORY, "/api/v1/categories/{id}", "DELETE",
                                        "CATEGORIES"));
                        arr.add(new Permission(PermissionEnum.GET_CATEGORY_BY_ID, "/api/v1/categories/{id}", "GET",
                                        "CATEGORIES"));
                        arr.add(new Permission(PermissionEnum.GET_CATEGORIES, "/api/v1/categories", "GET",
                                        "CATEGORIES"));

                        arr.add(new Permission(PermissionEnum.CREATE_CUSTOMER, "/api/v1/customers", "POST",
                                        "CUSTOMERS"));
                        arr.add(new Permission(PermissionEnum.UPDATE_CUSTOMER, "/api/v1/customers/{id}", "PUT",
                                        "CUSTOMERS"));
                        arr.add(new Permission(PermissionEnum.DELETE_CUSTOMER, "/api/v1/customers/{id}", "DELETE",
                                        "CUSTOMERS"));
                        arr.add(new Permission(PermissionEnum.GET_CUSTOMER_BY_ID, "/api/v1/customers/{id}", "GET",
                                        "CUSTOMERS"));
                        arr.add(new Permission(PermissionEnum.GET_CUSTOMERS, "/api/v1/customers", "GET", "CUSTOMERS"));

                        arr.add(new Permission(PermissionEnum.CREATE_DISCOUNT, "/api/v1/discounts", "POST",
                                        "DISCOUNTS"));
                        arr.add(new Permission(PermissionEnum.UPDATE_DISCOUNT, "/api/v1/discounts/{id}", "PUT",
                                        "DISCOUNTS"));
                        arr.add(new Permission(PermissionEnum.DELETE_DISCOUNT, "/api/v1/discounts/{id}", "DELETE",
                                        "DISCOUNTS"));
                        arr.add(new Permission(PermissionEnum.GET_DISCOUNT_BY_ID, "/api/v1/discounts/{id}", "GET",
                                        "DISCOUNTS"));
                        arr.add(new Permission(PermissionEnum.GET_DISCOUNTS, "/api/v1/discounts", "GET", "DISCOUNTS"));

                        arr.add(new Permission(PermissionEnum.CREATE_INVENTORY, "/api/v1/inventories", "POST",
                                        "INVENTORIES"));
                        arr.add(new Permission(PermissionEnum.UPDATE_INVENTORY, "/api/v1/inventories/{id}", "PUT",
                                        "INVENTORIES"));
                        arr.add(new Permission(PermissionEnum.DELETE_INVENTORY, "/api/v1/inventories/{id}", "DELETE",
                                        "INVENTORIES"));
                        arr.add(new Permission(PermissionEnum.GET_INVENTORY_BY_ID, "/api/v1/inventories/{id}", "GET",
                                        "INVENTORIES"));
                        arr.add(new Permission(PermissionEnum.GET_INVENTORIES, "/api/v1/inventories", "GET",
                                        "INVENTORIES"));

                        arr.add(new Permission(PermissionEnum.CREATE_INVOICE, "/api/v1/invoices", "POST", "INVOICES"));
                        arr.add(new Permission(PermissionEnum.UPDATE_INVOICE, "/api/v1/invoices/{id}", "PUT",
                                        "INVOICES"));
                        arr.add(new Permission(PermissionEnum.DELETE_INVOICE, "/api/v1/invoices/{id}", "DELETE",
                                        "INVOICES"));
                        arr.add(new Permission(PermissionEnum.GET_INVOICE_BY_ID, "/api/v1/invoices/{id}", "GET",
                                        "INVOICES"));
                        arr.add(new Permission(PermissionEnum.GET_INVOICES, "/api/v1/invoices", "GET", "INVOICES"));

                        arr.add(new Permission(PermissionEnum.CREATE_ORDER, "/api/v1/orders", "POST", "ORDERS"));
                        arr.add(new Permission(PermissionEnum.UPDATE_ORDER, "/api/v1/orders/{id}", "PUT", "ORDERS"));
                        arr.add(new Permission(PermissionEnum.DELETE_ORDER, "/api/v1/orders/{id}", "DELETE", "ORDERS"));
                        arr.add(new Permission(PermissionEnum.GET_ORDER_BY_ID, "/api/v1/orders/{id}", "GET", "ORDERS"));
                        arr.add(new Permission(PermissionEnum.GET_ORDERS, "/api/v1/orders", "GET", "ORDERS"));

                        arr.add(new Permission(PermissionEnum.CREATE_ORDER_ITEM, "/api/v1/order-items", "POST",
                                        "ORDER_ITEMS"));
                        arr.add(new Permission(PermissionEnum.UPDATE_ORDER_ITEM, "/api/v1/order-items/{id}", "PUT",
                                        "ORDER_ITEMS"));
                        arr.add(new Permission(PermissionEnum.DELETE_ORDER_ITEM, "/api/v1/order-items/{id}", "DELETE",
                                        "ORDER_ITEMS"));
                        arr.add(new Permission(PermissionEnum.GET_ORDER_ITEM_BY_ID, "/api/v1/order-items/{id}", "GET",
                                        "ORDER_ITEMS"));
                        arr.add(new Permission(PermissionEnum.GET_ORDER_ITEMS, "/api/v1/order-items", "GET",
                                        "ORDER_ITEMS"));

                        arr.add(new Permission(PermissionEnum.CREATE_PAYMENT, "/api/v1/payments", "POST", "PAYMENTS"));
                        arr.add(new Permission(PermissionEnum.UPDATE_PAYMENT, "/api/v1/payments/{id}", "PUT",
                                        "PAYMENTS"));
                        arr.add(new Permission(PermissionEnum.DELETE_PAYMENT, "/api/v1/payments/{id}", "DELETE",
                                        "PAYMENTS"));
                        arr.add(new Permission(PermissionEnum.GET_PAYMENT_BY_ID, "/api/v1/payments/{id}", "GET",
                                        "PAYMENTS"));
                        arr.add(new Permission(PermissionEnum.GET_PAYMENTS, "/api/v1/payments", "GET", "PAYMENTS"));

                        arr.add(new Permission(PermissionEnum.CREATE_PERMISSION, "/api/v1/permissions", "POST",
                                        "PERMISSIONS"));
                        arr.add(new Permission(PermissionEnum.UPDATE_PERMISSION, "/api/v1/permissions/{id}", "PUT",
                                        "PERMISSIONS"));
                        arr.add(new Permission(PermissionEnum.DELETE_PERMISSION, "/api/v1/permissions/{id}", "DELETE",
                                        "PERMISSIONS"));
                        arr.add(new Permission(PermissionEnum.GET_PERMISSION_BY_ID, "/api/v1/permissions/{id}", "GET",
                                        "PERMISSIONS"));
                        arr.add(new Permission(PermissionEnum.GET_PERMISSIONS, "/api/v1/permissions", "GET",
                                        "PERMISSIONS"));

                        arr.add(new Permission(PermissionEnum.CREATE_PRODUCT_ATTRIBUTE, "/api/v1/product-attributes",
                                        "POST",
                                        "PRODUCT_ATTRIBUTES"));
                        arr.add(new Permission(PermissionEnum.UPDATE_PRODUCT_ATTRIBUTE,
                                        "/api/v1/product-attributes/{id}",
                                        "PUT",
                                        "PRODUCT_ATTRIBUTES"));
                        arr.add(new Permission(PermissionEnum.DELETE_PRODUCT_ATTRIBUTE,
                                        "/api/v1/product-attributes/{id}", "DELETE",
                                        "PRODUCT_ATTRIBUTES"));
                        arr.add(new Permission(PermissionEnum.GET_PRODUCT_ATTRIBUTE_BY_ID,
                                        "/api/v1/product-attributes/{id}", "GET",
                                        "PRODUCT_ATTRIBUTES"));
                        arr.add(new Permission(PermissionEnum.GET_PRODUCT_ATTRIBUTES, "/api/v1/product-attributes",
                                        "GET",
                                        "PRODUCT_ATTRIBUTES"));

                        arr.add(new Permission(PermissionEnum.CREATE_PRODUCT_ATTRIBUTE_VALUE,
                                        "/api/v1/product-attribute-values",
                                        "POST", "PRODUCT_ATTRIBUTE_VALUES"));
                        arr.add(new Permission(PermissionEnum.UPDATE_PRODUCT_ATTRIBUTE_VALUE,
                                        "/api/v1/product-attribute-values/{id}",
                                        "PUT", "PRODUCT_ATTRIBUTE_VALUES"));
                        arr.add(new Permission(PermissionEnum.DELETE_PRODUCT_ATTRIBUTE_VALUE,
                                        "/api/v1/product-attribute-values/{id}", "DELETE", "PRODUCT_ATTRIBUTE_VALUES"));
                        arr.add(new Permission(PermissionEnum.GET_PRODUCT_ATTRIBUTE_VALUE_BY_ID,
                                        "/api/v1/product-attribute-values/{id}", "GET", "PRODUCT_ATTRIBUTE_VALUES"));
                        arr.add(new Permission(PermissionEnum.GET_PRODUCT_ATTRIBUTE_VALUES,
                                        "/api/v1/product-attribute-values",
                                        "GET", "PRODUCT_ATTRIBUTE_VALUES"));

                        arr.add(new Permission(PermissionEnum.CREATE_PRODUCT, "/api/v1/products", "POST", "PRODUCTS"));
                        arr.add(new Permission(PermissionEnum.UPDATE_PRODUCT, "/api/v1/products/{id}", "PUT",
                                        "PRODUCTS"));
                        arr.add(new Permission(PermissionEnum.DELETE_PRODUCT, "/api/v1/products/{id}", "DELETE",
                                        "PRODUCTS"));
                        arr.add(new Permission(PermissionEnum.GET_PRODUCT_BY_ID, "/api/v1/products/{id}", "GET",
                                        "PRODUCTS"));
                        arr.add(new Permission(PermissionEnum.GET_PRODUCTS, "/api/v1/products", "GET", "PRODUCTS"));

                        arr.add(new Permission(PermissionEnum.CREATE_PRODUCT_VARIANT, "/api/v1/product-variants",
                                        "POST",
                                        "PRODUCT_VARIANTS"));
                        arr.add(new Permission(PermissionEnum.UPDATE_PRODUCT_VARIANT, "/api/v1/product-variants/{id}",
                                        "PUT",
                                        "PRODUCT_VARIANTS"));
                        arr.add(new Permission(PermissionEnum.DELETE_PRODUCT_VARIANT, "/api/v1/product-variants/{id}",
                                        "DELETE",
                                        "PRODUCT_VARIANTS"));
                        arr.add(new Permission(PermissionEnum.GET_PRODUCT_VARIANT_BY_ID,
                                        "/api/v1/product-variants/{id}", "GET",
                                        "PRODUCT_VARIANTS"));
                        arr.add(new Permission(PermissionEnum.GET_PRODUCT_VARIANTS, "/api/v1/product-variants", "GET",
                                        "PRODUCT_VARIANTS"));

                        arr.add(new Permission(PermissionEnum.CREATE_REVIEW, "/api/v1/reviews", "POST", "REVIEWS"));
                        arr.add(new Permission(PermissionEnum.UPDATE_REVIEW, "/api/v1/reviews/{id}", "PUT", "REVIEWS"));
                        arr.add(new Permission(PermissionEnum.DELETE_REVIEW, "/api/v1/reviews/{id}", "DELETE",
                                        "REVIEWS"));
                        arr.add(new Permission(PermissionEnum.GET_REVIEW_BY_ID, "/api/v1/reviews/{id}", "GET",
                                        "REVIEWS"));
                        arr.add(new Permission(PermissionEnum.GET_REVIEWS, "/api/v1/reviews", "GET", "REVIEWS"));

                        arr.add(new Permission(PermissionEnum.CREATE_ROLE, "/api/v1/roles", "POST", "ROLES"));
                        arr.add(new Permission(PermissionEnum.UPDATE_ROLE, "/api/v1/roles/{id}", "PUT", "ROLES"));
                        arr.add(new Permission(PermissionEnum.DELETE_ROLE, "/api/v1/roles/{id}", "DELETE", "ROLES"));
                        arr.add(new Permission(PermissionEnum.GET_ROLE_BY_ID, "/api/v1/roles/{id}", "GET", "ROLES"));
                        arr.add(new Permission(PermissionEnum.GET_ROLES, "/api/v1/roles", "GET", "ROLES"));

                        arr.add(new Permission(PermissionEnum.CREATE_ROLE_PERMISSION, "/api/v1/role-permissions",
                                        "POST",
                                        "ROLE_PERMISSIONS"));
                        arr.add(new Permission(PermissionEnum.UPDATE_ROLE_PERMISSION, "/api/v1/role-permissions/{id}",
                                        "PUT",
                                        "ROLE_PERMISSIONS"));
                        arr.add(new Permission(PermissionEnum.DELETE_ROLE_PERMISSION, "/api/v1/role-permissions/{id}",
                                        "DELETE",
                                        "ROLE_PERMISSIONS"));
                        arr.add(new Permission(PermissionEnum.GET_ROLE_PERMISSION_BY_ID,
                                        "/api/v1/role-permissions/{id}", "GET",
                                        "ROLE_PERMISSIONS"));
                        arr.add(new Permission(PermissionEnum.GET_ROLE_PERMISSIONS, "/api/v1/role-permissions", "GET",
                                        "ROLE_PERMISSIONS"));

                        arr.add(new Permission(PermissionEnum.CREATE_USER, "/api/v1/users", "POST", "USERS"));
                        arr.add(new Permission(PermissionEnum.UPDATE_USER, "/api/v1/users/{id}", "PUT", "USERS"));
                        arr.add(new Permission(PermissionEnum.DELETE_USER, "/api/v1/users/{id}", "DELETE", "USERS"));
                        arr.add(new Permission(PermissionEnum.GET_USER_BY_ID, "/api/v1/users/{id}", "GET", "USERS"));
                        arr.add(new Permission(PermissionEnum.GET_USERS, "/api/v1/users", "GET", "USERS"));

                        this.permissionRepository.saveAll(arr);
                }

                if (countRoles == 0) {
                        List<Permission> permissionList = this.permissionRepository.findAll();
                        Set<Permission> allPermissions = new HashSet<>(permissionList);

                        Role adminRole = new Role();
                        adminRole.setName(RoleEnum.SUPER_ADMIN);
                        adminRole.setPermissions(allPermissions);

                        this.roleRepository.save(adminRole);
                }

                if (countUsers == 0) {
                        User adminUser = new User();
                        adminUser.setEmail("admin@gmail.com");
                        adminUser.setGender(GenderEnum.MALE);
                        adminUser.setName("I'm super admin");
                        adminUser.setPassword(this.passwordEncoder.encode("123456"));

                        Role adminRole = this.roleRepository.findByName(RoleEnum.SUPER_ADMIN).orElse(null);
                        if (adminRole != null) {
                                adminUser.setRoles(Collections.singleton(adminRole));
                        }
                        this.userRepository.save(adminUser);
                }

                if (countPermissions > 0 && countRoles > 0 && countUsers > 0) {
                        System.out.println(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
                } else
                        System.out.println(">>> END INIT DATABASE");
        }

}
