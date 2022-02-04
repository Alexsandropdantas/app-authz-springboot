package org.keycloak.quickstart.springboot.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.quickstart.springboot.security.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author <a href="mailto:psilva@redhat.com">Pedro Igor</a>
 */
@Controller
public class ApplicationController implements ErrorController{

    private final static String PATH = "/error";
    @RequestMapping(PATH)
    @ResponseBody
    public String getErrorPath() {
        return "Página de Erro da Aplicação - Verificar com a GSDS";
    }
	
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/protected", method = RequestMethod.GET)
    public String handleProtected(Model model) {
        configCommonAttributes(model);
        return "protected";
    }

    @RequestMapping(value = "/protected/premium", method = RequestMethod.GET)
    public String handlePremium(Model model) {
        configCommonAttributes(model);
        return "premium";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String handleLogoutt() throws ServletException {
        request.logout();
        return "redirect:/";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String handleHome(Model model) throws ServletException {
        configCommonAttributes(model);
        return "home";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String handleAccessDenied() throws ServletException {
        return "access-denied";
    }

    private void configCommonAttributes(Model model) {
        model.addAttribute("identity", new Identity(getKeycloakSecurityContext()));
    }

    private KeycloakSecurityContext getKeycloakSecurityContext() {
        return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
    }
}
