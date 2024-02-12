package deim.urv.cat.homework2.service;

import deim.urv.cat.homework2.model.CredentialsDTO;
import jakarta.ws.rs.core.Response;

public interface LoginService {

    Response authenticateUser(CredentialsDTO credentialsDTO);
}
