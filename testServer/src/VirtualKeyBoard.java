import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;



public class VirtualKeyBoard extends Robot {

    public VirtualKeyBoard() throws AWTException {
        super();
    }

    //Нажатие набора клавиш
    public void pressKeys(String keysCombination) throws IllegalArgumentException {
        for (String key : keysCombination.split("\\+")) {
            try {
                System.out.println(key);
                this.keyPress((int) KeyEvent.class.getField("VK_" + key.toUpperCase()).getInt(null));
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();

            }
            catch(NoSuchFieldException e ) {
                throw new IllegalArgumentException(key.toUpperCase()+" is invalid key\n"+"VK_"+key.toUpperCase() + " is not defined in java.awt.event.KeyEvent");
            }

        }

    }


    //Разжатие набор кнопок
    public void releaseKeys(String keysCombination) throws IllegalArgumentException {

        for (String key : keysCombination.split("\\+")) {
            try {
                // KeyRelease method inherited from java.awt.Robot
                this.keyRelease((int) KeyEvent.class.getField("VK_" + key.toUpperCase()).getInt(null));
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch(NoSuchFieldException e ) {
                throw new IllegalArgumentException(key.toUpperCase()+" is invalid key\n"+"VK_"+key.toUpperCase() + " is not defined in java.awt.event.KeyEvent");
            }
        }

    }

}