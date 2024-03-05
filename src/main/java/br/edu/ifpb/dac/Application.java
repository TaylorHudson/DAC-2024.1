package br.edu.ifpb.dac;

import br.edu.ifpb.dac.domain.User;
import br.edu.ifpb.dac.domain.enumeration.UserType;
import br.edu.ifpb.dac.persistence.repository.UserRepository;
import br.edu.ifpb.dac.util.reader.DataReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public Application(UserRepository userRepository, @Qualifier("scanner") DataReader reader) {
        this.userRepository = userRepository;
        this.reader = reader;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final UserRepository userRepository;
    private final DataReader reader;

    @Override
    public void run(String... args) {
        while (true) {
            String answer = reader.read(
                    """
                    Menu de opções
                                
                    1 - Criar um usuário
                    2 - Mostrar todos os usuários     
                    S - Sair
                    """
            );

            switch (answer) {
                case "1" -> {
                    int code = Integer.parseInt(reader.read("Digite 1 se for cliente ou 2 se for operador: "));
                    UserType type = UserType.findByCode(code);
                    String firstName = reader.read("Nome: ");
                    String lastName = reader.read("Sobrenome: ");
                    String email = reader.read("Email: ");
                    String password = reader.read("Senha: ");
                    String document = reader.read("CPF: ");

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
