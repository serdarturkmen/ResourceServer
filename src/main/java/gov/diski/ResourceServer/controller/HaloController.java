package gov.diski.ResourceServer.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.diski.ResourceServer.model.User;
import gov.diski.ResourceServer.repository.UserRepository;
import gov.diski.ResourceServer.service.UserBS;


@RestController 
@RequestMapping("/api")
public class HaloController {
    private static final String STATE = "state";
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserBS userBS;
    
    /**
     * success method after login success
     * this method checks user if exist or not
     * @param user
     * @return success true
     */
    @RequestMapping(value="/success", method = RequestMethod.GET, params = { "access_token" })
    public Map<String, Object> halo(Principal user, @RequestParam("access_token") String accessToken){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	//check user
    	User usr = userRepository.findByEmail(user.getName().toString());
    	if(usr!=null){
    		usr.setAccessToken(accessToken);
    		userRepository.save(usr);
    	}else{
    		//create new user and authenticate
    		userBS.createUser(user.getName().toString(), accessToken);
    	}
        Map<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("success", Boolean.TRUE);
        hashmap.put("page", "halo");
        hashmap.put("user", user);
        return hashmap;
    }
    
    @RequestMapping(value="/token", method = RequestMethod.GET)
    public String halo(Principal user){
    	//check user
    	User usr = userRepository.findByEmail(user.getName().toString());
    	if(usr!=null){
    		return usr.getAccessToken();
    	}else{
    		return null;
    	}
     
    }
    
    @RequestMapping("/admin")
    public Map<String, Object> admin(Principal user){
        Map<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("success", Boolean.TRUE);
        hashmap.put("page", "admin");
        hashmap.put("user", user.getName());
        return hashmap;
    }
    
    @RequestMapping("/client")
    public Map<String, Object> client(Principal user){
        Map<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("sukses", Boolean.TRUE);
        hashmap.put("page", "client");
        hashmap.put("user", user.getName());
        return hashmap;
    }
    
    @RequestMapping("/staff")
    public Map<String, Object> staff(Principal user){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	OAuth2Authentication userd = (OAuth2Authentication) user;
        Map<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("success", Boolean.TRUE);
        hashmap.put("page", "staff");
        return hashmap;
    }
    
    @RequestMapping("/state/new")
    public Map<String, Object> newState(HttpSession session){
        Map<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("success", Boolean.TRUE);
        
        String state = UUID.randomUUID().toString();
        hashmap.put(STATE, state);
        session.setAttribute(STATE, state);
        
        return hashmap;
    }
    
    @RequestMapping("/state/verify")
    public Map<String, Object> verifyState(HttpSession session){
        Map<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("success", Boolean.TRUE);
        
        String state = (String) session.getAttribute(STATE);
        hashmap.put(STATE, state);
        session.removeAttribute(STATE);
        
        return hashmap;
    }
}
