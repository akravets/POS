package pos;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.exceptions.CsvException;
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
import pos.provider.DataProvider;
import pos.services.POSService;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    @Autowired
    POSService posService;
    @Autowired
    POSHelper posHelper;
    @Autowired
    DataProvider dataProvider;

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

    /*    File copyFile = new File(getClass().getClassLoader().getResource(".").getFile() + file.getName());

        Path copied = copyFile.toPath();
        Path originalPath = file.toPath();
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);*/

        Reader reader = Files.newBufferedReader(Paths.get(file.toURI()));

        Set<CsvException> exceptionSet = new LinkedHashSet<>();

        List<Item> items = new CsvToBeanBuilder(reader).withExceptionHandler(new CsvExceptionHandler() {
            @Override
            public CsvException handleException(CsvException e) throws CsvException {
                exceptionSet.add(e);
                return new CsvException(e.getMessage());
            }
        }).withType(Item.class).build().parse();


        if(exceptionSet.size() != 0){
            System.out.println("Following warnings occurred while loading data:\n");
            for (CsvException csvException : exceptionSet) {
                System.out.println("\t"+csvException.toString());
            }
            System.out.println("\nBad data was removed during import\n");
        }

        dataProvider.setItems(items);

        posHelper.printAllCommands();

        System.out.print("\nEnter commandCode to start: ");

        Scanner scanner = new Scanner(System.in);

        while (true){
            String input = scanner.nextLine();
            log.debug("Command entered: " + input);
            Optional<AbstractCommand> commandByCode = posService.getCommandByCode(input);
            Command command = commandByCode.orElse(new ListItemsCommand(posService, posHelper));
            try{
                command.execute(input);
            } catch (CommandException e){
                System.out.println(e.getMessage());
                continue;
            }
        }
    }
}

