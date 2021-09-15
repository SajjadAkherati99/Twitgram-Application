package Database;

import Model.Request.SignUpRequest;
import Model.enums.LastSeenTypes;
import Model.enums.PrivacyTypes;
import Model.shared.Message.Chat;
import Model.shared.UserInformation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.HashMap;

public class Database {
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private Reader reader = new Reader(objectMapper, objectWriter);
    private Writer writer = new Writer(objectMapper);
    private Checker checker = new Checker();
    private DateAndTimeHandler dateAndTimeHandler = new DateAndTimeHandler();
    private static Database database = null;

    public Database(Reader reader, Writer writer, Checker checker, DateAndTimeHandler dateAndTimeHandler) {
        this.reader = reader;
        this.writer = writer;
        this.checker = checker;
        this.dateAndTimeHandler = dateAndTimeHandler;
    }

    public Database() {
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public Checker getChecker() {
        return checker;
    }

    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    public DateAndTimeHandler getDateAndTimeHandler() {
        return dateAndTimeHandler;
    }

    public void setDateAndTimeHandler(DateAndTimeHandler dateAndTimeHandler) {
        this.dateAndTimeHandler = dateAndTimeHandler;
    }

    public static Database getInstance(){
        if (database == null){
            database = new Database();
        }
        return database;
    }

    public UserInformation createUser(SignUpRequest user){
        return new UserInformation(
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.getBirthday(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getBio(),
                PrivacyTypes.PUBLIC,
                LastSeenTypes.EVERYBODY,
                true
        );
    }
}
