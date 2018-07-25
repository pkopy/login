package pl.pkopy.login.models.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.pkopy.login.models.EmailUtil;
import pl.pkopy.login.models.entities.RegisterEntity;
import pl.pkopy.login.models.forms.UserForm;
import pl.pkopy.login.models.repositories.RegisterRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class UserService {

    private RegisterRepository registerRepository;
    private HttpServletRequest request;
    private String ip;

    @Autowired
    public UserService(RegisterRepository registerRepository){
        this.registerRepository = registerRepository;
        request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        ip = request.getRemoteAddr();
    }

    public String createRegisterKey(){

        StringBuilder registerKey = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 30; i++){
            registerKey.append(Character.toString((char) (random.nextInt(26)+64)));
        }

        return registerKey.toString();
    }

    public void register(UserForm userForm){
        LocalDateTime time = LocalDateTime.now();
        List<RegisterEntity> entities = registerRepository.findAllByIp(ip);
        System.out.println(entities.toString());



        if(entities.size() > 0 && time.isBefore(entities.get(entities.size()-1).getCreationTime().plusMinutes(2)) ){
            System.out.println("poczekaj minute");

        }else{
            registerRepository.deleteAll(entities);

            System.out.println("ok");
            System.out.println(time);
            String key = createRegisterKey();
            RegisterEntity registerEntity = new RegisterEntity();
            registerEntity.setUserName(userForm.getUserName());
            registerEntity.setEmail(userForm.getEmail());
            registerEntity.setPassword(userForm.getPassword());
            registerEntity.setLocation(userForm.getLocation());
            registerEntity.setRegisterKey(key);
            registerEntity.setIp(ip);



            EmailUtil.sendEmailAuth(userForm, key);

            registerRepository.save(registerEntity);
        }

    }

    public boolean isExist(String registerKey){
        return registerRepository.existsByRegisterKey(registerKey);
    }


}
