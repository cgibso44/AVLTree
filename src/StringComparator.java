/**
 * Created by Cale Gibson (͡° ͜ʖ͡°)
 * on 02/03/15.
 */
public class StringComparator implements Comparator {

    public StringComparator(){}

    @Override
    public int compare(Object a, Object b) throws ClassCastException {
        String aComp = (String) a;
        String bComp = (String) b;

        if(aComp.compareTo(bComp) < 0 )
            return -1;
        else if(aComp.compareTo(bComp) > 0)
            return 1;
        else
            return 0;
    }
}
