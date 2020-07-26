package android.example.vkinfo;

public class Utils {

    private UtilsSecond utilsSecond;

    public Utils(UtilsSecond utilsSecond) {
        this.utilsSecond =  utilsSecond;
    }

    public String getRandomString() {
        return "RandomString";
    }

    public String getRandomStringWithRandomNumber() {
        String randomStr = getRandomString();
        int randomNumber = utilsSecond.getRandomNumberSecond();
        return randomStr + " : " + randomNumber;
    }

}
