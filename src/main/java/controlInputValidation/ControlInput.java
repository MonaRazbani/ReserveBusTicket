package controlInputValidation;

import exceptions.InvalidInput;

public class ControlInput {

    public static boolean isValidChar (char select)throws InvalidInput {

        if (select == 'y'||select == 'n'){
            return true;
        }
        else {
            throw new InvalidInput("invalid char ");
        }
    }

    public boolean isValidGender(String gender) throws InvalidInput{
        if (gender.toUpperCase().equals("MALE"))
            return true ;
        else if (gender.toUpperCase().equals("FEMALE"))
            return true;
        else {
            throw new InvalidInput("invalid char ");
        }

    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        boolean isValid=false;
        if (phoneNumber.matches("[0-9]*"))
            if ( phoneNumber.startsWith("09"))
                if (phoneNumber.length()==11)
                    isValid= true;
        return isValid;
    }


    public boolean isValidUserNationalCode(String nationalCode) {
        boolean isVAlid = false;
        if (nationalCode.matches("[0-9]*") )
            if ( nationalCode.length() == 10)
                isVAlid=true;
        return isVAlid;
    }


    public boolean isValidPassword(String name) throws InvalidInput {
        if (name.matches("[0-9]*") && name.length() > 3)
            return true;
        else
            throw new InvalidInput("invalid name Or UserNAme Or CompanyName ");
    }

    public static boolean isValidUserNameOrCompanyNameOrBusName(String userName) {
        if (userName.length() > 3)
            return true;
        else {
            throw new InvalidInput("invalid name Or UserNAme Or CompanyName ");
        }
    }


    public boolean isValidName(String name) {

        if (name.matches("[a-zA-Z]*") && name.length() > 2)
            return true;
        else
            return false;
    }
    public static boolean isValidBusType(String busType){
        if (busType.toUpperCase().equals("VIP"))
            return true ;
        else if (busType.toUpperCase().equals("NORMAL"))
            return true;
        else {
            throw new InvalidInput("invalid BusType ");
        }
    }

    public static boolean isValidBusCapacity(String capacity){
        if (capacity.matches("[0-9]*"))
            return true;
        else
            throw new InvalidInput("invalid capacity ");
    }

    public static boolean isValidBusInfo(String busInfo){
        try {
            String[] split = busInfo.split(",");
            return isValidUserNameOrCompanyNameOrBusName(split[0]) &
                    isValidUserNameOrCompanyNameOrBusName(split[1]) &
                    isValidBusCapacity(split[2]) &
                    isValidBusType(split[3]);
        }catch (InvalidInput|NumberFormatException|NullPointerException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
