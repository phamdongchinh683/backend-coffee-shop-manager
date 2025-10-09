package com.example.coffee_shop_manage_api.mock;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.coffee_shop_manage_api.global.MenuStatus;
import com.example.coffee_shop_manage_api.global.PaymentStatus;
import com.example.coffee_shop_manage_api.global.TableStatus;
import com.example.coffee_shop_manage_api.global.UserRole;
import com.example.coffee_shop_manage_api.model.CoffeeTable;
import com.example.coffee_shop_manage_api.model.Menu;
import com.example.coffee_shop_manage_api.model.User;
import com.example.coffee_shop_manage_api.repository.CoffeeTableRepository;
import com.example.coffee_shop_manage_api.repository.MenuRepository;
import com.example.coffee_shop_manage_api.repository.UserRepository;

@Configuration
public class DataInitializer {

 private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

 // Constants for better maintainability
 private static final int AVAILABLE_TABLES = 7;
 private static final int OCCUPIED_UNPAID_TABLE = 8;
 private static final int OCCUPIED_PAID_TABLE = 9;
 private static final int INACTIVE_TABLE = 10;

 private static final String SIZE_M_L_XL = "M,L,XL";
 private static final String SIZE_S_M_XL = "S,M,XL";
 private static final String SIZE_S_M_L = "S,M,L";
 private static final String COST_25_30_35 = "25,30,35";
 private static final String COST_30_35_40 = "30,35,40";
 private static final String COST_35_40_45 = "35,40,45";

 @Value("${account.admin.username}")
 private String adminUsername;
 @Value("${account.admin.password}")
 private String adminPassword;

 @Bean
 @Transactional
 CommandLineRunner initSampleData(
   UserRepository userRepository,
   PasswordEncoder passwordEncoder,
   MenuRepository menuRepository,
   CoffeeTableRepository tableRepository) {
  return args -> {
   try {
    logger.info("Starting data initialization...");

    initAdminAccount(userRepository, passwordEncoder);
    initSampleMenus(menuRepository);
    initSampleTables(tableRepository);

    logger.info("Data initialization completed successfully.");
   } catch (Exception e) {
    logger.error("Error during data initialization: {}", e.getMessage(), e);
    throw new RuntimeException("Failed to initialize sample data", e);
   }
  };
 }

 private void initAdminAccount(UserRepository userRepository, PasswordEncoder passwordEncoder) {
  if (userRepository.findByUsername(adminUsername).isEmpty()) {
   User admin = createAdminUser(passwordEncoder);
   userRepository.save(admin);
   logger.info("Admin account created successfully.");
  } else {
   logger.info("Admin account already exists. Skipping creation.");
  }
 }

 private User createAdminUser(PasswordEncoder passwordEncoder) {
  User admin = new User();
  // Note: This approach assumes Lombok generates setters. If not, we'll need to
  // use reflection or builder pattern
  admin.setFullName("Default Admin");
  admin.setUsername(adminUsername);
  admin.setPassword(passwordEncoder.encode(adminPassword));
  admin.setPhoneNumber("0772573366");
  admin.setRole(UserRole.ADMIN);
  return admin;
 }

 private void initSampleMenus(MenuRepository menuRepository) {
  if (menuRepository.count() == 0) {
   List<Menu> menus = createMenuList();
   menuRepository.saveAll(menus);
   logger.info("Sample menu items created successfully. Total: {}", menus.size());
  } else {
   logger.info("Menu items already exist. Skipping menu creation.");
  }
 }

