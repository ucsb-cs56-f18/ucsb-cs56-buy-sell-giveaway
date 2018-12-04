package edu.ucsb.cs56.pconrad.springboot.hello;


import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.pac4j.core.client.Client;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.springframework.annotation.ui.RequireAnyRole;
import org.pac4j.springframework.helper.UISecurityHelper;
import org.pac4j.springframework.web.LogoutController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import edu.ucsb.cs56.pconrad.springboot.hello.Posting;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;




@Controller
public class HelloController {


ArrayList<Posting> postings = new ArrayList<Posting>();


    @Value("${pac4j.centralLogout.defaultUrl:#{null}}")
    private String defaultUrl;

    @Value("${pac4j.centralLogout.logoutUrlPattern:#{null}}")
    private String logoutUrlPattern;

    @Autowired
    private Config config;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private UISecurityHelper uiSecurityHelper;

    private LogoutController logoutController;

    @PostConstruct
    protected void afterPropertiesSet() {
        logoutController = new LogoutController();
        logoutController.setDefaultUrl(defaultUrl);
        logoutController.setLogoutUrlPattern(logoutUrlPattern);
        logoutController.setLocalLogout(false);
        logoutController.setCentralLogout(true);
        logoutController.setConfig(config);
        logoutController.setDestroySession(false);
    }

@RequestMapping("/")
    public ModelAndView index() {
		System.out.println(postings);

		Map<String, Object> params = new HashMap<>();
		params.put("postings", postings);
		
		return new ModelAndView("index", params);
	}



public void setAttributes(Model model) {
		List<CommonProfile> profiles = uiSecurityHelper.getProfiles();
		model.addAttribute("profiles",profiles);
		model.addAttribute("profiles_s",profiles.toString());

		final J2EContext context = uiSecurityHelper.getJ2EContext();
		SessionStore<J2EContext> ss = context.getSessionStore();
		String sessionID = ss.getOrCreateSessionId(context);
        model.addAttribute("sessionId",sessionID);
	}

/*
@RequestMapping("new_post")
    public String new_post_form(Model model, @RequestParam(value="title",required=true,defaultValue="") String title,
				@RequestParam(value="desc",required=true,defaultValue="") String desc,
				@RequestParam(value="email",required=true,defaultValue="") String email,
				@RequestParam(value="number",required=true,defaultValue="") String number){
	Posting newPost = new Posting(title,desc,email,number);
	    if(PostVerifier.isValid(newPost)){
		postings.add(newPost);
		//Found how to redirect on this article: https://o7planning.org/en/11547/spring-boot-and-freemarker-tutorial
		return "redirect:/";
	    }
	    //Don't know if I need the following line?
	    //model.addAttribute("new_post", new Posting(title,desc,contact));

	    //Bad post
	    return "new_post";
    } 
*/

@RequestMapping("protected/new_post")
    public String new_post_form_protected(Model model, @RequestParam(value="title",required=true,defaultValue="") String title,
				@RequestParam(value="desc",required=true,defaultValue="") String desc,
				@RequestParam(value="email",required=true,defaultValue="") String email,
				@RequestParam(value="number",required=true,defaultValue="") String number){
	Posting newPost = new Posting(title,desc,email,number);
	    if(PostVerifier.isValid(newPost)){
		postings.add(newPost);
		//Found how to redirect on this article: https://o7planning.org/en/11547/spring-boot-and-freemarker-tutorial
		return "redirect:/";
	    }
	    //Don't know if I need the following line?
	    //model.addAttribute("new_post", new Posting(title,desc,contact));

	    //Bad post
		setAttributes(model);
	    return "new_post";
    }


    @ExceptionHandler(HttpAction.class)
    public void httpAction() {
        // do nothing
    }

} 


