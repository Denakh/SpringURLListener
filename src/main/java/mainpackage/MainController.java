package mainpackage;

import mainpackage.listeningresults.ListeningResultService;
import mainpackage.urls.ListenedUrl;
import mainpackage.urls.ListenedUrlService;
import mainpackage.users.CustomUser;
import mainpackage.users.UserRole;
import mainpackage.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private ListenedUrlService listenedUrlService;
    @Autowired
    private ListeningResultService listeningResultService;

    @RequestMapping("/")
    public String index(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser dbUser = userService.getUserByLogin(login);

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("email", dbUser.getEmail());
        model.addAttribute("phone", dbUser.getPhone());

        return "index";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam(required = false) String email, @RequestParam(required = false) String phone) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser dbUser = userService.getUserByLogin(login);
        dbUser.setEmail(email);
        dbUser.setPhone(phone);

        userService.updateUser(dbUser);

        return "redirect:/";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam String login,
                         @RequestParam String password,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone,
                         Model model) {
        if (userService.existsByLogin(login)) {
            model.addAttribute("exists", true);
            return "register";
        }

        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        String passHash = encoder.encodePassword(password, null);

        CustomUser dbUser = new CustomUser(login, passHash, UserRole.USER, email, phone);
        userService.addUser(dbUser);

        return "redirect:/";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }

    @RequestMapping("/results")
    public String results(Model model) {
        CustomUser user = this.getCurrentUser();
        this.modelAddAttForURLList(user, model);
        return "/url_list";
    }

    @RequestMapping("/url_execute")
    public String urlExecute(@RequestParam(defaultValue = "0") String inurl,
                             @RequestParam(defaultValue = "-") String inkeyword,
                             @RequestParam(defaultValue = "0") String inlimtime,
                             Model model) {
        if (inurl.equals("0") || inlimtime.equals("0")) return this.messageStr("Error in URL. Try again", model);
        long limtime;
        try {
            limtime = Long.parseLong(inlimtime);
        } catch (NumberFormatException e) {
            String errorStr = "Number format error for response limit time. Try again";
            return this.messageStr(errorStr, model);
        }
        String[] allStrAr = inurl.split("//");
        if (allStrAr.length < 2) return this.messageStr("Error in URL. Try again", model);
        String[] strAr;
        if (!allStrAr[1].isEmpty()) strAr = allStrAr[1].split("/", 2);
        else return this.messageStr("Error in URL. Try again", model);
        String server = allStrAr[0] + "//" + strAr[0];
        String uri = "/";
        if (strAr.length == 2) uri = uri + strAr[1];
        CustomUser user = this.getCurrentUser();
        listenedUrlService.addListenedUrl(new ListenedUrl(user, new Date(), server, uri, inkeyword, limtime));
        this.modelAddAttForURLList(user, model);
        return "/url_list";
    }

    @RequestMapping("/get_results")
    public String getListeningResults(@RequestParam(defaultValue = "0") String dellist,
                                      @RequestParam(defaultValue = "0") String urlresults,
                                      Model model) {
        if (urlresults.equals("0")) {
            if (!dellist.equals("0")) return this.listenedURLdeletion(dellist, model, false);
            return this.messageStr("URL for result hasn't been choose. Try again", model);
        }
        long urlid;
        try {
            urlid = Long.parseLong(urlresults);
        } catch (NumberFormatException e) {
            String errorStr = "Number format error for URL id. Try again";
            return this.messageStr(errorStr, model);
        }
        ListenedUrl listenedUrl = listenedUrlService.getURLById(urlid);
        this.modelAddAttForResultsList(listenedUrl, model);
        if (!dellist.equals("0")) return this.listenedURLdeletion(dellist, model, true);
        return "result";
    }

    private String listenedURLdeletion(String dellist, Model model, boolean resView) {
        String[] urlsForDelStr;
        List<Long> urlsForDel = new ArrayList<>();
        urlsForDelStr = dellist.split(",");
        try {
            if (urlsForDelStr != null) {
                for (String ids : urlsForDelStr) {
                    urlsForDel.add(Long.parseLong(ids));
                }
            }
        } catch (NumberFormatException e) {
            String errorStr = "Number format error for URL id. Try again";
            return this.messageStr(errorStr, model);
        }
        if (!urlsForDel.isEmpty()) {
            for (long id : urlsForDel) listenedUrlService.deleteListenedUrl(id);
        }
        if (resView) return "result";
        else return this.messageStr("URL deletion has been completed", model);
    }

    private void modelAddAttForURLList(CustomUser user, Model model) {
        model.addAttribute("exeURLList", listenedUrlService.getAllURLsByUser(user));
    }

    private void modelAddAttForResultsList(ListenedUrl listenedUrl, Model model) {
        model.addAttribute("url", listenedUrl.getServer() + listenedUrl.getUri());
        model.addAttribute("exeResultsList", listeningResultService.getAllResultsByURL(listenedUrl));
    }

    private String messageStr(String message, Model model) {
        model.addAttribute("message", message);
        return "/message";
    }

    private CustomUser getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        return userService.getUserByLogin(login);
    }

}
