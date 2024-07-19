package ua.denys.carrentalservice.loginapp.session.service;

import ua.denys.carrentalservice.loginapp.common.db.session.repository.SessionRepository;
import ua.denys.carrentalservice.loginapp.session.model.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

import static ua.denys.carrentalservice.loginapp.utils.ConstUtils.PATH_TO_KEY;
import static ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils.SessionColumnConsts.dtf;
import static ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils.URL;

public class SessionService {
  private static SessionService INSTANCE = null;
  private static final SessionRepository repository = SessionRepository.getInstance(URL);

  private SessionService() {}

  public static SessionService getInstance() {
    if (INSTANCE == null) INSTANCE = new SessionService();
    return INSTANCE;
  }

  public void create(Long userId) throws SQLException {
    Session session = new Session(userId);
    try {
      createFileSessionKey(session);
    } catch (IOException e) {
      e.printStackTrace();
    }
    repository.registerSession(session);
  }

  public void delete() {
    try {
      repository.deleteSession(getSkByFile());
      deleteFileSessionKey();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  public String getSkByFile() {
    String sk = "";
    try {
      Scanner reader = new Scanner(new File(PATH_TO_KEY));
      while (reader.hasNext()) {
        sk = reader.next();
      }
      sk.substring(sk.indexOf("=") + 1);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return sk;
  }

  public boolean isExpired() {
    boolean result = false;
    try {
      String dateFromBase = repository.getSessionExpired_at(getSkByFile());
      String dateNow = LocalDateTime.now().format(dtf);
      if (dateFromBase.compareTo(dateNow) > 0) {
        result = false;
      } else {
        result = true;
      }
    } catch (SQLException exception) {
    }
    return result;
  }

  private void createFileSessionKey(Session sessionObject) throws IOException {
    File file = new File(PATH_TO_KEY);
    PrintWriter pw = new PrintWriter(file);
    pw.println(sessionObject.getUserId() + " = " + sessionObject.getSessionKey());
    pw.close();
    System.out.println("File with session key is created.");
  }

  private void deleteFileSessionKey() {
    if (new File(PATH_TO_KEY).delete()) {
      System.out.println("The key.sk is successfully deleted.");
    }
  }
}
