package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXISTINGCONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Nric;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_BIRTHDATE, PREFIX_SEX,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_BLOODTYPE, PREFIX_NOKNAME, PREFIX_NOKPHONE,
                        PREFIX_ALLERGY, PREFIX_HEALTHRISK, PREFIX_EXISTINGCONDITION, PREFIX_NOTE);

        Nric nric;

        try {
            nric = ParserUtil.parseNric(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_NRIC, PREFIX_BIRTHDATE, PREFIX_SEX, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_BLOODTYPE, PREFIX_NOKNAME, PREFIX_NOKPHONE, PREFIX_ALLERGY,
                PREFIX_HEALTHRISK, PREFIX_EXISTINGCONDITION, PREFIX_NOTE);

        EditPatientDescriptor editPatientDescriptor = new EditPatientDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPatientDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            editPatientDescriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }
        if (argMultimap.getValue(PREFIX_BIRTHDATE).isPresent()) {
            editPatientDescriptor.setBirthDate(ParserUtil.parseBirthDate(argMultimap.getValue(PREFIX_BIRTHDATE).get()));
        }
        if (argMultimap.getValue(PREFIX_SEX).isPresent()) {
            editPatientDescriptor.setSex(ParserUtil.parseSex(argMultimap.getValue(PREFIX_SEX).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPatientDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPatientDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPatientDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_BLOODTYPE).isPresent()) {
            editPatientDescriptor.setBloodType(ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOODTYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_NOKNAME).isPresent()) {
            editPatientDescriptor.setNokName(ParserUtil.parseNokName(argMultimap.getValue(PREFIX_NOKNAME).get()));
        }
        if (argMultimap.getValue(PREFIX_NOKPHONE).isPresent()) {
            editPatientDescriptor.setNokPhone(ParserUtil.parseNokPhone(argMultimap.getValue(PREFIX_NOKPHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_ALLERGY).isPresent()) {
            // TODO remove the comment yuancheng
            // editPatientDescriptor.setAllergy(ParserUtil.parseAllergy(argMultimap.getValue(PREFIX_ALLERGY).get()));
        }
        if (argMultimap.getValue(PREFIX_HEALTHRISK).isPresent()) {
            editPatientDescriptor.setHealthRisk(ParserUtil.parseHealthRisk(
                    argMultimap.getValue(PREFIX_HEALTHRISK).get()));
        }
        if (argMultimap.getValue(PREFIX_EXISTINGCONDITION).isPresent()) {
            editPatientDescriptor.setExistingCondition(ParserUtil.parseExistingCondition(
                    argMultimap.getValue(PREFIX_EXISTINGCONDITION).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            editPatientDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }

        if (!editPatientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }
        return new EditCommand(nric, editPatientDescriptor);
    }

}
