package org.edufood.edufood.service.service_interface;

import org.edufood.edufood.dto.request.UserRegisterRequest;
import org.edufood.edufood.entities.User;

public interface UserService {

    User register(UserRegisterRequest request);

    boolean existsByEmail(String email);
}
