package br.edu.ifpb.dac;

import br.edu.ifpb.dac.controller.CategoryController;
import br.edu.ifpb.dac.controller.ProductController;
import br.edu.ifpb.dac.model.Category;
import br.edu.ifpb.dac.model.Product;
import br.edu.ifpb.dac.model.User;
import br.edu.ifpb.dac.model.enumeration.UserType;
import br.edu.ifpb.dac.model.repository.UserRepository;
import br.edu.ifpb.dac.util.reader.DataReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public Application(
            UserRepository userRepository,
            ProductController productController,
            CategoryController categoryController,
            @Qualifier("scanner") DataReader reader) {
        this.userRepository = userRepository;
        this.productController = productController;
        this.categoryController = categoryController;
        this.reader = reader;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final UserRepository userRepository;
    private final ProductController productController;
    private final CategoryController categoryController;
    private final DataReader reader;

    @Override
    public void run(String... args) {
        while (true) {
            String answer = reader.read(
                    """
                    
                    Menu de opções
                    
                    Qual entidade irá manipular?        
                    1 - Usuário
                    2 - Produto
                    3 - Categoria     
                    S - Sair
                    """
            );

            switch (answer) {
                case "1" -> handleUserMenu();
                case "2" -> handleProductMenu();
                case "3" -> handleCategoryMenu();
                case "S" -> System.exit(0);
                default -> throw new RuntimeException("Valor inválido");
            }
        }
    }

    private void handleUserMenu() {
        while (true) {
            String option = reader.read(
                    """
                                                        
                    1 - Criar usuário
                    2 - Mostrar usuários
                    S - Sair
                    """
            );

            switch (option) {
                case "1" -> {
                    User user = readUserData();
                    userRepository.save(user);
                }
                case "2" -> userRepository.findAll().forEach(System.out::println);
                case "S" -> {
                    return;
                }
                default -> throw new RuntimeException("Valor inválido");
            }
        }
    }

    private void handleProductMenu() {
        while (true) {
            String option = reader.read(
                    """
                                                        
                    1 - Criar produto
                    2 - Mostrar produtos
                    S - Sair
                    """
            );

            switch (option) {
                case "1" -> {
                    Product product = readProductData();
                    productController.save(product);
                }
                case "2" -> productController.getProducts().forEach(System.out::println);
                case "S" -> {
                    return;
                }
                default -> throw new RuntimeException("Valor inválido");
            }

        }
    }

    private void handleCategoryMenu() {
        while (true) {
            String option = reader.read(
                    """
                                                        
                    1 - Criar categoria
                    2 - Mostrar categorias
                    S - Sair
                    """
            );

            switch (option) {
                case "1" -> {
                    Category category = readCategoryData();
                    categoryController.save(category);
                }
                case "2" -> categoryController.getCategories().forEach(System.out::println);
                case "S" -> {
                    return;
                }
                default -> throw new RuntimeException("Valor inválido");
            }

        }
    }

    private User readUserData() {
        int code = Integer.parseInt(reader.read("Digite 1 se for cliente ou 2 se for operador: "));
        UserType type = UserType.findByCode(code);
        String firstName = reader.read("Nome: ");
        String lastName = reader.read("Sobrenome: ");
        String email = reader.read("Email: ");
        String password = reader.read("Senha: ");
        String document = reader.read("CPF: ");

        return new User(
                firstName,
                lastName,
                email,
                password,
                document,
                type
        );
    }

    private Product readProductData() {
        String name = reader.read("Nome: ");
        String description = reader.read("Descrição: ");
        Double price = Double.valueOf(reader.read("Preço: "));
        Long categoryId = Long.valueOf(reader.read("ID da categoria do produto: "));
        String urls = reader.read("Url de imagens do produto(Ex: url1, url2 ...): ");

        List<String> imagesList = Arrays.asList(urls.split(", "));
        Set<String> images = new HashSet<>(imagesList);
        Category category = categoryController.getCategoryById(categoryId);

        return new Product(
                name,
                description,
                price,
                images,
                category
        );

    }

    private Category readCategoryData() {
        String name = reader.read("Nome: ");
        String description = reader.read("Descrição: ");

        return new Category(
                name,
                description
        );

    }
}
