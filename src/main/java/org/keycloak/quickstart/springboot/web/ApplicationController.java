package org.keycloak.quickstart.springboot.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.quickstart.springboot.security.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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

    @GetMapping("/protected")
    public String handleProtected(Model model) {
        configCommonAttributes(model);
        return "protected";
    }

    @GetMapping("/protected/premium")
    public String handlePremium(Model model) {
        configCommonAttributes(model);
        return "premium";
    }

    @GetMapping("/logout")
    public String handleLogoutt() throws ServletException {
        request.logout();
        return "redirect:/";
    }

    @GetMapping("/")
    public String handleHome(Model model) throws ServletException {
        configCommonAttributes(model);
        return "home";
    }

    @GetMapping("/accessDenied")
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
