package pl.pkopy.login.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pkopy.login.models.entities.RegisterEntity;
import pl.pkopy.login.models.entities.UserEntity;
import pl.pkopy.login.models.forms.UserForm;
import pl.pkopy.login.models.repositories.RegisterRepository;
import pl.pkopy.login.models.repositories.UserRepository;
import pl.pkopy.login.models.services.UserService;

import javax.validation.Valid;

@Controller
public class UserController {
    private UserService userService;
    private UserRepository userRepository;
    private RegisterRepository registerRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, RegisterRepository registerRepository){
        this.userRepository = userRepository;
        this.userService = userService;
        this.registerRepository = registerRepository;
    }

    @GetMapping("/")
//    @ResponseBody
    public String index(Model model){
        StringBuilder result = new StringBuilder();
        Iterable<UserEntity> response = userRepository.findAll();
        response.forEach((s)-> result.append(s.getUserName()) );
        System.out.println(result.toString());
        return "index";
    }

    @GetMapping("/login")
    public String loginUser(Model model){
        model.addAttribute("userForm", new UserForm());
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String loginUserPost(@ModelAttribute @Valid UserForm userForm,
                                BindingResult bindingResult,
                                Model model){
//        System.out.println();

        model.addAttribute("userService", userService);


        return userService.createRegisterKey();

    }

    @GetMapping("/register")
    public String registerUser(Model model){
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @GetMapping("/register/{key}")
    @ResponseBody
    public String registerUser(@PathVariable("key") String key,
                               Model model){
        model.addAttribute("userForm", new UserForm());

        if(userService.isExist(key)){
            RegisterEntity registerEntity = registerRepository.findByRegisterKey(key);
            UserEntity userEntity = new UserEntity();

            userEntity.setUserName(registerEntity.getUserName());
            userEntity.setEmail(registerEntity.getEmail());
            userEntity.setPassword(registerEntity.getPassword());
            userEntity.setLocation(registerEntity.getLocation());

            userRepository.save(userEntity);
            registerRepository.delete(registerEntity);

            return registerEntity.getUserName();
        }else{
            return "jest źle";

        }
    }

    @PostMapping("/register")


    public String registerUserPost(@ModelAttribute @Valid UserForm userForm,
                                   BindingResult bindingResult,
                                   Model model){


        model.addAttribute("userForm", new UserForm());

        if(!userRepository.existsByEmail(userForm.getEmail())){
            System.out.println(userRepository.existsByEmail(userForm.getEmail()));
            userService.register(userForm);
        }





        return "index";

    }

}