 private List<Menu> createMenuList() {
  List<Menu> menus = new ArrayList<>();
  menus
    .add(createMenu("Cappuccino", "Espresso with steamed milk and foam", "39,45,52", SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus.add(createMenu("Latte", "Espresso with steamed milk", "39,45,52", SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus.add(createMenu("Espresso", "Espresso", COST_25_30_35, SIZE_S_M_XL, MenuStatus.ACTIVE));
  menus.add(createMenu("Americano", "Espresso diluted with hot water", COST_30_35_40, SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus.add(createMenu("Mocha", "Espresso with chocolate and milk", COST_35_40_45, SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus
    .add(createMenu("Caramel Macchiato", "Espresso with caramel and milk", "42,48,55", SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus.add(createMenu("Black Coffee", "Strong black brewed coffee", COST_25_30_35, SIZE_S_M_L, MenuStatus.ACTIVE));
  menus
    .add(createMenu("Hazelnut Coffee", "Coffee with hazelnut flavor", COST_35_40_45, SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus.add(createMenu("Matcha Latte", "Matcha powder with milk", COST_30_35_40, SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus.add(createMenu("Peach Tea with Lemongrass", "Peach tea with lemongrass", COST_30_35_40, SIZE_M_L_XL,
    MenuStatus.ACTIVE));
  menus.add(createMenu("Lychee Tea", "Refreshing lychee flavored tea", "28,32,38", SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus.add(createMenu("Honey Kumquat Tea", "Kumquat tea with honey", COST_25_30_35, SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus.add(createMenu("Green Tea Latte", "Green tea with milk foam", "32,38,45", SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus.add(createMenu("Milk Tea with Pearls", "Classic milk tea with tapioca pearls", COST_35_40_45, SIZE_M_L_XL,
    MenuStatus.ACTIVE));
  menus
    .add(createMenu("Avocado Smoothie", "Fresh avocado blended with milk", "40,45,50", SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus
    .add(createMenu("Mango Smoothie", "Smooth mango drink with milk", COST_35_40_45, SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus.add(createMenu("Orange Juice", "Freshly squeezed orange juice", COST_30_35_40, SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus
    .add(createMenu("Iced Chocolate", "Chocolate drink topped with cream", "45,50,55", SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus
    .add(createMenu("Cookies & Cream Frappe", "Blended cookies and milk", "45,50,55", SIZE_M_L_XL, MenuStatus.ACTIVE));
  menus.add(createMenu("Bottled Water", "Chilled bottled mineral water", "10,12,15", SIZE_S_M_L, MenuStatus.ACTIVE));

  return menus;
 }

 private Menu createMenu(String name, String description, String cost, String size, MenuStatus status) {
  Menu menu = new Menu();
  menu.setMenuName(name);
  menu.setDescription(description);
  menu.setCosts(cost);
  menu.setSizes(size);
  menu.setStatus(status);
  return menu;
 }

 private void initSampleTables(CoffeeTableRepository tableRepository) {
  if (tableRepository.count() == 0) {
   List<CoffeeTable> tables = createTableList();
   tableRepository.saveAll(tables);
   logger.info("Sample tables created successfully. Total: {}", tables.size());
  } else {
   logger.info("Tables already exist. Skipping table creation.");
  }
 }

 private List<CoffeeTable> createTableList() {
  List<CoffeeTable> tables = new ArrayList<>();

  // Create available tables (1-7)
  for (int i = 1; i <= AVAILABLE_TABLES; i++) {
   tables.add(createTable(i, TableStatus.AVAILABLE, PaymentStatus.PENDING));
  }

  // Create occupied unpaid table (8)
  tables.add(createTable(OCCUPIED_UNPAID_TABLE, TableStatus.OCCUPIED, PaymentStatus.PENDING));

  // Create occupied paid table (9)
  tables.add(createTable(OCCUPIED_PAID_TABLE, TableStatus.OCCUPIED, PaymentStatus.PAID));

  // Create inactive table (10)
  tables.add(createTable(INACTIVE_TABLE, TableStatus.INACTIVE, PaymentStatus.PENDING));

  return tables;
 }

 private CoffeeTable createTable(int tableNumber, TableStatus status, PaymentStatus paymentStatus) {
  CoffeeTable table = new CoffeeTable();
  table.setTableNumber((short) tableNumber);
  table.setStatus(status);
  table.setPaymentStatus(paymentStatus);
  return table;
 }

}
