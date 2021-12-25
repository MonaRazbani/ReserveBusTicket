package services;

import dao.ManagerDao;
import dao.UserDao;
import exceptions.InvalidInput;
import exceptions.ManagerNotFound;
import exceptions.UserNotFound;
import models.Dto.UserDto;
import models.enums.Role;
import models.roles.Manager;
import models.roles.User;

public class PersonService {
    private ManagerDao managerDao = new ManagerDao();
    private UserDao userDao = new UserDao();


    public boolean isCorrectPassword(String nationalCode, String password, Role role) {
        try {
            if (role == Role.MANAGER) {
                Manager manager = managerDao.findByNationalCode(nationalCode);
                if (manager == null) {//todo check what dose exception throw
                    throw new UserNotFound();
                }
                if (manager.getPassword().equals(password)) {
                    return true;
                }
            } else if (role == Role.USER) {
                User user = userDao.findByNationalCode(nationalCode);
                if (user == null) {//todo check what dose exception throw
                    throw new UserNotFound();
                }
                if (user.getPassword().equals(password)) {
                    return true;
                }
            } else throw new InvalidInput("invalid role");
        } catch (InvalidInput | UserNotFound e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Manager findManagerByNationalCode(String nationalCode )throws ManagerNotFound{
        Manager byNationalCode = managerDao.findByNationalCode(nationalCode);
        if (byNationalCode ==null)
            throw new ManagerNotFound();
        return byNationalCode;
    }
    public User findUserByNationalCode(String nationalCode )throws ManagerNotFound{
        User byNationalCode = userDao.findByNationalCode(nationalCode);
        if (byNationalCode ==null)
            throw new UserNotFound();
        return byNationalCode;
    }

    public UserDto getUserDtoByUser (User user){
        UserDto userDtoWithUser = userDao.findUserDtoWithUser(user);
       return  userDtoWithUser ;
    }

}
