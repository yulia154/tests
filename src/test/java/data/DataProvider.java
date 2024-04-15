package data;

import java.util.ArrayList;
import java.util.Random;

public class DataProvider {
    @org.testng.annotations.DataProvider(name="createValidUser")
    public Object[][] createUser(){
        Random random = new Random();
        int randomNum = random.nextInt();

        return new Object[][]{
                {"my name"+randomNum, "automated"+randomNum+"@gmail.com","male","active"}
        };
    }

    @org.testng.annotations.DataProvider(name="createInvalidUser")
    public Object[][]createInvalidUser(){
        Random random = new Random();
        int randomNum = random.nextInt();

        return new Object[][]{
                {"", "automated"+randomNum+"@gmail.com","male","active"},
                {"my name"+randomNum, "automated"+randomNum,"male","active"},
                {"my name"+randomNum, "automated"+randomNum+"@gmail.com","none","active"},
                {"my name"+randomNum, "automated"+randomNum+"@gmail.com","female",""}
        };
    }

    @org.testng.annotations.DataProvider(name="edgeCases")
    public Object[][] edgeCases(){
        Random random = new Random();
        int randomNum = random.nextInt();
        StringBuilder addRandom = new StringBuilder();
        for(int i=0; i<50; i++){
            addRandom.append(random.nextInt());
            i++;
        }
        return new Object[][]{
                {"more than 200 char"+addRandom, "automated"+randomNum+"@gmail.com","male","active"},
                {"my name"+randomNum, "automated"+addRandom+"@gmail.com","female","inactive"}
        };
    }
}
