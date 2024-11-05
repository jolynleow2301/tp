package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Appt;
import seedu.address.model.healthservice.HealthService;

/**
 * Contains integration tests (interaction with the Model) and unit tests for BookApptCommand.
 */
public class BookApptCommandTest {

    private Model model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());

    @Test
    public void execute_patientAcceptedByModel_addSuccessful() throws Exception {
        Nric validNric = new Nric("S1234567A");
        Appt validAppt = new Appt(LocalDateTime.of(2024, 11, 15, 14, 0), 
            new HealthService("Consult"));
        BookApptCommand command = new BookApptCommand(validNric, validAppt);
        CommandResult commandResult = command.execute(model);
        assertEquals(BookApptCommand.MESSAGE_APPT_ADDED_SUCCESS, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateAppt_throwsCommandException() {
        Nric validNric = new Nric("S1234567A");
        Appt duplicateAppt = new Appt(LocalDateTime.of(2024, 11, 15, 14, 0), 
            new HealthService("Consult"));

        BookApptCommand command = new BookApptCommand(validNric, duplicateAppt);
        assertThrows(CommandException.class, () -> command.execute(model), 
            BookApptCommand.MESSAGE_DUPLICATE_APPT);
    }

    @Test
    public void execute_patientNotFound_throwsCommandException() {
        Nric nonExistentNric = new Nric("S9999999B");
        Appt validAppt = new Appt(LocalDateTime.of(2024, 11, 16, 16, 0), 
            new HealthService("Blood Test"));
        BookApptCommand command = new BookApptCommand(nonExistentNric, validAppt);
        assertThrows(CommandException.class, () -> command.execute(model), 
            BookApptCommand.MESSAGE_PATIENT_NOT_FOUND);
    }

    @Test
    public void equals() {
        Appt appt1 = new Appt(LocalDateTime.of(2024, 12, 25, 10, 30), new HealthService("Dermatology"));
        Appt appt2 = new Appt(LocalDateTime.of(2025, 1, 1, 9, 0), new HealthService("Optometry"));
        Nric nric1 = new Nric("S8888888C");
        Nric nric2 = new Nric("S7777777D");

        BookApptCommand bookApptCommand1 = new BookApptCommand(nric1, appt1);
        BookApptCommand bookApptCommand2 = new BookApptCommand(nric1, appt1);
        BookApptCommand bookApptCommand3 = new BookApptCommand(nric2, appt2);

        assertTrue(bookApptCommand1.equals(bookApptCommand1)); // same object
        assertTrue(bookApptCommand1.equals(bookApptCommand2)); // same values
        assertFalse(bookApptCommand1.equals(bookApptCommand3)); // different data
        assertFalse(bookApptCommand1.equals(null)); // null
    }
}
