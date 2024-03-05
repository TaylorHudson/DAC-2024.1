package br.edu.ifpb.dac;

import br.edu.ifpb.dac.domain.User;
import br.edu.ifpb.dac.domain.enumeration.UserType;
import br.edu.ifpb.dac.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public Application(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println(
                    """
                    Menu de opções
            
                    1 - Criar um usuário
                    2 - Mostrar todos os usuários     
                    S - Sair
                    """
            );

            String answer = sc.nextLine();

            switch (answer) {
                case "1" -> {
                    System.out.print("Digite 1 se for cliente ou 2 se for operador: ");
                    int code = Integer.parseInt(sc.nextLine());
                    UserType type = UserType.findByCode(code);
                    System.out.print("Primeiro nome: ");
                    String firstName = sc.nextLine();
                    System.out.print("Sobrenome: ");
                    String lastName = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Senha: ");
                    String password = sc.nextLine();
                    System.out.print("CPF: ");
                    String document = sc.nextLine();

                    User user = new User(
                            firstName,
                            lastName,
                            email,
                            password,
                            document,
                            type
                    );

                    userRepository.save(user);
                }
                case "2" -> userRepository.findAll().forEach(System.out::println);
                case "S" -> System.exit(0);
                default -> throw new RuntimeException("Valor inválido");
            }
        }
    }
}
