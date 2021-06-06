package pos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pos.commands.AbstractCommand;
import pos.commands.Command;
import pos.commands.ListItemsCommand;
import pos.services.POSService;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    @Autowired
    POSService posService;
    @Autowired
    POSHelper posHelper;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        // disable spring banner
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        String commandCode;

        posHelper.printAllCommands();

        Scanner input = new Scanner(System.in);

        System.out.println("Enter commandCode to start: ");
        while (!(commandCode = input.nextLine()).equals("exit")){
            log.info("Command entered: " + commandCode);
            Optional<AbstractCommand> commandByCode = posService.getCommandByCode(commandCode);
            Command command = commandByCode.orElse(new ListItemsCommand(posService, posHelper));
            command.execute();

        }
    }
}

