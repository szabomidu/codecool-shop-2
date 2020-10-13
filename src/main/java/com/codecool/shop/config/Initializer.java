package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        Supplier harryPotter = new Supplier("Harry Potter", "Magic world of the boy who lived");
        Supplier lordOfTheRings = new Supplier("Lord of the Rings", "Tale of sneaky little hobbits");
        Supplier gameOfThrones = new Supplier("Game of Thrones", "Medieval series with swords and titties");
        Supplier starWars = new Supplier("Star Wars", "Armless and legless people fight in space");

        supplierDataStore.addAll(harryPotter, lordOfTheRings, gameOfThrones, starWars);

        ProductCategory wizard = new ProductCategory("Wizard", "Figure", "Magical guy with wand or walking stick");
        ProductCategory muggle = new ProductCategory("Muggle", "Figure", "Non-magical guy without wand or walking stick");
        ProductCategory hobbit = new ProductCategory("Hobbit", "Figure", "Hairy soled cute little alcoholic creature");
        ProductCategory elf = new ProductCategory("Elf", "Figure", "Guy with pointy ears");
        ProductCategory dwarf = new ProductCategory("Dwarf", "Figure", "Stuffy hairy caveman");
        ProductCategory jedi = new ProductCategory("Jedi", "Figure", "Cloaked man with a colorful dingdong");
        ProductCategory sith = new ProductCategory("Sith", "Figure", "Evil cloaked man with a red dingdong");
        ProductCategory human = new ProductCategory("Human", "Figure", "Not much really");
        ProductCategory dragon = new ProductCategory("Dragon", "Figure", "Fire-breathing winged pet");

        productCategoryDataStore.addAll(wizard, muggle, hobbit, elf, dwarf, jedi, sith, human, dragon);

        Product gandalf = new Product("Gandalf the Grey", 49.9f, "USD", "Fantastic price. Changeable cloak (grey and white).", wizard, lordOfTheRings);
        Product uncleVernon = new Product("Uncle Vernon", 19.9f, "USD", "Fat guy with abusive attitude", muggle, harryPotter);
        Product sam = new Product("Samwise Gamgee", 59.9f, "USD", "He cannot carry the ring but he can carry you", hobbit, lordOfTheRings);
        Product legolas = new Product("Legolas", 34.9f, "USD", "Perfect for seeing issues. Sees everything instead of you", elf, lordOfTheRings);
        Product ronWeasley = new Product("Ron Weasley", 19.9f, "USD", "Unimportant ginger buddy. Suites well for French people, because he eats snails as fast as hell", wizard, harryPotter);
        Product harryPotterCharacter = new Product("Harry Potter", 29.9f, "USD", "The choosen one with tha scar on his head", wizard, harryPotter);
        Product hermioneGranger = new Product("Hermione Granger", 39.9f, "USD", "Crush of every 90's kid", wizard, harryPotter);
        Product gimli = new Product("Gimli", 44.9f, "USD", "He would die before he sees the ring in an elf's hand", dwarf, lordOfTheRings);
        Product drogon = new Product("Drogon", 69.9f, "USD", "Gigantic aggressive dragon aka The Winged Shadow", dragon, gameOfThrones);
        Product obiWan = new Product("Obi Wan Kenobi", 24.9f, "USD", "Hello there", jedi, starWars);
        Product anakin = new Product("Anakin Skywalker", 20.9f, "USD", "Psychological wreck aka Darth Vader", sith, starWars);
        Product windu = new Product("Master Windu", 24.9f, "USD", "Black guy with long purple lightsaber", jedi, starWars);

        productDataStore.addAll(gandalf, uncleVernon, sam, legolas, harryPotterCharacter, ronWeasley, hermioneGranger, gimli, drogon, obiWan, anakin, windu);

    }
}
