package pos;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import pos.commands.AbstractCommand;
import pos.commands.Command;
import pos.commands.ListItemsCommand;
import pos.exception.CommandException;
import pos.models.Item;
import pos.services.POSService;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
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
        if(args.length == 0){
            System.out.println("CSV data file must be specified");
            return;
        }
        final String dataFile = args[0];
        File file = new FileSystemResource(dataFile).getFile();

        File copyFile = new File(getClass().getClassLoader().getResource(".").getFile() + file.getName());

        Path copied = copyFile.toPath();
        Path originalPath = file.toPath();
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);

        Reader reader = Files.newBufferedReader(Paths.get(copyFile.toURI()));
        List parse = new CsvToBeanBuilder(reader).withType(Item.class).build().parse();

        posHelper.printAllCommands();

        System.out.println("Enter commandCode to start: ");

        String commandCode;

        Scanner input = new Scanner(System.in);

        while (true){
            commandCode = input.nextLine();
            log.debug("Command entered: " + commandCode);
            Optional<AbstractCommand> commandByCode = posService.getCommandByCode(commandCode);
            Command command = commandByCode.orElse(new ListItemsCommand(posService, posHelper));
            try{
                command.execute();
            } catch (CommandException e){
                System.out.println(e.getMessage());
                continue;
            }
        }

      /*  while (!(commandCode = input.nextLine()).equals("exit")){
            log.debug("Command entered: " + commandCode);
            Optional<AbstractCommand> commandByCode = posService.getCommandByCode(commandCode);
            Command command = commandByCode.orElse(new ListItemsCommand(posService, posHelper));
            try{
                command.execute();
            } catch (CommandException e){
                System.out.println(e.getMessage());
                continue;
            }

        } */
    }
}

