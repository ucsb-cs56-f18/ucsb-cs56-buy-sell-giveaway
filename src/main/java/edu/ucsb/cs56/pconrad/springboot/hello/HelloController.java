package edu.ucsb.cs56.pconrad.springboot.hello;

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
	/*
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	*/

   
    ArrayList<Posting> postings = new ArrayList<Posting>();
    
    @RequestMapping("/")
    public ModelAndView index() {
	/*ArrayList<Posting> postings = new ArrayList<Posting>();
		postings.add(new Posting("What a title!", "Great description!", "Number: 555-555-5555\nEmail: fake@email.com"));
		postings.add(new Posting("What a title 2!", "Great description 2!", "Number: 444-555-5555\nEmail: fake@email.com"));
		postings.add(new Posting("What a title 3!", "Great description 3!", "Number: 333-555-5555\nEmail: fake@email.com"));
	*/
		System.out.println(postings);

		Map<String, Object> params = new HashMap<>();
		params.put("postings", postings);
		
		return new ModelAndView("index", params);
	}

    //Info on @RequestParam: http://zetcode.com/springboot/requestparam/
    @RequestMapping("/new_post")
    public String new_post_form(Model model, @RequestParam(value="title",required=true,defaultValue="") String title,
				@RequestParam(value="desc",required=true,defaultValue="") String desc,
				@RequestParam(value="contact",required=true,defaultValue="") String contact){
	Posting newPost = new Posting(title,desc,contact);
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

    //To-do Implement github logins so we can have admins to the site and review/approve post.
    @RequestMapping("/admin_login")
    public String admin_login(){
	//Update when implmented
	return "/";
    }

    //This will be the page were we do all the verification.
    @RequestMapping("/admin")
    public ModelAndView admin(Model model){
	System.out.println(postings);

	Map<String, Object> params = new HashMap<>();
	params.put("postings", postings);
		
	return new ModelAndView("admin", params);
    }
}
