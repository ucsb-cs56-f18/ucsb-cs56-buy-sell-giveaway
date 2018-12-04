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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


@Controller
public class HelloController {


    ArrayList<Posting> postingsNeedApproval = new ArrayList<Posting>();
    ArrayList<Posting> postingsApproved = new ArrayList<Posting>();


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
	System.out.println(postingsApproved);

	Map<String, Object> params = new HashMap<>();
	params.put("postings", postingsApproved);
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
//will prompt a git login
@RequestMapping("/protected/new_post")
    public String new_post_form(Model model, @RequestParam(value="title",required=true,defaultValue="") String title,
				@RequestParam(value="desc",required=true,defaultValue="") String desc,
				@RequestParam(value="email",required=true,defaultValue="") String email,
				@RequestParam(value="number",required=true,defaultValue="") String number){
	//Create a posting object for new post
	//Generate Random Number for ID
	Random rand = new Random();
	//Some Range
	int max = 1000000;
	int min = 1;
	//https://stackoverflow.com/questions/363681/how-to-generate-random-integers-within-a-specific-range-in-java	
	int randomNum = rand.nextInt((max-min)+1)+min;
	Posting newPost = new Posting(title,desc,email,number,randomNum);
	
	if(PostVerifier.isValid(newPost)){
	    postingsNeedApproval.add(newPost);
	    //Found how to redirect on this article: https://o7planning.org/en/11547/spring-boot-and-freemarker-tutorial
	    return "redirect:/";
	}

	//Bad post
	return "new_post";
    }

    //To-do Implement github logins so we can have admins to the site and review/approve post.
    @RequestMapping("/admin_login")
    public String admin_login(){
	//Update when implmented
	return "/";
    }

//http://www.mkyong.com/spring-mvc/spring-mvc-form-handling-example/
    //Deals with approval button on admin 
    //This will be the page were we do all the verification.
    @RequestMapping("/admin/post")
//    @RequireAnyRole("ROLE_ADMIN")
    public ModelAndView admin(){
	System.out.println(postingsNeedApproval);

	Map<String, Object> params = new HashMap<>();
	params.put("postings", postingsNeedApproval);
		
	return new ModelAndView("admin", params);
    }

    // https://hellokoding.com/spring-boot-hello-world-example-with-freemarker/
    //https://stackoverflow.com/questions/49154565/how-to-call-a-method-with-a-button-in-freemarker-dropwizard
    //https://spring.io/guides/tutorials/rest/

    //In the FTL file the action has to be action = /approve/${posting.id} to work. Also this value is a string so needs to be parse into int.
    @GetMapping("/approve/{idString}")
    public String approve(Model model, @PathVariable String idString) {
	//idString has a comma we need to deal with before parsing
	idString=idString.replace(",","");
	int id = Integer.parseInt(idString);
	Posting temp;
	for(int i = 0; i< postingsNeedApproval.size();i++){
	    temp = postingsNeedApproval.get(i);
	    if(id == temp.getId()){
		postingsApproved.add(temp);
		postingsNeedApproval.remove(i);
		break;
	    }
	}
	
	return "redirect:/admin/post";
    }

    @ExceptionHandler(HttpAction.class)
    public void httpAction() {
        // do nothing
    }


     @GetMapping("/remove/{idString}")
    public String remove(Model model, @PathVariable String idString) {
	//idString has a comma we need to deal with before parsing
	idString=idString.replace(",","");
	int id = Integer.parseInt(idString);
	Posting temp;
	for(int i = 0; i< postingsNeedApproval.size();i++){
	    temp = postingsNeedApproval.get(i);
	    if(id == temp.getId()){
		postingsNeedApproval.remove(i);
		break;
	    }
	}
	
	return "redirect:/admin/post";
    }
  
}
