package kz.dilau.htcdatamanager.security;

//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public final class SecurityUtils {
    private SecurityUtils() {
    }

    public static Optional<String> getCurrentUserLogin() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        return Optional.ofNullable(securityContext.getAuthentication())
//                .map(authentication -> {
//                    if (authentication.getPrincipal() instanceof UserDetails) {
//                        UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
//                        return springSecurityUser.getUsername();
//                    } else if (authentication.getPrincipal() instanceof String) {
//                        return (String) authentication.getPrincipal();
//                    }
//                    return null;
//                });
        return null;
    }

    public static boolean isAuthenticated() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        return Optional.ofNullable(securityContext.getAuthentication())
//                .map(authentication -> authentication.getAuthorities().stream()
//                        .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Roles.ANONYMOUS)))
//                .orElse(false);
        return false;
    }

    public static boolean isCurrentUserInRole(String authority) {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        return Optional.ofNullable(securityContext.getAuthentication())
//                .map(authentication -> authentication.getAuthorities().stream()
//                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
//                .orElse(false);
        return false;
    }

    public static String getRequestRemoteAddr() {
//        try {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                    .getRequest();
//            StringBuilder sb = new StringBuilder();
//            String remoteAddr = request.getHeader("X-FORWARDED-FOR");
//            if (StringUtils.isNotEmpty(remoteAddr)) {
//                sb.append(remoteAddr);
//            }
//            if (sb.length() > 0) {
//                sb.append("|");
//            }
//            sb.append(request.getRemoteAddr());
//            return sb.toString();
//        } catch (Exception e) {
//            return "unknown";
//        }
        return null;
    }
}
