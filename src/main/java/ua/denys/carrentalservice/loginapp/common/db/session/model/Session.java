package ua.denys.carrentalservice.loginapp.common.db.session.model;

import ua.denys.carrentalservice.loginapp.common.db.ModelPattern;

import java.time.LocalDateTime;

public class Session extends ModelPattern {
  Long id;
  Long userId;
  String sessionKey;
  LocalDateTime expiredAt;
}
