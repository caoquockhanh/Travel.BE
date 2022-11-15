package com.MyTravel.mytravel.security.services;

import com.MyTravel.mytravel.exception.ApiException;
import com.MyTravel.mytravel.exception.ErrorCode;
import com.google.firebase.internal.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class AuthService {
//    public @NonNull Object getOptionalUid() {
//        Optional<Authentication> auth = Optional.ofNullable(
//                SecurityContextHolder.getContext().getAuthentication());
//        if (auth.isPresent()) {
//             return auth.get();
//        }
//        return Optional.empty();
//    }
//
//    public String getUid() {
//        Authentication auth = (Authentication) getOptionalUid();
//        return auth == null || auth instanceof AnonymousAuthenticationToken
//                ? null
//                : auth.getName();
//    }

    public String getUsername(Principal principal)
    {
        return principal.getName();
    }
}

