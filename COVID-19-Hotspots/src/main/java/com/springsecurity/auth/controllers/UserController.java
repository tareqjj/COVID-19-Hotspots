package com.springsecurity.auth.controllers;

import com.springsecurity.auth.models.Test;
import com.springsecurity.auth.models.User;
import com.springsecurity.auth.services.TestService;
import com.springsecurity.auth.services.UserService;
import com.springsecurity.auth.validator.TestValidator;
import com.springsecurity.auth.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final TestService testService;
    private final UserValidator userValidator;
    private final TestValidator testValidator;

    public UserController(UserService userService, TestService testService, UserValidator userValidator, TestValidator testValidator) {
        this.userService = userService;
        this.testService = testService;
        this.userValidator = userValidator;
        this.testValidator = testValidator;
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result) {
        userValidator.validate(user, result);
        if (result.hasErrors())
            return "loginRegistration.jsp";
        if (userService.allUsers().isEmpty())
            userService.saveUser(user, "ROLE_SUPER");
        else
            userService.saveUser(user, "ROLE_USER");
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, @ModelAttribute("user") User user, Principal principal, Model model) {
        if (error != null)
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        if (logout != null)
            model.addAttribute("logoutMessage", "Logout Successful!");
        return "loginRegistration.jsp";
    }

    @RequestMapping("/")
    public String routing(Principal principal) {
        String username = principal.getName();
        User loggedUser = userService.findByUsername(username);
        userService.updateLastSignIn(loggedUser);
        if (loggedUser.getRoles().containsAll(userService.findRoleByName("ROLE_SUPER")))
            return "redirect:/super";
        if (loggedUser.getRoles().containsAll(userService.findRoleByName("ROLE_AGENT")))
            return "redirect:/agent";
        if (loggedUser.getRoles().containsAll(userService.findRoleByName("ROLE_TESTER")))
            return "redirect:/tester";
        return "redirect:/dashboard";

    }
    @RequestMapping("/dashboard")
    public String userDashboard(Principal principal, Model model) {
        String username = principal.getName();
        User loggedUser = userService.findByUsername(username);
        model.addAttribute("loggedUser", loggedUser);
        return "dashboard.jsp";
    }

    @RequestMapping(value = "/super")
    public String superDashboard(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("loggedUser", userService.findByUsername(username));
        model.addAttribute("allUsers", userService.allUsers());
        return "admin.jsp";
    }

    @RequestMapping("/agent")
    public String agentDashboard(Principal principal, Model model) {
        String username = principal.getName();
        User loggedUser = userService.findByUsername(username);
        model.addAttribute("loggedUser", loggedUser);
        List<Test> submitted = testService.findTestByStatus("Submitted");
        model.addAttribute("submitted", submitted);
        return "approval.jsp";
    }
    @RequestMapping("/agent/tests/{test_id}")
    public String testAproval(Principal principal, Model model, @PathVariable("test_id") Long test_id) {
        String username = principal.getName();
        User loggedUser = userService.findByUsername(username);
        model.addAttribute("loggedUser", loggedUser);
        Test test = testService.findTestById(test_id);
//        List<Location> locations = test.getLocations();
        model.addAttribute("test", test);
//        model.addAttribute("locations", locations);
        return "testSubmit.jsp";
    }
    @RequestMapping("/agent/search")
    public String searchTestByRecord(@RequestParam("record_id") Long record_id, Model model) {
        if (testService.findTestByRecordId(record_id) != null) {
            List<Test> tests = testService.findTestByRecordId(record_id);
            model.addAttribute("submitted", tests);
            return "approval.jsp";
        }
        model.addAttribute("error", "No test found. Enter a valid sample test");
        return "results.jsp";
    }

    @RequestMapping("/agent/tests/{test_id}/submit")
    public String testSubmit(@PathVariable("test_id")Long test_id, @RequestParam("sample")Long sample, @Valid @ModelAttribute("test") Test test, BindingResult result,Model model) {
        test.setSample(sample);
        testValidator.validate(test, result);
        if(result.hasErrors()){
            model.addAttribute("test", test);
            return "testSubmit.jsp";
        }else{
            Test test1 = testService.findTestById(test_id);
            test1.setSample(sample);
            test1.setStatus("Pending");
            testService.creatTestxx(test1);
            return "redirect:/agent";
        }

    }

    @RequestMapping("/tester")
    public String testerDashboard(Principal principal, Model model) {
        String username = principal.getName();
        User loggedUser = userService.findByUsername(username);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("allPendingTests", testService.findTestByStatus("Pending"));
        return "results.jsp";
    }

    @RequestMapping("/tester/search")
    public String searchTestBySample(@RequestParam("sample_id") Long sample_id, Model model) {
         if (testService.findTestBySample(sample_id) != null)
            return "redirect:/tester/test/" + sample_id;
         model.addAttribute("error", "No test found");
         return "results.jsp";
    }

    @RequestMapping("/tester/test/{sample_id}")
    public String displayTest(@PathVariable("sample_id") Long sample_id, Model model){
        model.addAttribute("test", testService.findTestBySample(sample_id));
        return "testResult.jsp";
    }

    @RequestMapping("/tester/test/result/{sample_id}")
    public String setResult(@PathVariable("sample_id") Long sample_id, @RequestParam("testResult") String testResult) {
        Test test = testService.findTestBySample(sample_id);
        testService.setTestResult(test, testResult);
        return "redirect:/tester/test/" + sample_id;
    }

    @RequestMapping("/destroy/{user_id}")
    public String destroy(@PathVariable("user_id") Long user_id) {
        userService.deleteUserById(user_id);
        return "redirect:/super";
    }

    @PostMapping("/super/updateRole/{user_id}")
    public String updateRole(@PathVariable("user_id") Long user_id, @RequestParam("roleAdmin") boolean roleAdmin, @RequestParam("roleTester") boolean roleTester, @RequestParam("roleUser") boolean roleUser) {
        User user = userService.findUserById(user_id);
        if (roleUser) {
            if (!user.getRoles().containsAll(userService.findRoleByName("ROLE_USER")))
                userService.addUserRole(user, "ROLE_USER");
        }
        else
            userService.removeUserRole(user, "ROLE_USER");

        if (roleTester) {
            if (!user.getRoles().containsAll(userService.findRoleByName("ROLE_TESTER")))
                userService.addUserRole(user, "ROLE_TESTER");
        }
        else
            userService.removeUserRole(user, "ROLE_TESTER");

        if (roleAdmin) {
            if (!user.getRoles().containsAll(userService.findRoleByName("ROLE_AGENT")))
                userService.addUserRole(user, "ROLE_AGENT");
        }
        else
            userService.removeUserRole(user, "ROLE_AGENT");

        return "redirect:/super";
    }

}
