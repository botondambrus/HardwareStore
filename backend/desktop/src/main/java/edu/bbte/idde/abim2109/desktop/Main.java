package edu.bbte.idde.abim2109.desktop;

import edu.bbte.idde.abim2109.backend.crud.CRUD;
import edu.bbte.idde.abim2109.backend.crud.CRUDInterface;
import edu.bbte.idde.abim2109.backend.model.HardwareStoreModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final CRUDInterface<HardwareStoreModel> crud = new CRUD<>();
    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static void listHardware() {
        logger.info("All hardware:");

        for (HardwareStoreModel hardware : crud.readAll()) {
            logger.info(hardware.toString());
        }
    }

    private static String readString(BufferedReader bufferedReader, String message) throws IOException {
        logger.info(message);
        String input = bufferedReader.readLine();
        if (input == null || input.isEmpty()) {
            logger.warn("Input cannot be empty.\n");
            throw new IllegalArgumentException("Input cannot be empty.");
        }
        return input;
    }

    private static int readInt(BufferedReader bufferedReader, String message) throws IOException {
        String input = readString(bufferedReader, message);

        int value = Integer.parseInt(input.trim());
        if (value < 0) {
            logger.warn("Value cannot be negative.\n");
            throw new IllegalArgumentException("Value cannot be negative.");
        }
        return value;
    }

    private static void addHardware(BufferedReader bufferedReader) {
        try {
            HardwareStoreModel newHardware = new HardwareStoreModel();

            newHardware.setName(readString(bufferedReader, "Enter hardware name: "));
            newHardware.setCategory(readString(bufferedReader, "Enter hardware category: "));
            newHardware.setDescription(readString(bufferedReader, "Enter hardware description: "));
            newHardware.setPrice(readInt(bufferedReader, "Enter hardware price: "));
            newHardware.setQuantity(readInt(bufferedReader, "Enter hardware quantity: "));

            crud.create(newHardware);

        } catch (NumberFormatException e) {
            logger.warn("Invalid number format entered. Please enter a valid number.\n");
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid input. Please try again.\n");
        } catch (IOException e) {
            logger.error("Error reading input.\n");
        }
    }


    private static void updateHardware(BufferedReader bufferedReader) {
        try {
            int id = readInt(bufferedReader, "Enter hardware id: ");

            HardwareStoreModel existingHardware = crud.read(id);

            existingHardware.setName(readString(bufferedReader, "Enter hardware name: "));
            existingHardware.setCategory(readString(bufferedReader, "Enter hardware category: "));
            existingHardware.setDescription(readString(bufferedReader, "Enter hardware description: "));
            existingHardware.setPrice(readInt(bufferedReader, "Enter hardware price: "));
            existingHardware.setQuantity(readInt(bufferedReader, "Enter hardware quantity: "));

            crud.update(id, existingHardware);

        } catch (NumberFormatException e) {
            logger.warn("Invalid number format entered. Please enter a valid number.\n");
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid input. Please try again.\n");
        } catch (IOException e) {
            logger.error("Error reading input.\n");
        }
    }

    public static void deleteHardware(BufferedReader bufferedReader) {
        try {
            int id = readInt(bufferedReader, "Enter hardware id: ");

            crud.delete(id);
        } catch (NumberFormatException e) {
            logger.warn("Invalid number format entered. Please enter a valid number.\n");
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid input. Please try again.\n");
        } catch (IOException e) {
            logger.error("Error reading input.\n");
        }
    }

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        boolean running = true;

        while (running) {
            logger.info("1. List all hardware");
            logger.info("2. Add new hardware");
            logger.info("3. Update hardware");
            logger.info("4. Delete hardware");
            logger.info("5. Exit\n");
            logger.info("Select an option: ");

            try {
                int selectedNumber = Integer.parseInt(bufferedReader.readLine());

                switch (selectedNumber) {
                    case 1:
                        listHardware();
                        break;
                    case 2:
                        addHardware(bufferedReader);
                        break;
                    case 3:
                        updateHardware(bufferedReader);
                        break;
                    case 4:
                        deleteHardware(bufferedReader);
                        break;
                    case 5:
                        logger.info("Exiting...");
                        running = false;
                        break;
                    default:
                        logger.warn("Invalid option. Try again.\n");
                        break;
                }
            } catch (NumberFormatException e) {
                logger.error("Invalid input. Please enter a valid number.\n");
            } catch (IOException e) {
                logger.error("Error reading input. Exiting...");
                running = false;
            }
        }
    }
}